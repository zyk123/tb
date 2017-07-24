package com.flash.toolbar.omp.blacklist.controller;

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

import com.flash.toolbar.omp.blacklist.service.BlackListDeviceService;
import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.IdGenerator;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.HyBlacklistDevice;
import com.flash.toolbar.omp.model.HyWhitelistDevice;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="blackListDevice")
public class BlackListDeviceController extends BaseAction {
	
	private static final Log log = LogFactory.getLog(BlackListDeviceController.class);
	
	@Resource
	private BlackListDeviceService blackListDeviceService;
	
	/**
	 * 查询黑名单设备列表
	 * @param bDevice
	 * @param page
	 * @return
	 */
	@RequestMapping(value="showDeviceList")
	public ModelAndView showDeviceList(HyBlacklistDevice bDevice,Page page){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("blackList/blackListDevice");
		setOperatorInfo(bDevice);
		try {
			if (StringUtils.isNotEmpty(bDevice.getName())) {
				bDevice.setName(new String(bDevice.getName().getBytes(
						"ISO-8859-1"), "UTF-8"));
			}
			mv.addObject("list",blackListDeviceService.selectByPage(bDevice, page));
		} catch (Exception e) {
			log.error("Show BlackList Device occurs error : " + e.getLocalizedMessage());
		}
		return mv;
	}

	/**
	 * 添加黑名单设备
	 * @param bDevice
	 * @return
	 */
	@RequestMapping(value="addDevice")
	@ResponseBody
	public ModelMap addDevice(HyBlacklistDevice bDevice){
		ModelMap map = new ModelMap();
		bDevice.setId(IdGenerator.getUUID());
		setOperatorInfo(bDevice);
		bDevice.setCreatedate(DateUtil.getTimeZoneDate(new Date(),
				getSessionModel().getTimeZone(), "yyyyMMdd"));
		try {
			if(blackListDeviceService.insert(bDevice)){
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("Add BlackList Device occurs error : " + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 更新黑名单设备
	 * @param bDevice
	 * @return
	 */
	@RequestMapping(value="updateDevice")
	@ResponseBody
	public ModelMap updateDevice(HyBlacklistDevice bDevice){
		ModelMap map = new ModelMap();
		setOperatorInfo(bDevice);
		try {
			if(blackListDeviceService.updateByPrimaryKey(bDevice)){
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("Update BlackList Device occurs error : " + e.getLocalizedMessage());
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
		HyBlacklistDevice bDevice = new HyBlacklistDevice();
		setOperatorInfo(bDevice);
		try {
			if(blackListDeviceService.deleteByPrimaryKey(bDevice, array)){
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("Delete BlackList Device occurs error : " + e.getLocalizedMessage());
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
	public ModelMap check(HyBlacklistDevice bDevice){
		ModelMap map = new ModelMap();
		setOperatorInfo(bDevice);
		try {
			if(blackListDeviceService.isExsit(bDevice)){
				map.put("retCode", "0");//存在
			}
		} catch (Exception e) {
			log.error("Detect whether there is the same name occurs error : " + e.getLocalizedMessage());
		}
		return map;
	}
	
	private void setOperatorInfo(HyBlacklistDevice bDevice) {
		QxUserModel userModel = getSessionModel();
		bDevice.setCountryno(userModel.getBean().getCountryno());
		bDevice.setToperatorid(userModel.getBean().getToperatorid());
	}
	
}
