package com.flash.toolbar.omp.promotionment.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.model.HdPrizeInfo;
import com.flash.toolbar.omp.promotionment.bo.PrizeModel;
import com.flash.toolbar.omp.promotionment.service.PrizeListService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="/prizelist")
public class PrizeListController extends BaseAction{
	
	private static final Logger log = Logger.getLogger(PrizeListController.class);
	
	@Resource
	private PrizeListService prizeListService;
	
	@RequestMapping(value="/listPrizeInfo")
	public ModelAndView listPrizeInfo(PrizeModel prizeModel,Page page) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("promotionment/prizelist");
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				prizeModel.getBean().setCountryno(qxUserModel.getBean().getCountryno());
				prizeModel.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<PrizeModel> list = prizeListService.selectPrizeListByPage(prizeModel,page);
			mav.addObject("list", list);
			mav.addObject("page", page);
		} catch (Exception e) {
			log.error("The exception of querying the prizelist :"+e);
		}
		return mav;
	}
	
	@RequestMapping(value="listById")
	@ResponseBody
	public ModelMap selectPrize(HdPrizeInfo prizeModel){
		ModelMap map = new ModelMap();
		if(null == prizeModel || "".equals(prizeModel.getPrizeid())){
			return map;
		}
		QxUserModel userModel = getSessionModel();
		prizeModel.setCountryno(userModel.getBean().getCountryno());
		prizeModel.setToperatorid(userModel.getBean().getToperatorid());
		try {
			map.put("prize", prizeListService.selectByPrimaryKey(prizeModel));
		} catch (Exception e) {
			log.error("select prize occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}
	
	
	@RequestMapping(value="insertPrize")
	@ResponseBody
	public Map<String, String> insertPrize(PrizeModel prizeModel){
		Map<String, String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		prizeModel.getBean().setPrizeid(IdGenerator.getUUID());
		prizeModel.getBean().setCountryno(userModel.getBean().getCountryno());
		prizeModel.getBean().setToperatorid(userModel.getBean().getToperatorid());
		prizeModel.getBean().setModifydate(DateUtil.getTimeZoneDate(new Date(), userModel.getTimeZone(), "yyyyMMdd"));
		try {
			if(prizeListService.insertPrize(prizeModel)){
				map.put("retCode", "0");
			}else {
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("add prize occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}
	
	@RequestMapping(value="updatePrize")
	@ResponseBody
	public Map<String, String> updatePrize(PrizeModel prizeModel){
		Map<String, String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		prizeModel.getBean().setCountryno(userModel.getBean().getCountryno());
		prizeModel.getBean().setToperatorid(userModel.getBean().getToperatorid());
		prizeModel.getBean().setModifydate(new Date());
		try {
				map.put("retCode", prizeListService.updatePrize(prizeModel));
		} catch (Exception e) {
			log.error("edit prize occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}
	
	
	@RequestMapping(value="/deletePrize")
	public void delete(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "id") String id){
        try {
			if (StringUtil.isNull(id)) {
				log.info("ID invalid!");
			    sendFailureMessage(response, "ID invalid!");
			} else {
				QxUserModel userModel = getSessionModel();
				HdPrizeInfo hdPrizeInfo = new HdPrizeInfo();
				hdPrizeInfo.setCountryno(userModel.getBean().getCountryno());
				hdPrizeInfo.setToperatorid(userModel.getBean().getToperatorid());
				hdPrizeInfo.setPrizeid(id);
				boolean rtn = prizeListService.deletePrize(hdPrizeInfo);
				if(rtn){
					log.info("Delete success!");
					sendSuccessMessage(response, "Delete success!",null);
				}else{
					sendFailureMessage(response,"The prize is in promotion,can not be deleted!");
				}
			}
		} catch (Exception e) {
			sendFailureMessage(response, "DeLete failure!");
			log.error("The exception of deleting the prize :"+e);
		}
	}
	
}
