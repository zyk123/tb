package com.flash.toolbar.omp.role.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.IdGenerator;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.QxMenuPrivilege;
import com.flash.toolbar.omp.model.QxRole;
import com.flash.toolbar.omp.model.QxRoleMenuPrivilege;
import com.flash.toolbar.omp.role.servcie.QxRoleService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="qxRole")
public class QxRoleController extends BaseAction{
	
	private static final Log log = LogFactory.getLog(QxRoleController.class);
	
	@Resource
	private QxRoleService qxRoleService;
	
	/**
	 * 根据角色检索菜单权限
	 * @param roleMenuPrivilege
	 * @return
	 */
	@RequestMapping(value="selectMenuPrivilegeByRole",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, List<QxMenuPrivilege>> selectMenuPrivilegeByRole(QxRoleMenuPrivilege roleMenuPrivilege){
		Map<String, List<QxMenuPrivilege>> map = new HashMap<String, List<QxMenuPrivilege>>();
		QxUserModel userModel = getSessionModel();
		roleMenuPrivilege.setCountryno(userModel.getBean().getCountryno());
		roleMenuPrivilege.setToperatorid(userModel.getBean().getToperatorid());
		try {
			map.put("menu", qxRoleService.selectMenuByRole(roleMenuPrivilege));
		} catch (Exception e) {
			log.error("extract menu by role error :" + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 更新所属角色的菜单与权限
	 * @param roleMenuPrivilege
	 * @param roleMenuPrivileges
	 * @return
	 */
	@RequestMapping(value="updateRoleMenuPrivilege",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateRoleMenuPrivilege(String roleid,@RequestParam(required=false,defaultValue="")String[] mps){
		Map<String, String> map = new HashMap<String, String>();
		List<QxRoleMenuPrivilege> rmpList = new ArrayList<QxRoleMenuPrivilege>();
		QxUserModel userModel = getSessionModel();
		QxRoleMenuPrivilege qrmp = new QxRoleMenuPrivilege();
		qrmp.setCountryno(userModel.getBean().getCountryno());
		qrmp.setToperatorid(userModel.getBean().getToperatorid());
		qrmp.setRoleid(roleid);
		for (int i = 0; i < mps.length; i++) {
			String[] arr = mps[i].split("-");
			QxRoleMenuPrivilege rmp = new QxRoleMenuPrivilege();
			rmp.setCountryno(userModel.getBean().getCountryno());
			rmp.setToperatorid(userModel.getBean().getToperatorid());
			rmp.setRmpid(IdGenerator.getUUID());
			rmp.setRoleid(roleid);
			rmp.setMenuid(arr[0]);
			rmp.setPrivilegeid(arr[1]);
			rmp.setCreatedate(DateUtil.getTimeZoneDate(new Date(), userModel.getTimeZone(), "yyyyMMdd"));
			rmpList.add(rmp);
		}
		try {
			if(qxRoleService.updateRoleMenuPrivilege(qrmp,rmpList)){
				map.put("retCode", "0");
			}
		} catch (Exception e) {
			log.error("update menu and privilege that associates role error :" + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 主键查询角色
	 * @param role
	 * @return
	 */
	@RequestMapping(value="selectRole")
	@ResponseBody
	public ModelMap selectRole(QxRole role){
		ModelMap map = new ModelMap();
		if(null == role || "".equals(role.getRoleid())){
			return map;
		}
		QxUserModel userModel = getSessionModel();
		role.setCountryno(userModel.getBean().getCountryno());
		role.setToperatorid(userModel.getBean().getToperatorid());
		try {
			map.put("role", qxRoleService.selectByPrimaryKey(role));
		} catch (Exception e) {
			log.error("select role occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 分页查询角色列表
	 * @param role
	 * @param page
	 * @return
	 */
	@RequestMapping(value="selectRoleList")
	public ModelAndView selectRoleList(QxRole role,Page page){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/roleManagement");
		QxUserModel userModel = getSessionModel();
		role.setCountryno(userModel.getBean().getCountryno());
		role.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(null != role.getRolename()){
				role.setRolename(new String(role.getRolename().getBytes("ISO-8859-1"),"UTF-8"));
			}			
			mav.addObject("list", qxRoleService.selectRoleListByPage(role, page));
			mav.addObject("page", page);
		} catch (Exception e) {
			log.error("paging select role occurs error: " + e.getLocalizedMessage());
		}
		return mav;
	}
	
	/**
	 * 新增角色
	 * @param user
	 * @return
	 */
	@RequestMapping(value="insertRole")
	@ResponseBody
	public Map<String, String> insertRole(QxRole role){
		Map<String, String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		role.setRoleno(DateUtil.getTimeZoneStr(new Date(), userModel.getTimeZone(), "yyyyMMdd") + "0");
		role.setRoleid(IdGenerator.getUUID());
		role.setCountryno(userModel.getBean().getCountryno());
		role.setToperatorid(userModel.getBean().getToperatorid());
		role.setCreatedate(DateUtil.getTimeZoneDate(new Date(), userModel.getTimeZone(), "yyyyMMdd"));
		try {
			if(qxRoleService.insertRole(role)){
				map.put("retCode", "0");
			}else {
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("add role occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	@RequestMapping(value="updateRole")
	@ResponseBody
	public Map<String, String> updateRole(QxRole role){
		Map<String, String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		role.setCountryno(userModel.getBean().getCountryno());
		role.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(qxRoleService.updateRole(role)){
				map.put("retCode", "0");
			}else {
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("edit role occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 删除角色
	 * @param role
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="deleteRole")
	@ResponseBody
	public Map<String, String> deleteRole(QxRole role,String[] ids){
		Map<String, String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		role.setCountryno(userModel.getBean().getCountryno());
		role.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(qxRoleService.deleteRole(ids,role)){
				map.put("retCode", "0");
			}else {
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("delete role occurs error: " + e.getLocalizedMessage());
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
	public ModelMap check(QxRole role){
		ModelMap map = new ModelMap();
		QxUserModel userModel = getSessionModel();
		role.setCountryno(userModel.getBean().getCountryno());
		role.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(qxRoleService.isExsit(role)){
				map.put("retCode", "0");//存在
			}
		} catch (Exception e) {
			log.error("Detect whether there is the same name occurs error : " + e.getLocalizedMessage());
		}
		return map;
	}
}
