package com.flash.toolbar.omp.blacklist.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.flash.toolbar.omp.blacklist.bo.HyBlackListModel;
import com.flash.toolbar.omp.blacklist.service.BlackListService;
import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.Constant;
import com.flash.toolbar.omp.common.util.HtmlUtil;
import com.flash.toolbar.omp.common.util.RuleUtil;
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="/blackList")
public class BlackListController extends BaseAction{
	
	private static final Logger log = Logger.getLogger(BlackListController.class);
	
	private String[] allowExt = new String[]{"csv"};
	
	@Resource
	private BlackListService blackListService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "blackList/blackListPhoneNum";
	}
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,HyBlackListModel blackListModel) {
			try {
				QxUserModel qxUserModel = getSessionModel();
				if(qxUserModel!=null){
					blackListModel.getBean().setCountryNo(qxUserModel.getBean().getCountryno());
					blackListModel.getBean().settOperatorId(qxUserModel.getBean().getToperatorid());
				}
				List<HyBlackListModel> list = blackListService.getBlackListInfo(blackListModel);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", list);
				map.put("page", blackListModel.getPager());
				HtmlUtil.writerJson(response, map);
			} catch (Exception e) {
				log.error("The exception of querying the blacklist :"+e);
			}
	}
	
	
	@RequestMapping(value="/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "delIds[]") String[] delIds){
        try {
			if (delIds == null || delIds.length == 0) {
				log.info("ID invalid!");
			    sendFailureMessage(response, "ID invalid!");
			} else {
				blackListService.deleteBlackList(delIds,getSessionModel());
				log.info("Delete success!");
			    sendSuccessMessage(response, "Delete success!",delIds.length);
			}
		} catch (Exception e) {
			sendFailureMessage(response, "DeLete failure!");
			log.error("The exception of deleting the blacklist :"+e);
		}
	}
	
	
	@RequestMapping(value="/importBlackList")
	@ResponseBody
	public String importBlackList(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="fileImport")MultipartFile  file){
		try {
			if(file==null || file.isEmpty()){
				log.info("File can not be empty!");
				return sendFailureHtml("File can not be empty!");
			}
			String fileName = file.getOriginalFilename();
			String postfix = getFilePostfix(fileName);
			if(!extIsAllowed(postfix, allowExt)){
				log.info("File is not a CSV format!");
				return sendFailureHtml("File is not a CSV format!");
			}	
			InputStream in = file.getInputStream();
			StringBuffer msg = new StringBuffer();
			int mobileLength = 0;
			if(request.getSession()!=null && request.getSession().getAttribute(Constant.USER_MODEL_SESSION)!=null){
				QxUserModel qxUserModel = (QxUserModel)request.getSession().getAttribute(Constant.USER_MODEL_SESSION);
				String ml = qxUserModel.getMobileLength();
				if(!StringUtil.isNull(ml)){
					mobileLength = Integer.parseInt(ml);
				}
			}
			List<String> list = parseCsvForBlackList(mobileLength,in,msg);
			if(list==null || list.size()==0){
				log.info("File parse failed!");
				return sendFailureHtml("File parse failed!");
			}
			blackListService.importBlackList(list,getSessionModel());
			return sendSuccessHtml("Import success!"+msg.toString());
		} catch (Exception e) {
			log.error("The exception of importing the blacklist :"+e);
			return sendFailureHtml("Import failure!");
		}
	}
	
	@RequestMapping(value="/importBlackList2")
	@ResponseBody
	public String importBlackList2(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="fileImport")MultipartFile  file){
		try {
			if(file==null || file.isEmpty()){
				log.info("File can not be empty!");
				return sendFailureHtml("File can not be empty!");
			}
			String fileName = file.getOriginalFilename();
			String postfix = getFilePostfix(fileName);
			if(!extIsAllowed(postfix, allowExt)){
				log.info("File is not a CSV format!");
				return sendFailureHtml("File is not a CSV format!");
			}	
//			InputStream in = file.getInputStream();
			StringBuffer msg = new StringBuffer();
			int mobileLength = 0;
			if(request.getSession()!=null && request.getSession().getAttribute(Constant.USER_MODEL_SESSION)!=null){
				QxUserModel qxUserModel = (QxUserModel)request.getSession().getAttribute(Constant.USER_MODEL_SESSION);
				String ml = qxUserModel.getMobileLength();
				if(!StringUtil.isNull(ml)){
					mobileLength = Integer.parseInt(ml);
				}
			}
			
			log.info("start initialize db");
			long startTime = System.currentTimeMillis();
			StringBuffer sbCount = new StringBuffer();//已经读取的行数
			StringBuffer endLine = new StringBuffer();//结束标记
			int eachCount = 100000;//每次读取的行数
			int totalCount = 0;
			int curCount = 0;
			log.info("read csv file; each count " + eachCount);
						
			final String uuid = RuleUtil.generateUUID();
						
			while(!endLine.toString().equals("-1")){
				List<String> list = parseCsvForBlackList2(mobileLength, file, msg, sbCount, endLine, curCount, eachCount);
				if(list != null && list.size() > 0){
					log.info("save db " + list.size());
					totalCount += list.size();
					blackListService.importBlackList2(uuid, list, getSessionModel());
				}
				curCount = Integer.parseInt(sbCount.toString());
				log.info("read line " + sbCount.toString());
				list.clear();
			}
			log.info("read last line " + sbCount.toString());
			log.info("save db total " + totalCount);
			blackListService.addBlackListImp2(uuid, getSessionModel(), totalCount);
			
			long endTime = System.currentTimeMillis();
			log.info("end initialize db: cost " + (endTime-startTime)/1000 + " seconds");
			return sendSuccessHtml("Import success!"+msg.toString());
		} catch (Exception e) {
			log.error("The exception of importing the blacklist :"+e);
			return sendFailureHtml("Import failure!");
		}
	}
	
	private String getFilePostfix(String originalFilename) {
		int pointIndex = originalFilename.lastIndexOf(".");
		if (pointIndex == -1) {
			return "";
		}
		return originalFilename.substring(pointIndex + 1).toLowerCase();
	}
	
	
	private boolean extIsAllowed(String ext, String[] allowExt) {
		for (int i = 0; i < allowExt.length; i++) {
			if (ext.equals(allowExt[i].toLowerCase()) || "*".equals(allowExt[i]))
				return true;
		}
		return false;
	}
	
	private List<String> parseCsvForBlackList(int mobileLength,InputStream stream,StringBuffer msg){
		BufferedReader reader = null;
		List<String> list = new LinkedList<String>();
		try {
			reader = new BufferedReader(new InputStreamReader(stream));			
			String line;
			Pattern pattern = Pattern.compile("\\d+");
			Set<String> set = new HashSet<String>();
			while ((line = reader.readLine()) != null) {
				if(pattern.matcher(line.trim()).matches()){
					if(line.trim().length()==11 || line.trim().length() == 12){
						if(!set.contains(line.trim())){
							set.add(line.trim());
						}else{
							setImportSuccessWarningMsg(msg);
							log.info("File contains same data : "+line.trim()+" ,Skip current data :"+line.trim());
						}
					}else{
						setImportSuccessWarningMsg(msg);					
						log.info("mobileNo length is not equal eleven or twelve,Skip current data :"+line.trim());
					}
				}else{
					setImportSuccessWarningMsg(msg);				
					log.info("Skip is not digital data" + line);
				}				
			}
			list.addAll(set);
		} catch (Exception e) {
			log.error("The error of parsing the file is :"+e);
		} finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					log.error("The error of closing the bufferedReader is :"+e);
				}
			}
		}
		return list;
	}
	
	private List<String> parseCsvForBlackList2(int mobileLength,MultipartFile file,StringBuffer msg, StringBuffer sbCount, StringBuffer endLine, int curCount, int eachCount){		
		endLine.delete(0, endLine.length());
		sbCount.delete(0, sbCount.length());
		BufferedReader reader = null;
		List<String> list = new LinkedList<String>();
		Set<String> set = new HashSet<String>();
		try {
			InputStream stream = file.getInputStream();
			reader = new BufferedReader(new InputStreamReader(stream));			
			String line = new String("");
			int count = 0;//记录当前读取的行数
			while ((line = reader.readLine()) != null) {
				count += 1;
				//当前行数小于已经读取的行数则跳过
				if(count < curCount){
					continue;
				}
				//当前行数减已经读取的行数如果大于每次需要读取的行数则跳出
				if((count - curCount) >= eachCount){
					break;
				}
				String temp = line.trim();
				String[] lines = temp.split(",");
				if(lines.length == 3){
					String sPhone = lines[0];
					String sPlan = lines[1];
					String phone = sPhone.substring(sPhone.indexOf("\"")+1, sPhone.lastIndexOf("\""));
					if(!phone.startsWith("60")){
						phone = "60" + phone;
					}
					if(sPlan.toLowerCase().contains("magic") || sPlan.toLowerCase().contains("turbo")){
						set.add(phone);
					}
				}
						
			}
			if(line == null){//读取结束
				endLine.append("-1");
			}
			else{
				endLine.append("0");
			}
			sbCount.append(count);
			list.addAll(set);
			set.clear();
		} catch (Exception e) {
			endLine.delete(0, endLine.length());
			endLine.append("-1");
			log.error("The error of parsing the file is :"+e);
		} finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					endLine.delete(0, endLine.length());
					endLine.append("-1");
					log.error("The error of closing the bufferedReader is :"+e);
				}
			}
		}
		return list;
	}
	
	private void setImportSuccessWarningMsg(StringBuffer msg){
		if(msg!=null && msg.length()==0){
			msg.append(",\nBut some data was filtered because the format is wrong,please view the log");
		}
	}
	
	private void parseNewCsv(MultipartFile file){		
		BufferedReader reader = null;
		BufferedWriter writer1 = null;
		try {
			InputStream stream = file.getInputStream();
			reader = new BufferedReader(new InputStreamReader(stream));		
			
			File file1 = new File("d:/1w.csv");
			if (!file1.exists()) {
				file1.createNewFile();
			}
			writer1 = new BufferedWriter(new FileWriter(file1, true));
			
			String line = new String("");
			int count = 0;//记录行数
			while ((line = reader.readLine()) != null) {
				String temp = line.trim();
				String[] lines = temp.split(",");
				if(lines.length == 3){
					String sPhone = lines[0];
					String sPlan = lines[1];
					String phone = sPhone.substring(sPhone.indexOf("\"")+1, sPhone.lastIndexOf("\""));
					if(!phone.startsWith("60")){
						phone = "60" + phone;
					}
					if(sPlan.toLowerCase().contains("magic") || sPlan.toLowerCase().contains("turbo")){
						count += 1;
						if(count <= 10000){
							writer1.write(temp);
							writer1.newLine();
							writer1.flush();
						}
						else{
							break;
						}
					}
				}
						
			}
		} catch (Exception e) {
			log.error("The error of parsing the file is :"+e);
		} finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					log.error("The error of closing the bufferedReader is :"+e);
				}
			}
			if(writer1 != null){
				try {
					writer1.close();
					writer1 = null;
				} catch (IOException e) {
					log.error("The error of closing the bufferedWriter1 is :"+e);
				}
			}
		}
	}
}
