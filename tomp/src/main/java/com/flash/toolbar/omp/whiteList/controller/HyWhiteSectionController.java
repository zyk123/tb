package com.flash.toolbar.omp.whiteList.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.IdGenerator;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.HyWhiteImportBatch;
import com.flash.toolbar.omp.model.HyWhiteSection;
import com.flash.toolbar.omp.model.QxUser;
import com.flash.toolbar.omp.whiteList.service.HyWhiteSectionService;

/**
 * 白名单号段控制器
 * @author lijun
 *
 */
@Controller
@RequestMapping(value="hyWhiteSection")
public class HyWhiteSectionController extends BaseAction{
	
	private static final Log log = LogFactory.getLog(HyWhiteSectionController.class);
	
	@Autowired
	private HyWhiteSectionService service;
	
	@RequestMapping(value="select")
	public ModelAndView select(HyWhiteSection section,Page page){
		ModelAndView mav = new ModelAndView();
		try {
			mav.addAllObjects(service.selectHyWhiteSection(section, page));
		} catch (Exception e) {
			log.error("The exception of querying the whitesection :" + e.getLocalizedMessage());
		}
		mav.setViewName("whiteList/whiteListNumInterval");
		return mav;
	}
	
	@RequestMapping(value="upload")
	@ResponseBody
	public String upload(MultipartFile file){
		Map<String,String> map = new HashMap<String, String>();
		if(null == file || file.isEmpty()){
			log.info("File can not be empty!");
			map.put("retCode", "-1");
			return "<script>parent.callback(" + JSONObject.toJSON(map) + ")</script>";
		}
		StringBuffer msg = new StringBuffer();
		Map<String, Object> modelMap = parseFile(file,msg);
		if(null == modelMap || modelMap.isEmpty()){
			map.put("retCode","-2");
			return "<script>parent.callback(" + JSONObject.toJSON(map) + ")</script>";
		}
		try {
			if(service.insertHyWhiteSection((List<HyWhiteSection>)modelMap.get("list"),
					(HyWhiteImportBatch)modelMap.get("section"))){
				if(0 < msg.length()){
					map.put("retCode", "1");
				}else {
					map.put("retCode", "0");
				}
			}else{
				map.put("retCode", "-3");
			}
		} catch (Exception e) {
			log.error("The exception of importing the whitesection :" + e.getLocalizedMessage());
			map.put("retCode", "-3");
		}
		return "<script>parent.callback(" + JSONObject.toJSON(map) + ")</script>";
	}
	
	/**
	 * 删除指定白名单
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="delHyWhiteSection",method=RequestMethod.POST)
	@ResponseBody
	public Map delHyWhiteSection(String[] ids){
		Map<String,Integer> map = new HashMap<String,Integer>();
		QxUser user = getSessionModel().getBean();
		try {
			if(service.delWhiteSection(ids,user.getCountryno(),user.getToperatorid())){
				map.put("retCode", 0);
			}else{
				map.put("retCode",-1);
			}
		} catch (Exception e) {
			log.error("The exception of deleting the whitesection :" + e.getLocalizedMessage());
		}
		return map;
	}
	
	private Map<String, Object> parseFile(MultipartFile file,StringBuffer msg){
		BufferedReader br = null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> left = new ArrayList<String>();
		List<String> right = new ArrayList<String>();
		try {
			br = new BufferedReader(new InputStreamReader(file.getInputStream()));
			String line = null;
			Pattern pattern = Pattern.compile("\\d+");
			int mobileLength = 0;
			if(StringUtils.isNumeric(getSessionModel().getMobileLength())){
				mobileLength = Integer.parseInt(getSessionModel().getMobileLength());
			}
			while(null != (line = br.readLine())){
				String[] sectionArr = line.split(",");
				if(null != sectionArr && 2 == sectionArr.length ){
					String l = sectionArr[0].trim();
					String r = sectionArr[1].trim();
					if(pattern.matcher(l).matches() && pattern.matcher(r).matches() ){						
						if(l.length() == r.length()){
							if(l.length() == 11 || l.length() == 12){
								if(l.compareTo(r) < 0){
									if(left.indexOf(l) == -1 || right.indexOf(r) == -1 || left.indexOf(l) != right.indexOf(r)){
										left.add(sectionArr[0]);
										right.add(sectionArr[1]);												
									}else {
										if(0 == msg.length()){
											msg.append("skip");
										}
										log.info("File contains same data : startNo="+l+"   endNo="+r+",Skip current data  :"+line);
									}
								}else {
									if(0 == msg.length()){
										msg.append("skip");
									}
									log.info("startNo can not be more than or equal endNo,Skip current data  :"+line);
								}				
							}else {
								if(0 == msg.length()){
									msg.append("skip");
								}
								log.info("mobileNo length is not equal eleven or twelve ,Skip current data :"+line.trim());
							}							
						}else {
							if(0 == msg.length()){
								msg.append("skip");
							}
							log.info("startNo length and endNo length is not equal,Skip current data :"+line);
						}
					}else{
						if(0 == msg.length()){
							msg.append("skip");
						}
						log.info("Skip the phone number that contains non digital data:" + line);
					}
				}else {
					if(0 == msg.length()){
						msg.append("skip");
					}
					log.info("Skip the phone number either startNo or endNo is empty:" + line);
				}
			}
			if(0 < left.size()){
				generateModelData(map, left,right);
			}
		} catch (IOException e) {
			log.error("The error of reading the bufferedReader is :" + e.getLocalizedMessage());
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
	
	private void generateModelData(Map<String, Object> map,List<String> left,List<String> right) {

		List<HyWhiteSection> list = new ArrayList<HyWhiteSection>();
		String countryNo = getSessionModel().getBean().getCountryno();
		String tOperatorId = getSessionModel().getBean().getToperatorid();
		String tOperatorNo = getSessionModel().gettOperatorNo();
		String tOperatorName = getSessionModel().gettOperatorName();
		String uuid = IdGenerator.getUUID();
		int count = 0;
		for (int i = 0,l = left.size(); i < l; i ++) {
			count++;
			//白名单号段信息
			HyWhiteSection section = new HyWhiteSection();
			section.setWsectionid(IdGenerator.getUUID());
			section.setBatchid(uuid);
			section.setMobilenostart(left.get(i));
			section.setMobilenoend(right.get(i));
			section.setCountryno(countryNo);
			section.setToperatorid(tOperatorId);
			section.setAdddate(DateUtil.getTimeZoneDate(new Date(),
					getSessionModel().getTimeZone(), "yyyyMMdd"));					
			list.add(section);
		}
		//批次信息
		HyWhiteImportBatch batch = new HyWhiteImportBatch();
		batch.setBatchid(uuid);
		batch.setBatchno(DateUtil.getFormatDate(new Date(), DateUtil.FORMAT5) + "1");
		batch.setImportdate(DateUtil.getTimeZoneDate(new Date(),
				getSessionModel().getTimeZone(), "yyyyMMdd"));
		batch.setImportnum(count);
		batch.setCountryno(countryNo);
		batch.setToperatorid(tOperatorId);
		batch.setToperatorno(tOperatorNo);
		batch.setToperatorname(tOperatorName);
		map.put("list", list);
		map.put("section", batch);
	}

}
