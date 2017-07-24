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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.HtmlUtil;
import com.flash.toolbar.omp.common.util.IdGenerator;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.model.HyGameNavigation;
import com.flash.toolbar.omp.model.SysMainInterfaceIconConfig;
import com.flash.toolbar.omp.navigation.bo.HyGameNavigationModel;
import com.flash.toolbar.omp.navigation.service.GameNavService;
import com.flash.toolbar.omp.user.bo.QxUserModel;
@Controller
@RequestMapping(value="/navContent")
public class GameNavController extends BaseAction{
	private static final Logger log = Logger.getLogger(GameNavController.class);
	
	@RequestMapping(value="/show")
	public String show(){
		return "navigation/gameNav";
	}
	
	@Resource
	private GameNavService gameNavService;
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,HyGameNavigationModel model){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				model.getBean().setCountryno(qxUserModel.getBean().getCountryno());
				model.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<HyGameNavigation> list = gameNavService.getGameNavList(model);
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
	public String upload(@RequestParam(value = "file") MultipartFile file, HyGameNavigation hyGameNavigation){
		ModelMap map = new ModelMap();
		try {
			boolean flag = false;
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				hyGameNavigation.setCountryno(qxUserModel.getBean().getCountryno());
				hyGameNavigation.setToperatorid(qxUserModel.getBean().getToperatorid());
				hyGameNavigation.setModifyman(qxUserModel.getBean().getUserid());
			}
			hyGameNavigation.setModifydate(DateUtil.getTimeZoneDate(new Date(),
					getSessionModel().getTimeZone(), "yyyy-MM-dd HH:mm:ss"));
			if(StringUtil.isNull(hyGameNavigation.getId())){
				hyGameNavigation.setId(IdGenerator.getUUID());
					if(file==null || file.isEmpty()){
						log.info("File can not be empty!");
						return "<script>parent.callback(" + JSONObject.toJSONString(map) + ")</script>";
					}
					hyGameNavigation.setIcon(file.getBytes());
				flag = gameNavService.insertIcon(hyGameNavigation);
			}else{
					if(file!=null && !file.isEmpty()){
						hyGameNavigation.setIcon(file.getBytes());
					}					
				flag = gameNavService.modifyGameNav(hyGameNavigation);
			}
			map.put("retCode",flag);
		} catch (Exception e) {
			log.error("Upload Icon occurs error : " + e.getLocalizedMessage());
		}
		return "<script>parent.callback(" + JSONObject.toJSONString(map) + ")</script>";
	}
	
	
	@RequestMapping(value="readIcon")
	public void readIcon(HttpServletResponse response,HyGameNavigation hyGameNavigation){
		QxUserModel qxUserModel = getSessionModel();
		if(qxUserModel!=null){
			hyGameNavigation.setCountryno(qxUserModel.getBean().getCountryno());
			hyGameNavigation.setToperatorid(qxUserModel.getBean().getToperatorid());
			hyGameNavigation.setModifyman(qxUserModel.getBean().getUserid());
		}
		try {
			hyGameNavigation = gameNavService.selectIconByPrimaryKey(hyGameNavigation);
			ServletOutputStream out = response.getOutputStream();
			int off = 0;
			int len = 1024;
			do{
				out.write(hyGameNavigation.getIcon(), off, len);
				off = off + len;
			}while(off+len < hyGameNavigation.getIcon().length);
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
				gameNavService.deleteGameNav(id,orderCount,orderNo);
				log.info("Delete success!");
			    sendSuccessMessage(response, "Delete success!",1);
			}
		} catch (Exception e) {
			sendFailureMessage(response, "DeLete failure!");
			log.error("The exception of deleting the gameNav :"+e);
		}
	}
}
