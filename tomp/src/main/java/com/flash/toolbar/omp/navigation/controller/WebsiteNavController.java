package com.flash.toolbar.omp.navigation.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.HtmlUtil;
import com.flash.toolbar.omp.common.util.IdGenerator;
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.model.HyWebsiteNavigation;
import com.flash.toolbar.omp.navigation.bo.HyWebsiteNavigationModel;
import com.flash.toolbar.omp.navigation.service.WebsiteNavService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="/websiteContent")
public class WebsiteNavController extends BaseAction{
	private static final Logger log = Logger.getLogger(WebsiteNavController.class);
	
	@RequestMapping(value="/show")
	public String show(){
		return "navigation/websiteNav";
	}
	
	@Resource
	private WebsiteNavService websiteNavService;
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,HyWebsiteNavigationModel model){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				model.getBean().setCountryno(qxUserModel.getBean().getCountryno());
				model.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<HyWebsiteNavigation> list = websiteNavService.getWebsiteNavList(model);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("page", model.getPager());
			HtmlUtil.writerJson(response, map);
		} catch (Exception e) {
			log.error("The exception of querying the gameNavList :"+e);
		}
	}
	
	
	/**
	 * 上传图标
	 * @param file
	 * @param config
	 * @return
	 */
	@RequestMapping(value="upload")
	@ResponseBody
	public String upload(@RequestParam(value = "file") MultipartFile file, HyWebsiteNavigation hyWebsiteNavigation){
		ModelMap map = new ModelMap();
		try {
			boolean flag = false;
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				hyWebsiteNavigation.setCountryno(qxUserModel.getBean().getCountryno());
				hyWebsiteNavigation.setToperatorid(qxUserModel.getBean().getToperatorid());
				hyWebsiteNavigation.setModifyman(qxUserModel.getBean().getUserid());
			}
			hyWebsiteNavigation.setModifydate(DateUtil.getTimeZoneDate(new Date(),
					getSessionModel().getTimeZone(), "yyyy-MM-dd HH:mm:ss"));
			if(StringUtil.isNull(hyWebsiteNavigation.getId())){
				hyWebsiteNavigation.setId(IdGenerator.getUUID());
				if("1".equals(hyWebsiteNavigation.getType())){
					if(file==null || file.isEmpty()){
						log.info("File can not be empty!");
						return "<script>parent.callback(" + JSONObject.toJSONString(map) + ")</script>";
					}
					hyWebsiteNavigation.setIcon(file.getBytes());
				}
				flag = websiteNavService.insertIcon(hyWebsiteNavigation);
			}else{
				if("1".equals(hyWebsiteNavigation.getType())){
					if(file!=null && !file.isEmpty()){
						hyWebsiteNavigation.setIcon(file.getBytes());
					}					
				}
				flag = websiteNavService.modifyWebsiteNav(hyWebsiteNavigation);
			}
			map.put("retCode",flag);
		} catch (Exception e) {
			log.error("Upload Icon occurs error : " + e.getLocalizedMessage());
		}
		return "<script>parent.callback(" + JSONObject.toJSONString(map) + ")</script>";
	}
	
	
	@RequestMapping(value="readIcon")
	public void readIcon(HttpServletResponse response,HyWebsiteNavigation hyWebsiteNavigation){
		QxUserModel qxUserModel = getSessionModel();
		if(qxUserModel!=null){
			hyWebsiteNavigation.setCountryno(qxUserModel.getBean().getCountryno());
			hyWebsiteNavigation.setToperatorid(qxUserModel.getBean().getToperatorid());
			hyWebsiteNavigation.setModifyman(qxUserModel.getBean().getUserid());
		}
		try {
			hyWebsiteNavigation = websiteNavService.selectIconByPrimaryKey(hyWebsiteNavigation);
			ServletOutputStream out = response.getOutputStream();
			int off = 0;
			int len = 1024;
			do{
				out.write(hyWebsiteNavigation.getIcon(), off, len);
				off = off + len;
			}while(off+len < hyWebsiteNavigation.getIcon().length);
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("Read Icon occurs error : " + e.getLocalizedMessage());
		}
	}
	
	
	@RequestMapping(value="/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "id") String id,@RequestParam(value = "orderCount") int orderCount,@RequestParam(value = "orderNo") int orderNo){
		try {
			if (StringUtil.isNull(id)) {
				log.info("ID invalid!");
			    sendFailureMessage(response, "ID invalid!");
			} else {
				websiteNavService.deleteWebsiteNav(id,orderCount,orderNo);
				log.info("Delete success!");
			    sendSuccessMessage(response, "Delete success!",1);
			}
		} catch (Exception e) {
			sendFailureMessage(response, "DeLete failure!");
			log.error("The exception of deleting the gameNav :"+e);
		}
	}
}
