package com.flash.toolbar.omp.user.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.io.Configuration;
import com.flash.toolbar.omp.common.util.Constant;
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.IdGenerator;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.common.util.SessionUtil;
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.model.AuthorizedMenuPrivilege;
import com.flash.toolbar.omp.model.QxRole;
import com.flash.toolbar.omp.model.QxUser;
import com.flash.toolbar.omp.model.QxUserRole;
import com.flash.toolbar.omp.user.bo.QxUserModel;
import com.flash.toolbar.omp.user.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController extends BaseAction{
	
	private Logger log = Logger.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value="/login")
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response,QxUser qxUserBean){
			try {
//				qxUserBean.setUsername("admin");
//				qxUserBean.setPassword("admin");
//				String userName = qxUserBean.getUsername();
//				if(!StringUtil.isNull(userName)){
					HttpSession session = request.getSession();
//					String isCanLoginMsg = isCanLogin(session,userName);
//					if(!"".equals(isCanLoginMsg)){
//						log.info(isCanLoginMsg);
//						return sendFailureHtml(isCanLoginMsg);
//					}
					
					QxUserModel qxUserModel = userService.checkUserLogin(qxUserBean);
					QxUser user = qxUserModel!=null?qxUserModel.getBean():null;
					if(user==null){
						String failMsg = "Account validation does not pass!";
//								getFailureMsg(session,"Account validation does not pass!",userName);	
						log.info(failMsg);
						return sendFailureHtml(failMsg);
					}else if(!StringUtil.isNull(user.getDelflag()) && Constant.USER_INVAILD.equals(user.getDelflag())){
						String failMsg = "Invalid account!";
//						getFailureMsg(session,"Invalid account!",userName);	
						log.info(failMsg);
						return sendFailureHtml(failMsg);
					}
					String countryNo = user.getCountryno();
					String tOperatorId = user.getToperatorid();
					String userId = user.getUserid();
					List<AuthorizedMenuPrivilege> list = userService.selectAuthorizedMenuPrivileges(userId, countryNo, tOperatorId);
					if(list!=null){
						session.setAttribute(Constant.USER_MENU_PRIVILEGES, list);
					}
					
					if(session!=null && qxUserModel!=null ){
						session.setAttribute(Constant.USER_MODEL_SESSION, qxUserModel);
					}
					return sendSuccessHtml("login success!");
//				}
//				return sendFailureHtml("login fail!");
			} catch (Exception e) {
				log.error("The exception that logged in failed :"+e);
				return sendFailureHtml("login fail!");
			}
	}
	
	@RequestMapping(value="logout")
	public void logout(HttpServletRequest request, HttpServletResponse response){
		try {
			SessionUtil.clearAttribute(request.getSession());
			response.sendRedirect("/tomp/login.jsp");
		} catch (IOException e) {
			log.error("The exception of logging out :"+e);
		}
	}
	
	
	/**
	 * 主键查询用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="selectUser")
	@ResponseBody
	public ModelMap selectUser(QxUser user){
		ModelMap map = new ModelMap();
		if(null == user || "".equals(user.getUserid())){
			return map;
		}
		QxUserModel userModel = getSessionModel();
		user.setCountryno(userModel.getBean().getCountryno());
		user.setToperatorid(userModel.getBean().getToperatorid());
		try {
			map.put("user", userService.selectByPrimaryKey(user));
		} catch (Exception e) {
			log.error("select user occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 分页查询用户列表
	 * @param user
	 * @param page
	 * @return
	 */
	@RequestMapping(value="selectUserList")
	public ModelAndView selectUserList(QxUser user,Page page){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/userManagement");
		QxUserModel userModel = getSessionModel();
		user.setCountryno(userModel.getBean().getCountryno());
		user.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(null != user.getUsername()){
				user.setUsername(new String(user.getUsername().getBytes("ISO-8859-1"),"UTF-8"));
			}
			mav.addObject("list", userService.selectUserListByPage(user, page));
			mav.addObject("page", page);
		} catch (Exception e) {
			log.error("paging select user occurs error: " + e.getLocalizedMessage());
		}
		return mav;
	}
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="insertUser")
	@ResponseBody
	public Map<String, String> insertUser(QxUser user){
		Map<String, String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		user.setUserno(DateUtil.getTimeZoneStr(new Date(), userModel.getTimeZone(), "yyyyMMdd") + "0");
		user.setUserid(IdGenerator.getUUID());
		user.setCountryno(userModel.getBean().getCountryno());
		user.setToperatorid(userModel.getBean().getToperatorid());
		user.setCreatedate(DateUtil.getTimeZoneDate(new Date(), userModel.getTimeZone(), "yyyyMMdd"));
		try {
			if(userService.insertUser(user)){
				map.put("retCode", "0");
			}else {
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("add user occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="updateUser")
	@ResponseBody
	public Map<String, String> updateUser(QxUser user){
		Map<String, String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		user.setCountryno(userModel.getBean().getCountryno());
		user.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(userService.updateUser(user)){
				map.put("retCode", "0");
			}else {
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("edit user occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 删除用户
	 * @param user
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="deleteUser")
	@ResponseBody
	public Map<String, String> deleteUser(QxUser user,String[] ids){
		Map<String, String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		user.setCountryno(userModel.getBean().getCountryno());
		user.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(userService.deleteUser(ids,user)){
				map.put("retCode", "0");
			}else {
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("delete user occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 检索用户拥有的角色
	 * @param userRole
	 * @return
	 */
	@RequestMapping(value="selectRoleByUser")
	@ResponseBody
	public ModelMap selectRoleByUser(QxUserRole userRole){
		QxUserModel userModel = getSessionModel();
		userRole.setCountryno(userModel.getBean().getCountryno());
		userRole.setToperatorid(userModel.getBean().getToperatorid());
		ModelMap map = new ModelMap();
		try {
			map.put("roles", userService.selectRoleByUser(userRole));
		} catch (Exception e) {
			log.error("select role by user occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}
	
	/**
	 * 保存用户角色
	 * @param userid
	 * @param roleids
	 * @return
	 */
	@RequestMapping(value="insertUserRole")
	@ResponseBody
	public String insertUserRole(String userid,@RequestParam(required=false,defaultValue="")String[] roleids){
		QxUserModel userModel = getSessionModel();
		List<QxUserRole> uList = new ArrayList<QxUserRole>();
		QxUserRole ur = new QxUserRole();
		ur.setCountryno(userModel.getBean().getCountryno());
		ur.setToperatorid(userModel.getBean().getToperatorid());
		ur.setUserid(userid);
		for (int i = 0; i < roleids.length; i++) {
			QxUserRole userRole = new QxUserRole();
			userRole.setCountryno(userModel.getBean().getCountryno());
			userRole.setToperatorid(userModel.getBean().getToperatorid());
			userRole.setUserroleid(IdGenerator.getUUID());
			userRole.setUserid(userid);
			userRole.setRoleid(roleids[i]);
			userRole.setCreatedate(DateUtil.getTimeZoneDate(new Date(), userModel.getTimeZone(), "yyyyMMdd"));
			uList.add(userRole);
		}
		try {
			if(userService.insertUserRole(ur,uList)){
				return "SUCCESS";
			}
		} catch (Exception e) {
			log.error("save user role occurs error: " + e.getLocalizedMessage());
		}
		return "ERROR";
	}
	
	
	private String isCanLogin(HttpSession session,String userName){
		String failMsg = "";
		if(session.getAttribute(Constant.USER_LOGIN_FAILURE_TIMES+userName)==null){
			return "";
		}
		String loginFailreInfo = session.getAttribute(Constant.USER_LOGIN_FAILURE_TIMES+userName).toString();
		Configuration cfg = new Configuration(null, "environment.properties");
		String loginFailureWaittime = cfg.getValue("login.failure.waittime");
		Long mins =  (Long.valueOf(loginFailureWaittime) % (1000 * 60 * 60)) / (1000 * 60); 		
		String currentTime = String.valueOf(new Date().getTime());
		String nextLoginTime = "";
		String loginFailureTimes = "0";
		boolean isCanLogin = true;
		if(!StringUtil.isNull(loginFailreInfo) &&loginFailreInfo.indexOf("|")!=-1){
			String[] allInfo = loginFailreInfo.split("\\|");
			loginFailureTimes = allInfo[0];
			nextLoginTime = allInfo[1];
		}
		if(!StringUtil.isNull(nextLoginTime) && Integer.parseInt(loginFailureTimes)==3 && currentTime.compareTo(nextLoginTime)<0){
			isCanLogin = false;
		}else if(currentTime.compareTo(nextLoginTime)>=0){
			SessionUtil.removeAttribute(session, Constant.USER_LOGIN_FAILURE_TIMES+userName);
		}
		if(!isCanLogin){
			failMsg = "Login failure 3 times, "+mins+" minutes to prohibit re-login,please log in again at "+DateUtil.getFormatDate(new Date(Long.valueOf(nextLoginTime)));
		}
		return failMsg;
	}
	
	private String getFailureMsg(HttpSession session,String defaultMsg,String userName){
		String failMsg = defaultMsg;
		
		Configuration cfg = new Configuration(null, "environment.properties");
		String loginFailureWaittime = cfg.getValue("login.failure.waittime");
		Long mins =  (Long.valueOf(loginFailureWaittime) % (1000 * 60 * 60)) / (1000 * 60); 
		Long nextLoignTime  = new Date().getTime()+Long.valueOf(loginFailureWaittime);
		String loginFailureTimes = "0";
		if(session.getAttribute(Constant.USER_LOGIN_FAILURE_TIMES+userName)!=null){
			String loginFailreInfo = session.getAttribute(Constant.USER_LOGIN_FAILURE_TIMES+userName).toString();
			if(!StringUtil.isNull(loginFailreInfo) &&loginFailreInfo.indexOf("|")!=-1){
				String[] allInfo = loginFailreInfo.split("\\|");
				loginFailureTimes = allInfo[0];
			}
		}
		session.setAttribute(Constant.USER_LOGIN_FAILURE_TIMES+userName, String.valueOf(Integer.parseInt(loginFailureTimes)+1)+"|"+String.valueOf(nextLoignTime));
		
		loginFailureTimes = session.getAttribute(Constant.USER_LOGIN_FAILURE_TIMES+userName).toString().split("\\|")[0];
		
		if(Integer.parseInt(loginFailureTimes) == 3){
			failMsg = "Login failure 3 times, "+mins+" minutes to prohibit re-login";
		}
		return failMsg;
	}
	
	/**
	 * 检测是否存在同名
	 * @param wDevice
	 * @return
	 */
	@RequestMapping(value="check")
	@ResponseBody
	public ModelMap check(QxUser user){
		ModelMap map = new ModelMap();
		QxUserModel userModel = getSessionModel();
		user.setCountryno(userModel.getBean().getCountryno());
		user.setToperatorid(userModel.getBean().getToperatorid());
		try {
			if(userService.isExsit(user)){
				map.put("retCode", "0");//存在
			}
		} catch (Exception e) {
			log.error("Detect whether there is the same name occurs error : " + e.getLocalizedMessage());
		}
		return map;
	}
}
