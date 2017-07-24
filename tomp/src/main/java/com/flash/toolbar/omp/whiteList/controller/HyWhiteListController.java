/**
 * 
 */
package com.flash.toolbar.omp.whiteList.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.Constant;
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.IdGenerator;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.common.util.RuleUtil;
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.model.HyWhiteImportBatch;
import com.flash.toolbar.omp.model.HyWhiteList;
import com.flash.toolbar.omp.model.QxUser;
import com.flash.toolbar.omp.user.bo.QxUserModel;
import com.flash.toolbar.omp.whiteList.service.HyWhiteListService;


/**
 * 白名单控制器
 * @author lijun
 *
 */
@Controller
@RequestMapping(value="hyWhiteList")
public class HyWhiteListController extends BaseAction{
	
	private static final Log log = LogFactory.getLog(HyWhiteListController.class);
	
	@Autowired
	private HyWhiteListService service;
	
	/**
	 * 获取白名单信息列表
	 * @param wl
	 * @return
	 */
	@RequestMapping(value="select")
	public ModelAndView select(HyWhiteList hwl,Page page){
		ModelAndView mav = new ModelAndView();
		String countryNo = getSessionModel().getBean().getCountryno();
		String tOperatorId = getSessionModel().getBean().getToperatorid();
		hwl.setCountryno(countryNo);
		hwl.setToperatorid(tOperatorId);
		try {
			mav.addAllObjects(service.selectWhiteList(hwl,page));
		} catch (Exception e) {
			log.error("The exception of querying the whitelist :" + e.getLocalizedMessage());
		}
		mav.setViewName("whiteList/whiteListPhoneNum");
		return mav;
	}
	
