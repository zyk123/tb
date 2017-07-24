package com.flash.toolbar.omp.blacksegmentlist.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.flash.toolbar.omp.blacksegmentlist.bo.HyBlackSectionModel;
import com.flash.toolbar.omp.blacksegmentlist.service.BlackSegmentListService;
import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.Constant;
import com.flash.toolbar.omp.common.util.HtmlUtil;
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.user.bo.QxUserModel;
@Controller
@RequestMapping(value="/blackSegmentList")
public class BlackSegmentListController extends BaseAction{
	private String[] allowExt = new String[]{"csv"};
	
	private static final Logger log = Logger.getLogger(BlackSegmentListController.class);
	
	@Resource
	private BlackSegmentListService blackSegmentListService;
	
	@RequestMapping(value="/show")
	public String show(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "menuPrivileges") String menuPrivileges){
		request.setAttribute("blackSegmentListPrivileges", menuPrivileges);
		return "blacksectionlist/blackListNumInterval";
	}
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,HyBlackSectionModel blackSectionListModel) {
			try {
				QxUserModel qxUserModel = getSessionModel();
				if(qxUserModel!=null){
					blackSectionListModel.getBean().setCountryNo(qxUserModel.getBean().getCountryno());
					blackSectionListModel.getBean().settOperatorId(qxUserModel.getBean().getToperatorid());
				}
				List<HyBlackSectionModel> list = blackSegmentListService.getBlackSectionListInfo(blackSectionListModel);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", list);
				map.put("page", blackSectionListModel.getPager());
				HtmlUtil.writerJson(response, map);
			} catch (Exception e) {
				log.error("The exception of querying the blacksectionlist :"+e);
			}
	}
	
	
	@RequestMapping(value="/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "delIds[]") String[] delIds){
        try {
			if (delIds == null || delIds.length == 0) {
				log.info("ID invalid!");
			    sendFailureMessage(response, "ID invalid!");
			} else {
				blackSegmentListService.deleteBlackSectionList(delIds,getSessionModel());
				log.info("Delete success!");
			    sendSuccessMessage(response, "Delete success!",delIds.length);
			}
		} catch (Exception e) {
			sendFailureMessage(response, "DeLete failure!");
			log.error("The exception of deleting the blacklist :"+e);
		}
	}
	
	
	@RequestMapping(value="/importBlackSectionList")
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
			List<Map<String, String>> list = parseCsvForBlackList(mobileLength,in,msg);
			if(list==null || list.size()==0){
				log.info("File parse failed!");
				return sendFailureHtml("File parse failed!");
			}
			blackSegmentListService.importBlackSectionList(list,getSessionModel());
			return sendSuccessHtml("Import success!"+msg.toString());
		} catch (Exception e) {
			log.error("The exception of importing the blacksectionlist :"+e);
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
	
	private List<Map<String, String>> parseCsvForBlackList(int mobileLength,InputStream stream,StringBuffer msg){
		BufferedReader reader = null;
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			reader = new BufferedReader(new InputStreamReader(stream));			
			String line;
			while ((line = reader.readLine()) != null) {
				Pattern pattern = Pattern.compile("\\d+");
				String[] segmentList = line.split(",");
				if(segmentList!=null && segmentList.length==2){
					Map<String, String> map = new HashMap<String, String>();
					String startNo = segmentList[0].trim();
					String endNo = segmentList[1].trim();
					map.put("startNo", startNo);
					map.put("endNo", endNo);
					if(pattern.matcher(startNo).matches() && pattern.matcher(endNo).matches()){
						if((startNo.length()== 11 || startNo.length()== 12) && (endNo.length()==11 || endNo.length()==12)){
							if(endNo.compareTo(startNo)>0){
								if(!list.contains(map)){
									list.add(map);
								}else{
									setImportSuccessWarningMsg(msg);
									log.info("File contains same data : startNo="+segmentList[0].trim()+"   endNo="+segmentList[1].trim()+",Skip current data  :"+line);
								}
							}else{
								setImportSuccessWarningMsg(msg);
								log.info("startNo can not be smaller than endNo,Skip current data  :"+line);
							}
						}else{
							setImportSuccessWarningMsg(msg);
							log.info("startNo length or endNo length is not equal eleven or twelve,Skip current data :"+line);
						}
					}else{
						setImportSuccessWarningMsg(msg);
						log.info("Skip is not digital data" + line);
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
		}
		return list;
	}
	
	private void setImportSuccessWarningMsg(StringBuffer msg){
		if(msg!=null && msg.length()==0){
			msg.append(",\nBut some data was filtered because the format is wrong,please view the log");
		}
	}
}
