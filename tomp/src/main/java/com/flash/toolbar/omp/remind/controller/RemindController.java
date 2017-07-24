package com.flash.toolbar.omp.remind.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.HtmlUtil;
import com.flash.toolbar.omp.common.util.IdGenerator;
import com.flash.toolbar.omp.model.SysRemindConfig;
import com.flash.toolbar.omp.remind.bo.SysRemindConfigModel;
import com.flash.toolbar.omp.remind.service.impl.RemindService;
import com.flash.toolbar.omp.user.bo.QxUserModel;
@Controller
@RequestMapping(value="/remind")
public class RemindController extends BaseAction{
	private static final Logger log = Logger.getLogger(RemindController.class);
	
	@Resource
	private RemindService remindService;	
	
	@RequestMapping(value="/show")
	public String show(){
		return "remind/remind";
	}
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,SysRemindConfigModel sysRemindConfigModel) {
			try {
				QxUserModel qxUserModel = getSessionModel();
				if(qxUserModel!=null){
					sysRemindConfigModel.getBean().setCountryno(qxUserModel.getBean().getCountryno());
					sysRemindConfigModel.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
				}
				List<SysRemindConfig> list = remindService.getRemindListInfo(sysRemindConfigModel);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", list);
				map.put("page", sysRemindConfigModel.getPager());
				HtmlUtil.writerJson(response, map);
			} catch (Exception e) {
				log.error("The exception of querying the remindlist :"+e);
			}
	}	
	
	@RequestMapping(value="/batchOnOff")
	public void batchOnOff(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "batchIds[]") String[] batchIds,@RequestParam(value = "type") String type){
        try {
			if (batchIds == null || batchIds.length == 0) {
				log.info("ID invalid!");
			    sendFailureMessage(response, "ID invalid!");
			} else {
				remindService.batchOnOff(batchIds,type,getSessionModel());
				log.info("Update success!");
			    sendSuccessMessage(response, "Update success!",batchIds.length);
			}
		} catch (Exception e) {
			sendFailureMessage(response, "Update failure!");
			log.error("The exception of turning on or off :"+e);
		}
	}
	
	@RequestMapping(value="/batchSetTimeInterval")
	public void batchSetTimeInterval(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "batchIds[]") String[] batchIds,@RequestParam(value = "remindVal") String remindVal){
		try {
			if (batchIds == null || batchIds.length == 0) {
				log.info("ID invalid!");
				sendFailureMessage(response, "ID invalid!");
			} else {
				remindService.batchSetTimeInterval(batchIds,remindVal,getSessionModel());
				log.info("Update success!");
				sendSuccessMessage(response, "Update success!",batchIds.length);
			}
		} catch (Exception e) {
			sendFailureMessage(response, "Update failure!");
			log.error("The exception of modifying time interval :"+e);
		}
	}
	
	@RequestMapping(value="/modify")
	public void modify(HttpServletRequest request, HttpServletResponse response,SysRemindConfig sysRemindConfig){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				sysRemindConfig.setCountryno(qxUserModel.getBean().getCountryno());
				sysRemindConfig.setToperatorid(qxUserModel.getBean().getToperatorid());
				sysRemindConfig.setModifydate(DateUtil.getTimeZoneDate(new Date(), qxUserModel.getTimeZone(), "yyyy-MM-dd HH:mm:ss"));
				sysRemindConfig.setModifyman(qxUserModel.getBean().getUserid());
			}
			remindService.modify(sysRemindConfig);
			sendSuccessMessage(response, "Update success!");
		} catch (Exception e) {
			sendFailureMessage(response, "Update failure!");
			log.error("The exception of modifying :"+e);
		}
	}
	
	
	@RequestMapping(value="/add")
	public void add(HttpServletRequest request, HttpServletResponse response,SysRemindConfig sysRemindConfig){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				sysRemindConfig.setCountryno(qxUserModel.getBean().getCountryno());
				sysRemindConfig.setToperatorid(qxUserModel.getBean().getToperatorid());
				sysRemindConfig.setModifydate(DateUtil.getTimeZoneDate(new Date(), qxUserModel.getTimeZone(), "yyyy-MM-dd HH:mm:ss"));
				sysRemindConfig.setModifyman(qxUserModel.getBean().getUserid());				
			}
			sysRemindConfig.setId(IdGenerator.getUUID());
			SysRemindConfigModel sysRemindConfigModel = getTempsysRemindConfigModel(sysRemindConfig,qxUserModel);
			if(sysRemindConfigModel!=null && remindService.countByCondition(sysRemindConfigModel)==0){
				remindService.add(sysRemindConfig);
			}else{
				sendFailureMessage(response, "Sorry,Contains same data,Please re-enter!");
			}
			sendSuccessMessage(response, "Update success!");
		} catch (Exception e) {
			sendFailureMessage(response, "Update failure!");
			log.error("The exception of modifying :"+e);
		}
	}
	
	@RequestMapping(value="/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "batchIds[]") String[] batchIds){
		try {
			if (batchIds == null || batchIds.length == 0) {
				log.info("ID invalid!");
			    sendFailureMessage(response, "ID invalid!");
			} else {
				remindService.deleteRemindList(batchIds,getSessionModel());
				log.info("Delete success!");
			    sendSuccessMessage(response, "Delete success!",batchIds.length);
			}
		} catch (Exception e) {
			sendFailureMessage(response, "DeLete failure!");
			log.error("The exception of deleting the remindList :"+e);
		}
	}
	
	private SysRemindConfigModel getTempsysRemindConfigModel(SysRemindConfig originalSysRemindConfig,QxUserModel qxUserModel){
		if(qxUserModel==null || originalSysRemindConfig==null){
			return null;
		}
		SysRemindConfigModel sysRemindConfigModel = new SysRemindConfigModel();
		SysRemindConfig tempSysRemindConfig =  new SysRemindConfig();
		tempSysRemindConfig.setCountryno(qxUserModel.getBean().getCountryno());
		tempSysRemindConfig.setToperatorid(qxUserModel.getBean().getToperatorid());
		tempSysRemindConfig.setBrand(originalSysRemindConfig.getBrand());
		tempSysRemindConfig.setType(originalSysRemindConfig.getType());
		tempSysRemindConfig.setPreposindicator(originalSysRemindConfig.getPreposindicator());
		sysRemindConfigModel.setBean(tempSysRemindConfig);	
		return sysRemindConfigModel;
	}
}
