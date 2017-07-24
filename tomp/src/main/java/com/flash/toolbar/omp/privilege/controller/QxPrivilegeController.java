package com.flash.toolbar.omp.privilege.controller;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

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
import com.flash.toolbar.omp.model.QxMenu;
import com.flash.toolbar.omp.model.QxPrivilege;
import com.flash.toolbar.omp.privilege.service.QxPrivilegeServcie;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="qxPrivilege")
public class QxPrivilegeController extends BaseAction{
	
	private static final Log log = LogFactory.getLog(QxPrivilegeController.class);
	
	@Resource
	private QxPrivilegeServcie qxPrivilegeService;
	
	/**
	 * 检索单个权限
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value="selectPrivilege")
	@ResponseBody
	public ModelMap selectPrivilegeByPrimaryKey(QxPrivilege privilege){
		ModelMap map = new ModelMap();
		QxUserModel userModel = getSessionModel();
		privilege.setCountryno(userModel.getBean().getCountryno());
		privilege.setToperatorid(userModel.getBean().getToperatorid());
		try {
			map.put("privilege", qxPrivilegeService.selectPrivilegeByPrimaryKey(privilege));
		} catch (Exception e) {
			log.error("single privilege retrieves error:" + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 查询权限列表
	 * @param privilege
	 * @param page
	 * @return
	 */
	@RequestMapping(value="selectPrivilegeList")
	public ModelAndView selectPrivilegeListByPage(QxPrivilege privilege,Page page){
		ModelAndView mav = new ModelAndView();
		QxUserModel userModel = getSessionModel();
		privilege.setCountryno(userModel.getBean().getCountryno());
		privilege.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(null != privilege.getPrivilegename()){
				privilege.setPrivilegename(new String(privilege.getPrivilegename().getBytes("ISO-8859-1"),"UTF-8"));
			}	
			mav.addObject("list", qxPrivilegeService.selectPrivilegeListByPage(privilege, page));
			mav.addObject("page",page);
		} catch (Exception e) {
			log.error("privilege list selects error:" + e.getLocalizedMessage());
		}
		mav.setViewName("user/privilegeManagement");
		return mav;
	}
	/**
	 * 删除权限
	 * @param array
	 * @param countryno
	 * @param toperatorid
	 * @return
	 */
	@RequestMapping(value="deletePrivilege")
	@ResponseBody
	public Map<String,String> deletePrivilege(String[] ids){
		Map<String,String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		try {
			if(qxPrivilegeService.deletePrivilege(ids, userModel.getBean().getCountryno(), userModel.getBean().getToperatorid())){
				map.put("retCode", "0");
			}else {
				map.put("retCode", "-1");			
			}
		} catch (Exception e) {
			log.error("privilege deletes error:" + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 新增权限
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value="insertPrivilege")
	@ResponseBody
	public Map<String,String> insertPrivilege(QxPrivilege privilege){
		Map<String,String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		privilege.setPrivilegeno(DateUtil.getTimeZoneStr(new Date(), userModel.getTimeZone(), "yyyyMMdd") + "0");
		privilege.setPrivilegeid(IdGenerator.getUUID());
		privilege.setCountryno(userModel.getBean().getCountryno());
		privilege.setToperatorid(userModel.getBean().getToperatorid());
		privilege.setCreatedate(DateUtil.getTimeZoneDate(new Date(), userModel.getTimeZone(), "yyyyMMdd"));
		try {
			if(qxPrivilegeService.insertPrivilege(privilege)){
				map.put("retCode", "0");
			}else {
				map.put("retCode", "-1");			
			}
		} catch (Exception e) {
			log.error("privilege inserts error:" + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 修改权限
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value="updatePrivilege")
	@ResponseBody
	public Map<String,String> updatePrivilege(QxPrivilege privilege){
		Map<String,String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		privilege.setCountryno(userModel.getBean().getCountryno());
		privilege.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(qxPrivilegeService.updatePrivilege(privilege)){
				map.put("retCode", "0");
			}else {
				map.put("retCode", "-1");			
			}
		} catch (Exception e) {
			log.error("privilege updates error:" + e.getLocalizedMessage());
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
	public ModelMap check(QxPrivilege privilege){
		ModelMap map = new ModelMap();
		QxUserModel userModel = getSessionModel();
		privilege.setCountryno(userModel.getBean().getCountryno());
		privilege.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(qxPrivilegeService.isExsit(privilege)){
				map.put("retCode", "0");//存在
			}
		} catch (Exception e) {
			log.error("Detect whether there is the same name occurs error : " + e.getLocalizedMessage());
		}
		return map;
	}
}
