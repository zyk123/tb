package com.flash.toolbar.omp.packagemange.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.flash.toolbar.omp.model.TcFlowPackage;
import com.flash.toolbar.omp.model.TcFlowpackageDetail;
import com.flash.toolbar.omp.packagemange.bo.TcFlowPackageModel;
import com.flash.toolbar.omp.packagemange.bo.TcFlowpackageDetailModel;
import com.flash.toolbar.omp.packagemange.bo.TcFlowpackageDetailRec;
import com.flash.toolbar.omp.packagemange.service.PackageService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="/package")
public class PackageController extends BaseAction{
	private static final Logger log = Logger.getLogger(PackageController.class);
	
	@Resource
	private PackageService packageService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "package/package";
	}
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,TcFlowPackageModel model){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				model.getBean().setCountryno(qxUserModel.getBean().getCountryno());
				model.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<TcFlowPackage> list = packageService.getPackageList(model);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("page", model.getPager());
			HtmlUtil.writerJson(response, map);
		} catch (Exception e) {
			log.error("The exception of querying the packageList :"+e);
		}
	}
	
	
	@RequestMapping(value="/getPackageDetailList")
	public void getPackageDetailList(HttpServletRequest request, HttpServletResponse response,TcFlowpackageDetail bean){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				bean.setCountryno(qxUserModel.getBean().getCountryno());
				bean.setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			bean.setDelflag("1");
			List<TcFlowpackageDetailRec> list = packageService.getPackageDetailRecList(bean);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			HtmlUtil.writerJson(response, map);
		} catch (Exception e) {
			log.error("The exception of querying the packageDetailList :"+e);
		}
	}	
	
	
	
	@RequestMapping(value="/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "delIds[]") String[] delIds){
        try {
			if (delIds == null || delIds.length == 0) {
				log.info("ID invalid!");
			    sendFailureMessage(response, "ID invalid!");
			} else {
				packageService.deleteList(delIds,getSessionModel());
				log.info("Delete success!");
			    sendSuccessMessage(response, "Delete success!",delIds.length);
			}
		} catch (Exception e) {
			sendFailureMessage(response, "DeLete failure!");
			log.error("The exception of deleting the package :"+e);
		}
	}
	
	
	@RequestMapping(value="/updateStatus")
	public void updateStatus(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "delIds[]") String[] delIds,@RequestParam(value = "status") String status){
		try {
			if (delIds == null || delIds.length == 0) {
				log.info("ID invalid!");
				sendFailureMessage(response, "ID invalid!");
			} else {
				packageService.updateStatus(delIds,status,getSessionModel());
				log.info("Delete success!");
				sendSuccessMessage(response, "Update success!",delIds.length);
			}
		} catch (Exception e) {
			sendFailureMessage(response, "Update failure!");
			log.error("The exception of Updating the package :"+e);
		}
	}
	
	@RequestMapping(value="/save")
	public void save(HttpServletRequest request, HttpServletResponse response,TcFlowPackage bean){
		try {
			boolean flag = false;
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				bean.setCountryno(qxUserModel.getBean().getCountryno());
				bean.setToperatorid(qxUserModel.getBean().getToperatorid());
				bean.setModifyman(qxUserModel.getBean().getUserid());
			}
			bean.setModifydate(DateUtil.getTimeZoneDate(new Date(),
					getSessionModel().getTimeZone(), "yyyy-MM-dd HH:mm:ss"));
			bean.setDelflag("1");
			if(StringUtil.isNull(bean.getPackageid())){
				bean.setPackageid(IdGenerator.getUUID());
				bean.setAdddate(DateUtil.getTimeZoneDate(new Date(),
					getSessionModel().getTimeZone(), "yyyy-MM-dd HH:mm:ss"));
				flag = packageService.savePackage(bean);
			}else{
				flag = packageService.modifyPackage(bean);
			}
			sendSuccessMessage(response,"Save success!");
		} catch (Exception e) {
			sendFailureMessage(response,"Save failed!");
			log.error("The exception of saving the package :"+e);
		}
	}
	
	@RequestMapping(value="/saveRelation")
	public void saveRelation(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "relationids[]") String[] relationids,@RequestParam(value = "packageid") String packageid){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(StringUtil.isNull(packageid) ||  relationids.length==0){
				log.info("packageid is null or relationids is empty!");
				sendFailureMessage(response, "packageid is null or relationids is empty!");				
			}else{
				Set<String> packageDetailIds = new HashSet<String>();
				for (int i = 0; i < relationids.length; i++) {
					String[] tempRelationIds = relationids[i].split("-");
					if(tempRelationIds.length==2){
						packageDetailIds.add(tempRelationIds[0]);
						packageDetailIds.add(tempRelationIds[1]);
					}
				}
				packageService.saveRelation(packageid,packageDetailIds.toArray(new String[0]),qxUserModel);
				sendSuccessMessage(response,"Save success!");
			}
			
		} catch (Exception e) {
			sendFailureMessage(response,"Save failed!");
			log.error("The exception of saving Relation :"+e);
		}
	}	
}
