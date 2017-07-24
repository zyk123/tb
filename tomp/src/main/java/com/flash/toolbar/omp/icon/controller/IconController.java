package com.flash.toolbar.omp.icon.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.flash.toolbar.omp.redis.RedisOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.flash.toolbar.omp.common.util.IdGenerator;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.icon.service.IconService;
import com.flash.toolbar.omp.model.SysMainInterfaceIconConfig;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="icon")
public class IconController extends BaseAction{
	
	private static final Log log = LogFactory.getLog(IconController.class);
	
	@Resource
	private IconService iconService;

	@Autowired
	private RedisOperation redisOperation;
	
	/**
	 * 展示图标列表
	 * @param config
	 * @param page
	 * @return
	 */
	@RequestMapping(value="showIconList")
	public ModelAndView showIconList(SysMainInterfaceIconConfig config,Page page){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("icon/icon");
		setOperatorInfo(config);
		try {
			mv.addObject("list", iconService.selectIconByPage(config, page));
		} catch (Exception e) {
			log.error("Show Icon List occurs error : " + e.getLocalizedMessage());
		}
		return mv;
	}
	
	@RequestMapping(value="readIcon")
	public void readIcon(HttpServletResponse response,SysMainInterfaceIconConfig config){
		setOperatorInfo(config);
		try {
			config = iconService.selectIconByPrimaryKey(config);
			ServletOutputStream out = response.getOutputStream();
			int off = 0;
			int len = 1024;
			do{
				out.write(config.getIcon(), off, len);
				off = off + len;
			}while(off+len < config.getIcon().length);
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("Read Icon occurs error : " + e.getLocalizedMessage());
		}
	}

	@RequestMapping(value="readPopup")
	public void readPopup(HttpServletResponse response,SysMainInterfaceIconConfig config){
		setOperatorInfo(config);
		try {
			config = iconService.selectPopupByPrimaryKey(config);
			ServletOutputStream out = response.getOutputStream();
			int off = 0;
			int len = 1024;
			do{
				out.write(config.getPopup(), off, len);
				off = off + len;
			}while(off+len < config.getPopup().length);
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("Read Popup occurs error : " + e.getLocalizedMessage());
		}
	}
	
	/**
	 * 上传图标
	 * @param file
	 * @param config
	 * @return
	 */
	@RequestMapping(value="iconUpload")
	@ResponseBody
	public String iconUpload(@RequestParam(value = "file") MultipartFile[] file, SysMainInterfaceIconConfig config){
		ModelMap map = new ModelMap();
		try {
			if(file.length > 0){
				config.setId(IdGenerator.getUUID());
				if(!file[0].isEmpty()){
					config.setIcon(file[0].getBytes());
				}
				if(file.length==2){
					if(!file[1].isEmpty()){
						config.setPopup(file[1].getBytes());
					}
				}
				setOperatorInfo(config);
				config.setCreatedate(DateUtil.getTimeZoneDate(new Date(),
						getSessionModel().getTimeZone(), "yyyyMMdd"));
				map.put("retCode",iconService.insertIcon(config));
			}
		} catch (Exception e) {
			log.error("Upload Icon occurs error : " + e.getLocalizedMessage());
		}
		return "<script>parent.callback(" + JSONObject.toJSONString(map) + ")</script>";
	}
	
	/**
	 * 更新图标
	 * @param file
	 * @return
	 */
	@RequestMapping(value="updateIcon")
	@ResponseBody
	public String updateIcon(@RequestParam(value = "file") MultipartFile[] file,SysMainInterfaceIconConfig config){
		ModelMap map = new ModelMap();
		try {
			if(file.length > 0) {
				if (!file[0].isEmpty()) {
					config.setIcon(file[0].getBytes());
				}
				if(file.length==2) {
					if (!file[1].isEmpty()) {
						config.setPopup(file[1].getBytes());
					}
				}
			}
			setOperatorInfo(config);
			iconService.updateIcon(config);
			map.put("retCode", iconService.updateIcon(config));
		} catch (Exception e) {
			log.error("Update Icon occurs error : " + e.getLocalizedMessage());
		}
		return "<script>parent.callback(" + JSONObject.toJSONString(map) + ")</script>";
	}
	
	/**
	 * 删除图标
	 * @param array
	 * @return
	 */
	@RequestMapping(value="delIcon")
	@ResponseBody
	public ModelMap delIcon(String[] array){
		SysMainInterfaceIconConfig config = new SysMainInterfaceIconConfig();
		ModelMap map = new ModelMap();
		setOperatorInfo(config);
		try {
			if(array.length > 0){
				map.put("retCode", iconService.deleteIcon(config, array));
			}
		} catch (Exception e) {
			log.error("Delete Icon occurs error : " + e.getLocalizedMessage());
		}
		return map;
	}

	@RequestMapping(value = "syncIcon")
	@ResponseBody
	public String syncIcon(SysMainInterfaceIconConfig config){
		try{
			if(redisOperation.isExistKey("pre_sys_icon")){
				redisOperation.del("pre_sys_icon");
				setOperatorInfo(config);
				config.setType("1");
				redisOperation.add("pre_sys_icon",JSONObject.toJSONString(iconService.selectAllIcon(config)));
			}else{
				setOperatorInfo(config);
				config.setType("1");
				redisOperation.add("pre_sys_icon",JSONObject.toJSONString(iconService.selectAllIcon(config)));
			}
			if(redisOperation.isExistKey("post_sys_icon")){
				redisOperation.del("post_sys_icon");
				setOperatorInfo(config);
				config.setType("2");
				redisOperation.add("post_sys_icon",JSONObject.toJSONString(iconService.selectAllIcon(config)));
			}else{
				setOperatorInfo(config);
				config.setType("2");
				redisOperation.add("post_sys_icon",JSONObject.toJSONString(iconService.selectAllIcon(config)));
			}
		}catch (Exception e){
			log.error("Sync Icon failed : " + e.getLocalizedMessage());
			return "ERROR";
		}
		return "SUCCESS";
	}
	
	private void setOperatorInfo(SysMainInterfaceIconConfig config) {
		QxUserModel userModel = getSessionModel();
		config.setCountryno(userModel.getBean().getCountryno());
		config.setToperatorid(userModel.getBean().getToperatorid());
	}

}
