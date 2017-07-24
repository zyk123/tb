package com.flash.toolbar.omp.menu.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.IdGenerator;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.menu.service.QxMenuService;
import com.flash.toolbar.omp.model.QxMenu;
import com.flash.toolbar.omp.model.QxMenuPrivilege;
import com.flash.toolbar.omp.model.QxPrivilege;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="qxMenu")
public class QxMenuController extends BaseAction{

private static final Log log = LogFactory.getLog(QxMenuController.class);
	
	@Resource
	private QxMenuService qxMenuService;
	
	/**
	 * 检索单个菜单
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="selectMenu")
	@ResponseBody
	public ModelMap selectMenuByPrimaryKey(QxMenu menu){
		ModelMap map = new ModelMap();
		QxUserModel userModel = getSessionModel();
		menu.setCountryno(userModel.getBean().getCountryno());
		menu.setToperatorid(userModel.getBean().getToperatorid());
		try {
			map.put("menu", qxMenuService.selectMenuByPrimaryKey(menu));
		} catch (Exception e) {
			log.error("single menu retrieves error:" + e.getLocalizedMessage());
		}
		return map;
	}
	/**
	 * 检索父级菜单
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="selectParentMenu")
	@ResponseBody
	public ModelMap selectParentMenu(QxMenu menu){
		ModelMap map = new ModelMap();
		QxUserModel userModel = getSessionModel();
		menu.setCountryno(userModel.getBean().getCountryno());
		menu.setToperatorid(userModel.getBean().getToperatorid());
		try {
			map.put("menu", qxMenuService.selectParentMenu(menu));
		} catch (Exception e) {
			log.error("parent menu retrieves error:" + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 查询菜单列表
	 * @param menu
	 * @param page
	 * @return
	 */
	@RequestMapping(value="selectMenuList")
	public ModelAndView selectMenuListByPage(QxMenu menu,Page page){
		ModelAndView mav = new ModelAndView();
		QxUserModel userModel = getSessionModel();
		menu.setCountryno(userModel.getBean().getCountryno());
		menu.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(null != menu.getMenuname()){
				menu.setMenuname(new String(menu.getMenuname().getBytes("ISO-8859-1"), "UTF-8"));
			}
			mav.addObject("list", qxMenuService.selectMenuListByPage(menu, page));
			mav.addObject("page", page);
		} catch (Exception e) {
			log.error("menu list selects error:" + e.getLocalizedMessage());
		}
		mav.setViewName("user/menuManagement");
		return mav;
	}
	/**
	 * 删除菜单
	 * @param array
	 * @param countryno
	 * @param toperatorid
	 * @return
	 */
	@RequestMapping(value="deleteMenu")
	@ResponseBody
	public Map<String,String> deleteMenu(String[] array){
		Map<String,String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		try {
			if(qxMenuService.deleteMenu(array, userModel.getBean().getCountryno(), userModel.getBean().getToperatorid())){
				map.put("retCode", "0");
			}else {
				map.put("retCode", "-1");			
			}
		} catch (Exception e) {
			log.error("menu deletes error:" + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 新增菜单
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="insertMenu")
	@ResponseBody
	public Map<String,String> insertMenu(QxMenu menu){
		Map<String,String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		menu.setMenuno(DateUtil.getTimeZoneStr(new Date(), userModel.getTimeZone(), "yyyyMMdd") + "0");
		menu.setMenuid(IdGenerator.getUUID());
		menu.setCountryno(userModel.getBean().getCountryno());
		menu.setToperatorid(userModel.getBean().getToperatorid());
		menu.setCreatedate(DateUtil.getTimeZoneDate(new Date(), userModel.getTimeZone(), "yyyyMMdd"));
		try {
			if(qxMenuService.insertMenu(menu)){
				map.put("retCode", "0");
			}else {
				map.put("retCode", "-1");			
			}
		} catch (Exception e) {
			log.error("menu inserts error:" + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 修改菜单
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="updateMenu")
	@ResponseBody
	public Map<String,String> updateMenu(QxMenu menu){
		Map<String,String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		menu.setCountryno(userModel.getBean().getCountryno());
		menu.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(qxMenuService.updateMenu(menu)){
				map.put("retCode", "0");
			}else {
				map.put("retCode", "-1");			
			}
		} catch (Exception e) {
			log.error("menu updates error:" + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 查询菜单关联的权限
	 * @param menuPrivilege
	 * @return
	 */
	@RequestMapping(value="selectPrivilegeByMenu")
	@ResponseBody
	public Map<String,List<QxPrivilege>> selectPrivilegeByMenu(QxMenuPrivilege menuPrivilege){
		Map<String,List<QxPrivilege>> map = new HashMap<String, List<QxPrivilege>>();
		QxUserModel userModel = getSessionModel();
		menuPrivilege.setCountryno(userModel.getBean().getCountryno());
		menuPrivilege.setToperatorid(userModel.getBean().getToperatorid());
		try {
			map.put("qxPrivilege", qxMenuService.selectPrivilegeByMenu(menuPrivilege));
		} catch (Exception e) {
			log.error("query privileges that menu associates error:" + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 更新菜单权限关联关系
	 * @param menuid
	 * @param menuids
	 * @return
	 */
	@RequestMapping(value="updateMenuPrivilege")
	@ResponseBody
	public String updateMenuPrivilege(String menuid,@RequestParam(required=false,defaultValue="")String[] privileges){
		QxUserModel userModel = getSessionModel();
		List<QxMenuPrivilege> mList = new ArrayList<QxMenuPrivilege>();
		QxMenuPrivilege mp = new QxMenuPrivilege();
		mp.setCountryno(userModel.getBean().getCountryno());
		mp.setToperatorid(userModel.getBean().getToperatorid());
		mp.setMenuid(menuid);
		for (int i = 0; i < privileges.length; i++) {
			QxMenuPrivilege menuPrivilege = new QxMenuPrivilege();
			menuPrivilege.setCountryno(userModel.getBean().getCountryno());
			menuPrivilege.setToperatorid(userModel.getBean().getToperatorid());
			menuPrivilege.setMenuprivilegeid(IdGenerator.getUUID());
			menuPrivilege.setMenuid(menuid);
			menuPrivilege.setPrivilegeid(privileges[i]);
			menuPrivilege.setCreatedate(DateUtil.getTimeZoneDate(new Date(), userModel.getTimeZone(), "yyyyMMdd"));
			mList.add(menuPrivilege);			
		}
		try {
			if(qxMenuService.updateMenuPrivilege(mp,mList)){
				return "SUCCESS";
			}
		} catch (Exception e) {
			log.error("query menu privilege associated relationship error :" + e.getLocalizedMessage());
		}
		return "ERROR";
	}
	
	/**
	 * 检测是否存在同名
	 * @param wDevice
	 * @return
	 */
	@RequestMapping(value="check")
	@ResponseBody
	public ModelMap check(QxMenu menu){
		ModelMap map = new ModelMap();
		QxUserModel userModel = getSessionModel();
		menu.setCountryno(userModel.getBean().getCountryno());
		menu.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(qxMenuService.isExsit(menu)){
				map.put("retCode", "0");//存在
			}
		} catch (Exception e) {
			log.error("Detect whether there is the same name occurs error : " + e.getLocalizedMessage());
		}
		return map;
	}
}
