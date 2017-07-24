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
import com.flash.toolbar.omp.model.TcFlowpackageDetail;
import com.flash.toolbar.omp.packagemange.bo.TcFlowpackageDetailModel;
import com.flash.toolbar.omp.packagemange.service.PackageDetailService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="/packageDetail")
public class PackageDetailController extends BaseAction{
	private static final Logger log = Logger.getLogger(PackageDetailController.class);
	
	@Resource
	private PackageDetailService packageDetailService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "package/packageDetail";
	}
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,TcFlowpackageDetailModel model){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				model.getBean().setCountryno(qxUserModel.getBean().getCountryno());
				model.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<TcFlowpackageDetailModel> list = packageDetailService.getPackageDetailList(model);
			List<Map<String, Object>> parentList = packageDetailService.getParentList(model.getBean(),null);
			List<Map<String, Object>> newList = new ArrayList<Map<String,Object>>(list.size());
			for (int i = 0; i < parentList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", parentList.get(i).get("ID"));
				map.put("text", parentList.get(i).get("TEXT"));
				newList.add(map);				
			}			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("parentList", newList);
			map.put("page", model.getPager());
			HtmlUtil.writerJson(response, map);
		} catch (Exception e) {
			log.error("The exception of querying the packageDetailList :"+e);
		}
	}
	
	@RequestMapping(value="/parentSelect")
	public void parentSelect(HttpServletRequest request, HttpServletResponse response,TcFlowpackageDetail bean,@RequestParam(value = "q", defaultValue = "") String term){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				bean.setCountryno(qxUserModel.getBean().getCountryno());
				bean.setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<Map<String, Object>> list = packageDetailService.getParentList(bean,term);
			List<Map<String, Object>> newList = new ArrayList<Map<String,Object>>(list.size());
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", list.get(i).get("ID"));
				map.put("text", list.get(i).get("TEXT"));
				newList.add(map);				
			}
			HtmlUtil.writerJson(response, newList);
		} catch (Exception e) {
			log.error("The exception of querying the parentPackageDetailList :"+e);
		}
	}	
	
	
	@RequestMapping(value="/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "delIds[]") String[] delIds){
        try {
			if (delIds == null || delIds.length == 0) {
				log.info("ID invalid!");
			    sendFailureMessage(response, "ID invalid!");
			} else {
				packageDetailService.deleteDetailList(delIds,getSessionModel());
				log.info("Delete success!");
			    sendSuccessMessage(response, "Delete success!",delIds.length);
			}
		} catch (Exception e) {
			sendFailureMessage(response, "DeLete failure!");
			log.error("The exception of deleting the packageDetail :"+e);
		}
	}
	
	@RequestMapping(value="/save")
	public void save(HttpServletRequest request, HttpServletResponse response,TcFlowpackageDetail bean){
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
			if(StringUtil.isNull(bean.getParentid())){
				bean.setParentid("-1");
			}
			if(StringUtil.isNull(bean.getPackagedetailid())){
				bean.setPackagedetailid(IdGenerator.getUUID());
				flag = packageDetailService.saveDetail(bean);
			}else{
				flag = packageDetailService.modifyDetail(bean);
			}
			sendSuccessMessage(response,"Save success!");
		} catch (Exception e) {
			sendFailureMessage(response,"Save failed!");
			log.error("The exception of saving the packageDetail :"+e);
		}
	}		
}
