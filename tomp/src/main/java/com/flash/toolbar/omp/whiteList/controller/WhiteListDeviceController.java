package com.flash.toolbar.omp.whiteList.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.IdGenerator;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.HyWhitelistDevice;
import com.flash.toolbar.omp.user.bo.QxUserModel;
import com.flash.toolbar.omp.whiteList.service.WhiteListDeviceService;

@Controller
@RequestMapping(value="whiteListDevice")
public class WhiteListDeviceController extends BaseAction {
	
	private static final Log log = LogFactory.getLog(WhiteListDeviceController.class);
	
	@Resource
	private WhiteListDeviceService whiteListDeviceService;
	
	/**
	 * 查询黑名单设备列表
	 * @param wDevice
	 * @param page
	 * @return
	 */
	@RequestMapping(value="showDeviceList")
	public ModelAndView showDeviceList(HyWhitelistDevice wDevice,Page page){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("whiteList/whiteListDevice");
		setOperatorInfo(wDevice);
		try {
			if (StringUtils.isNotEmpty(wDevice.getName())) {
				wDevice.setName(new String(wDevice.getName().getBytes(
						"ISO-8859-1"), "UTF-8"));
			}
			mv.addObject("list",whiteListDeviceService.selectByPage(wDevice, page));
		} catch (Exception e) {
			log.error("Show WhiteList Device occurs error : " + e.getLocalizedMessage());
		}
		return mv;
	}

	/**
	 * 添加黑名单设备
	 * @param wDevice
	 * @return
	 */
	@RequestMapping(value="addDevice")
	@ResponseBody
	public ModelMap addDevice(HyWhitelistDevice wDevice){
		ModelMap map = new ModelMap();
		wDevice.setId(IdGenerator.getUUID());
		setOperatorInfo(wDevice);
		wDevice.setCreatedate(DateUtil.getTimeZoneDate(new Date(),
				getSessionModel().getTimeZone(), "yyyyMMdd"));
		try {
			if(whiteListDeviceService.insert(wDevice)){
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("Add WhiteList Device occurs error : " + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 更新黑名单设备
	 * @param wDevice
	 * @return
	 */
	@RequestMapping(value="updateDevice")
	@ResponseBody
	public ModelMap updateDevice(HyWhitelistDevice wDevice){
		ModelMap map = new ModelMap();
		setOperatorInfo(wDevice);
		try {
			if(whiteListDeviceService.updateByPrimaryKey(wDevice)){
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("Update WhiteList Device occurs error : " + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 删除黑名单设备
	 * @param array
	 * @return
	 */
	@RequestMapping(value="delDevice")
	@ResponseBody
	public ModelMap delDevice(String[] array){
		ModelMap map = new ModelMap();
		HyWhitelistDevice wDevice = new HyWhitelistDevice();
		setOperatorInfo(wDevice);
		try {
			if(whiteListDeviceService.deleteByPrimaryKey(wDevice, array)){
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("Delete WhiteList Device occurs error : " + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 检测是否存在同名
	 * @param wDevice
	 * @return
	 */
	@RequestMapping(value="check")
	@ResponseBody
	public ModelMap check(HyWhitelistDevice wDevice){
		ModelMap map = new ModelMap();
		setOperatorInfo(wDevice);
		try {
			if(whiteListDeviceService.isExsit(wDevice)){
				map.put("retCode", "0");//存在
			}
		} catch (Exception e) {
			log.error("Detect whether there is the same name occurs error : " + e.getLocalizedMessage());
		}
		return map;
	}
	
	private void setOperatorInfo(HyWhitelistDevice wDevice) {
		QxUserModel userModel = getSessionModel();
		wDevice.setCountryno(userModel.getBean().getCountryno());
		wDevice.setToperatorid(userModel.getBean().getToperatorid());
	}
	
}
