package com.flash.toolbar.omp.packagemange.controller;

import java.util.ArrayList;
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
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.model.TcFlowpackagetypeRela;
import com.flash.toolbar.omp.model.TcPackageType;
import com.flash.toolbar.omp.packagemange.bo.TcFlowPackageRelation;
import com.flash.toolbar.omp.packagemange.bo.TcPackageTypeModel;
import com.flash.toolbar.omp.packagemange.service.PackageTypeService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="/packageType")
public class PackageTypeController extends BaseAction{
	private static final Logger log = Logger.getLogger(PackageTypeController.class);
	
	@Resource
	private PackageTypeService packageTypeService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "package/packageType";
	}
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,TcPackageTypeModel model){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				model.getBean().setCountryno(qxUserModel.getBean().getCountryno());
				model.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<TcPackageType> list = packageTypeService.getPackageTypeList(model);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("page", model.getPager());
			HtmlUtil.writerJson(response, map);
		} catch (Exception e) {
			log.error("The exception of querying the packageTypeList :"+e);
		}
	}	
	
	
	@RequestMapping(value="/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "delIds[]") String[] delIds){
        try {
			if (delIds == null || delIds.length == 0) {
				log.info("ID invalid!");
			    sendFailureMessage(response, "ID invalid!");
			} else {
				packageTypeService.deleteList(delIds,getSessionModel());
				log.info("Delete success!");
			    sendSuccessMessage(response, "Delete success!",delIds.length);
			}
		} catch (Exception e) {
			sendFailureMessage(response, "DeLete failure!");
			log.error("The exception of deleting the packageType :"+e);
		}
	}
	
	
	@RequestMapping(value="/save")
	public void save(HttpServletRequest request, HttpServletResponse response,TcPackageType bean){
		try {
			boolean flag = false;
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				bean.setCountryno(qxUserModel.getBean().getCountryno());
				bean.setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			if(StringUtil.isNull(bean.getPackagetypeid())){
				bean.setPackagetypeid(IdGenerator.getUUID());
				bean.setAdddate(DateUtil.getTimeZoneDate(new Date(),
					getSessionModel().getTimeZone(), "yyyy-MM-dd HH:mm:ss"));
				flag = packageTypeService.savePackageType(bean);
			}else{
				flag = packageTypeService.modifyPackageType(bean);
			}
			sendSuccessMessage(response,"Save success!");
		} catch (Exception e) {
			sendFailureMessage(response,"Save failed!");
			log.error("The exception of saving the package :"+e);
		}
	}
	
	
	@RequestMapping(value="/updateOrderNo")
	public void updateOrderNo(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "packagetypeid") String packagetypeid,@RequestParam(value = "type") String type,
			@RequestParam(value = "preorafterid") String preorafterid,@RequestParam(value = "preoraftertype") String preoraftertype){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if (StringUtil.isNull(packagetypeid) || StringUtil.isNull(type) || StringUtil.isNull(preorafterid) || StringUtil.isNull(preoraftertype)) {
				log.info("Param invalid!");
				sendFailureMessage(response, "Param invalid!");
			} else {
				packageTypeService.updateOrderNo(packagetypeid,type,preorafterid,preoraftertype,qxUserModel);
				log.info("Delete success!");
				sendSuccessMessage(response, "Update success!");
			}
		} catch (Exception e) {
			sendFailureMessage(response, "Update failure!");
			log.error("The exception of Updating the packageType :"+e);
		}
	}
	
	
	@RequestMapping(value="/getRelationPackageList")
	public void getRelationPackageList(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "packagetypeid",defaultValue="") String packagetypeid){
		try {
			QxUserModel qxUserModel = getSessionModel();
			List<TcFlowPackageRelation> list = packageTypeService.getRelationPackageList(packagetypeid,qxUserModel);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			HtmlUtil.writerJson(response, map);
		} catch (Exception e) {
			log.error("The exception of querying the relationPackageList :"+e);
		}
	}
	
	@RequestMapping(value="/saveRelation")
	public void saveRelation(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "packageids[]",defaultValue="") String[] packageids,@RequestParam(value = "packagetypeid") String packagetypeid){
		try {
			if(StringUtil.isNull(packagetypeid)){
				log.info("packageid is null or relationids is empty!");
				sendFailureMessage(response, "packagetypeid is null or packageids is empty!");				
			}else{
				List<TcFlowpackagetypeRela> list = new ArrayList<TcFlowpackagetypeRela>(packageids.length);
				for (int i = 0; i < packageids.length; i++) {
					TcFlowpackagetypeRela rela = new TcFlowpackagetypeRela();
					rela.setRelaid(IdGenerator.getUUID());
					rela.setPackagetypeid(packagetypeid);
					rela.setPackageid(packageids[i]);
					list.add(rela);
				}
				packageTypeService.saveRelation(packagetypeid,list);
				sendSuccessMessage(response,"Save success!");
			}
			
		} catch (Exception e) {
			sendFailureMessage(response,"Save failed!");
			log.error("The exception of saving Relation :"+e);
		}
	}		

}
