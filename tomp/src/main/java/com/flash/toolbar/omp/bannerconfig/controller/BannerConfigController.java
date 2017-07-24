package com.flash.toolbar.omp.bannerconfig.controller;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
import com.flash.toolbar.omp.bannerconfig.service.BannerConfigService;
import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.IdGenerator;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.PzBannerConfig;
import com.flash.toolbar.omp.redis.RedisOperation;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="bannerconfig")
public class BannerConfigController extends BaseAction{
	
	private static final Log log = LogFactory.getLog(BannerConfigController.class);
	
	@Resource
	private BannerConfigService bannerConfigService;

	@Autowired
	private RedisOperation redisOperation;
	
	/**
	 * 展示图标列表
	 * @param config
	 * @param page
	 * @return
	 */
	@RequestMapping(value="showBannerconfigList")
	public ModelAndView showBannerconfigList(PzBannerConfig config,Page page){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bannerconfig/bannerlist");
		setOperatorInfo(config);
		if(config.getPagetype()==null){
			config.setPagetype(new BigDecimal(1));
		}
		mv.addObject("pagetype", config.getPagetype());
		try {
			mv.addObject("list", bannerConfigService.selectBannerconfigByPage(config, page));
		} catch (Exception e) {
			log.error("Show Bannerconfig List occurs error : " + e.getLocalizedMessage());
		}
		return mv;
	}
	
	@RequestMapping(value="readBannerconfig")
	public void readBannerconfig(HttpServletResponse response,PzBannerConfig config){
		setOperatorInfo(config);
		try {
			config = bannerConfigService.selectBannerconfigByPrimaryKey(config);
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
			log.error("Read Bannerconfig occurs error : " + e.getLocalizedMessage());
		}
	}

	/**
	 * 上传图标
	 * @param file
	 * @param config
	 * @return
	 */
	@RequestMapping(value="bannerconfigUpload")
	@ResponseBody
	public String BannerconfigUpload(@RequestParam(value = "file") MultipartFile[] file, PzBannerConfig config){
		ModelMap map = new ModelMap();
		try {
			if(file.length > 0){
				config.setId(IdGenerator.getUUID());
				if(!file[0].isEmpty()){
					config.setIcon(file[0].getBytes());
				}
				setOperatorInfo(config);
				map.put("retCode",bannerConfigService.insertBannerconfig(config));
			}
		} catch (Exception e) {
			log.error("Upload Bannerconfig occurs error : " + e.getLocalizedMessage());
		}
		return "<script>parent.callback(" + JSONObject.toJSONString(map) + ")</script>";
	}
	
	/**
	 * 更新图标
	 * @param file
	 * @return
	 */
	@RequestMapping(value="updateBannerconfig")
	@ResponseBody
	public String updateBannerconfig(@RequestParam(value = "file") MultipartFile[] file,PzBannerConfig config){
		ModelMap map = new ModelMap();
		try {
			if(file.length > 0) {
				if (!file[0].isEmpty()) {
					config.setIcon(file[0].getBytes());
				}
			}
			setOperatorInfo(config);
			bannerConfigService.updateBannerconfig(config);
			map.put("retCode", bannerConfigService.updateBannerconfig(config));
		} catch (Exception e) {
			log.error("Update Bannerconfig occurs error : " + e.getLocalizedMessage());
		}
		return "<script>parent.callback(" + JSONObject.toJSONString(map) + ")</script>";
	}
	
	/**
	 * 删除图标
	 * @param array
	 * @return
	 */
	@RequestMapping(value="delBannerconfig")
	@ResponseBody
	public ModelMap delBannerconfig(String[] array){
		PzBannerConfig config = new PzBannerConfig();
		ModelMap map = new ModelMap();
		setOperatorInfo(config);
		try {
			if(array.length > 0){
				map.put("retCode", bannerConfigService.deleteBannerconfig(config, array));
			}
		} catch (Exception e) {
			log.error("Delete Bannerconfig occurs error : " + e.getLocalizedMessage());
		}
		return map;
	}

//	@RequestMapping(value = "syncBannerconfig")
//	@ResponseBody
//	public String syncBannerconfig(PzBannerConfig config){
//		try{
//			if(redisOperation.isExistKey("pre_sys_Bannerconfig")){
//				redisOperation.del("pre_sys_Bannerconfig");
//				setOperatorInfo(config);
//				config.setType("1");
//				redisOperation.add("pre_sys_Bannerconfig",JSONObject.toJSONString(bannerConfigService.selectAllBannerconfig(config)));
//			}else{
//				setOperatorInfo(config);
//				config.setType("1");
//				redisOperation.add("pre_sys_Bannerconfig",JSONObject.toJSONString(bannerConfigService.selectAllBannerconfig(config)));
//			}
//			if(redisOperation.isExistKey("post_sys_Bannerconfig")){
//				redisOperation.del("post_sys_Bannerconfig");
//				setOperatorInfo(config);
//				config.setType("2");
//				redisOperation.add("post_sys_Bannerconfig",JSONObject.toJSONString(bannerConfigService.selectAllBannerconfig(config)));
//			}else{
//				setOperatorInfo(config);
//				config.setType("2");
//				redisOperation.add("post_sys_Bannerconfig",JSONObject.toJSONString(bannerConfigService.selectAllBannerconfig(config)));
//			}
//		}catch (Exception e){
//			log.error("Sync Bannerconfig failed : " + e.getLocalizedMessage());
//			return "ERROR";
//		}
//		return "SUCCESS";
//	}
	
	private void setOperatorInfo(PzBannerConfig config) {
		QxUserModel userModel = getSessionModel();
		config.setCountryno(userModel.getBean().getCountryno());
		config.setToperatorid(userModel.getBean().getToperatorid());
	}

}
