/**
 * 
 */
package com.flash.toolbar.omp.packagesub.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.HtmlUtil;
import com.flash.toolbar.omp.model.TcFlowSub;
import com.flash.toolbar.omp.packagesub.bo.TcFlowSubModel;
import com.flash.toolbar.omp.packagesub.service.PackageSubService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

/**
 * 套餐订单管理控制器
 * @author zhulin
 */
@Controller
@RequestMapping(value="/packageSub")
public class PackageSubController extends BaseAction {
	
	private static final Logger log = Logger.getLogger(PackageSubController.class);

	@Resource
	private PackageSubService packageSubService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "/packageSub/packageSubShow";
	}
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,TcFlowSubModel tcFlowSubModel){
		
		try{
			
			//获取运营商ID和国家编码
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				tcFlowSubModel.setCountryNo(qxUserModel.getBean().getCountryno());
				tcFlowSubModel.settOperatorId(qxUserModel.getBean().getToperatorid());
			}
			
			List<TcFlowSub> list = packageSubService.getPackageSubInfo(tcFlowSubModel);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("page", tcFlowSubModel.getPager());
			HtmlUtil.writerJson(response, map);
			
		}catch (Exception e) {
			log.error("The exception of querying the packageSub :"+e);
		}
		
	}
	
}