	/**
	 * 导入白名单
	 * @param file
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="import",method=RequestMethod.POST)
	@ResponseBody
	public String importHyWhiteList(MultipartFile file){
		Map<String,String> map = new HashMap<String,String>();
		if(null == file || file.isEmpty()){
			log.info("File can not be empty!");
			map.put("retCode", "-1");
			return "<script>parent.callback(" + JSONObject.toJSON(map) + ");</script>";
		}
//		//解析白名单文件
//		StringBuffer msg = new StringBuffer();
//		Map<String, Object> fileMap = parseWhiteList(file,msg);
//		if(null == fileMap || fileMap.isEmpty()){
//			map.put("retCode", "-2");
//			return "<script>parent.callback(" + JSONObject.toJSON(map) + ");</script>";
//		}
//		//调用service层导入数据
//		try {
//			if(service.saveWhiteList((List<HyWhiteList>)fileMap.get("list"),
//					(HyWhiteImportBatch)fileMap.get("section"))){
//				if(0 < msg.length()){
//					map.put("retCode", "1");
//				}else {
//					map.put("retCode", "0");
//				}
//			}else{
//				map.put("retCode", "-3");
//			}
//		} catch (Exception e) {
//			log.error("The exception of importing the whitelist :" + e.getLocalizedMessage());
//			map.put("retCode", "-3");
//		}
		
		try{
			StringBuffer msg = new StringBuffer();
			int mobileLength = 0;
			log.info("start initialize white list db");
			long startTime = System.currentTimeMillis();
			StringBuffer sbCount = new StringBuffer();//已经读取的行数
			StringBuffer endLine = new StringBuffer();//结束标记
			int eachCount = 1000;//每次读取的行数
			int totalCount = 0;
			int curCount = 0;
			log.info("read csv file; each count " + eachCount);
						
			final String uuid = RuleUtil.generateUUID();
			long sTime = 0;
			long eTime = 0;
			QxUserModel qxUserModel = getSessionModel();
			while(!endLine.toString().equals("-1")){
				List<String> list = parseCsvForWhiteList2(mobileLength, file, msg, sbCount, endLine, curCount, eachCount);
				if(list != null && list.size() > 0){
					log.info("save db " + list.size());
					sTime = System.currentTimeMillis();
					totalCount += list.size();
					service.importWhiteList2(uuid, list, qxUserModel);
					eTime = System.currentTimeMillis();
					log.info("save db cost " + (eTime-sTime) + " ms");
				}
				curCount = Integer.parseInt(sbCount.toString());
				log.info("read line " + sbCount.toString());
				list.clear();
			}
			log.info("read last line " + sbCount.toString());
			log.info("save db total " + totalCount);
			service.addWhiteListImp2(uuid, qxUserModel, totalCount);
			
			long endTime = System.currentTimeMillis();
			log.info("end initialize db: cost " + (endTime-startTime)/1000 + " seconds");
			map.put("retCode", "1");
		}catch(Exception e){
			log.error("The exception of importing the whitelist :" + e.getLocalizedMessage());
			map.put("retCode", "-3");
		}
		
		return "<script>parent.callback(" + JSONObject.toJSON(map) + ");</script>";
	}
	
	private List<String> parseCsvForWhiteList2(int mobileLength,MultipartFile file,StringBuffer msg, StringBuffer sbCount, StringBuffer endLine, int curCount, int eachCount){		
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
					//if(sPlan.toLowerCase().contains("magic") || sPlan.toLowerCase().contains("turbo")){
						set.add(phone);
					//}
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
	
	/**
	 * 删除指定白名单
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="delHyWhiteList",method=RequestMethod.POST)
	@ResponseBody
	public Map delHyWhiteList(String[] ids){
		Map<String,Integer> map = new HashMap<String,Integer>();
		QxUser user = getSessionModel().getBean();
		try {
			if(service.delWhiteList(ids,user.getCountryno(),user.getToperatorid())){
				map.put("retCode", 0);
			}else{
				map.put("retCode",-1);
			}
		} catch (Exception e) {
			log.error("The exception of deleting the whitelist :" + e.getLocalizedMessage());
		}
		return map;
	}

	/**
	 * 解析csv文件
	 * @param file
	 * @param modelMap
	 * @param flag
	 * @return
	 */
	private Map<String, Object> parseWhiteList(MultipartFile file,StringBuffer msg) {
		BufferedReader br = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			br = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
			String line;
			Set<String> set = new HashSet<String>();
			Pattern pattern = Pattern.compile("\\d+");
			int mobileLength = 0;
			if(StringUtils.isNumeric(getSessionModel().getMobileLength())){
				mobileLength = Integer.parseInt(getSessionModel().getMobileLength());
			}
			while(null != (line = br.readLine())){
					if(pattern.matcher(line.trim()).matches()){
						if(line.trim().length() == 11 || line.trim().length() == 12){
							set.add(line.trim());
						}else {
							if(0 == msg.length()){
								msg.append("skip");
							}
							log.info("mobileNo length is not equal eleven or twelve ,Skip current data :"+line.trim());
						}						
					}else{
						if(0 == msg.length()){
							msg.append("skip");
						}
						log.info("Skip the phone number that does not conform to the format " + line);
					}
			}
			if(0 < set.size()){
				generateModelData(map,set);
			}			
		} catch (IOException e) {
			log.error("The error of reading the bufferedReader is :" + e.getLocalizedMessage());
			return null;
		}finally{
			if(null != br){
				try {
					br.close();
				} catch (IOException e) {
					log.error("The exception of deleting the blacklist :" + e.getLocalizedMessage());
				}
			}
		}
		return map;
	}

	private void generateModelData(Map<String, Object> map,Set<String> set) {

		List<HyWhiteList> list = new ArrayList<HyWhiteList>();
		String countryNo = getSessionModel().getBean().getCountryno();
		String tOperatorId = getSessionModel().getBean().getToperatorid();
		String tOperatorNo = getSessionModel().gettOperatorNo();
		String tOperatorName = getSessionModel().gettOperatorName();
		String uuid = IdGenerator.getUUID();
		int count = 0;
		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
			String mobileno = iterator.next();
			count++;
			//白名单信息
			HyWhiteList wl = new HyWhiteList();
			wl.setWlistid(IdGenerator.getUUID());
			wl.setBatchid(uuid);
			wl.setMobileno(mobileno);
			wl.setCountryno(countryNo);
			wl.setToperatorid(tOperatorId);
			wl.setAdddate(DateUtil.getTimeZoneDate(new Date(),
					getSessionModel().getTimeZone(), "yyyyMMdd"));					
			list.add(wl);
		}
		//批次信息
		HyWhiteImportBatch section = new HyWhiteImportBatch();
		section.setBatchid(uuid);
		section.setBatchno(DateUtil.getTimeZoneStr(new Date(), 
				getSessionModel().getTimeZone(), DateUtil.FORMAT5) + "0");
		section.setImportdate(DateUtil.getTimeZoneDate(new Date(),
				getSessionModel().getTimeZone(), "yyyyMMdd"));
		section.setImportnum(count);
		section.setCountryno(countryNo);
		section.setToperatorid(tOperatorId);
		section.setToperatorno(tOperatorNo);
		section.setToperatorname(tOperatorName);
		map.put("list", list);
		map.put("section", section);
	}
}
