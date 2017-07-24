package com.flash.toolbar.omp.promotionment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.IdGenerator;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.model.HdPromotionInfo;
import com.flash.toolbar.omp.promotionment.bo.PromotionModel;
import com.flash.toolbar.omp.promotionment.bo.PromotionPrizeModel;
import com.flash.toolbar.omp.promotionment.common.PromotionStatus;
import com.flash.toolbar.omp.promotionment.service.PromotionListService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value = "/promotionlist")
public class PromotionListController extends BaseAction {

	private static final Logger log = Logger
			.getLogger(PromotionListController.class);

	@Resource
	private PromotionListService promotionListService;

	@RequestMapping(value = "/listPromotionInfo")
	public ModelAndView listPromotionInfo(PromotionModel promotionModel,
			Page page) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("promotionment/promotionlist");
		try {
			QxUserModel qxUserModel = getSessionModel();
			if (qxUserModel != null) {
				promotionModel.getBean().setCountryno(
						qxUserModel.getBean().getCountryno());
				promotionModel.getBean().setToperatorid(
						qxUserModel.getBean().getToperatorid());
			}
			List<PromotionModel> list = promotionListService
					.selectPromotionListByPage(promotionModel, page);
			mav.addObject("list", list);
			mav.addObject("page", page);
		} catch (Exception e) {
			log.error("The exception of querying the promotionlist :" + e);
		}
		return mav;
	}

	@RequestMapping(value = "listById")
	@ResponseBody
	public ModelMap selectPromotion(HdPromotionInfo hdPromotionInfo) {
		ModelMap map = new ModelMap();
		if (null == hdPromotionInfo
				|| "".equals(hdPromotionInfo.getPromotionid())) {
			return map;
		}
		QxUserModel userModel = getSessionModel();
		hdPromotionInfo.setCountryno(userModel.getBean().getCountryno());
		hdPromotionInfo.setToperatorid(userModel.getBean().getToperatorid());
		try {
			map.put("promotion",
					promotionListService.selectByPrimaryKey(hdPromotionInfo));
		} catch (Exception e) {
			log.error("select promotion occurs error: "
					+ e.getLocalizedMessage());
		}
		return map;
	}

	@RequestMapping(value = "insertPromotion")
	@ResponseBody
	public Map<String, String> insertPromotion(PromotionModel promotionModel) {
		Map<String, String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		promotionModel.getBean().setPromotionid(IdGenerator.getUUID());
		promotionModel.getBean().setCountryno(
				userModel.getBean().getCountryno());
		promotionModel.getBean().setToperatorid(
				userModel.getBean().getToperatorid());
		promotionModel.getBean().setModifydate(
				DateUtil.getTimeZoneDate(new Date(), userModel.getTimeZone(),
						"yyyyMMdd"));
		promotionModel.getBean().setStatus(
				new BigDecimal(PromotionStatus.statusInit));
		try {
			if (promotionListService.insertPromotion(promotionModel)) {
				map.put("retCode", "0");
			} else {
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("add promotion occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}

	@RequestMapping(value = "updatePromotion")
	@ResponseBody
	public Map<String, String> updatePromotion(PromotionModel promotionModel) {
		Map<String, String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		promotionModel.getBean().setCountryno(
				userModel.getBean().getCountryno());
		promotionModel.getBean().setToperatorid(
				userModel.getBean().getToperatorid());
		try {
			if (promotionListService.updatePromotion(promotionModel)) {
				map.put("retCode", "0");
			} else {
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("edit promotion occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}

	@RequestMapping(value = "shiftPromotion")
	@ResponseBody
	public Map<String, String> shiftPromotion(String promotionid,String status) {
		Map<String, String> map = new HashMap<String, String>();
		QxUserModel userModel = getSessionModel();
		PromotionModel promotionModel = new PromotionModel();
		promotionModel.getBean().setCountryno(
				userModel.getBean().getCountryno());
		promotionModel.getBean().setToperatorid(
				userModel.getBean().getToperatorid());
		promotionModel.getBean().setPromotionid(promotionid);
		if("0".equals(status)){
			promotionModel.getBean().setStatus(
					new BigDecimal(PromotionStatus.statusInit));
		}else if("1".equals(status)){
			promotionModel.getBean().setStatus(
					new BigDecimal(PromotionStatus.statusShift));
		}
		try {
			if (promotionListService.updatePromotion(promotionModel)) {
				map.put("retCode", "0");
			} else {
				map.put("retCode", "1");
			}
		} catch (Exception e) {
			log.error("edit promotion occurs error: " + e.getLocalizedMessage());
		}
		return map;
	}

	@RequestMapping(value = "/deletePromotion")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "id") String id) {
		try {
			if (StringUtil.isNull(id)) {
				log.info("ID invalid!");
				sendFailureMessage(response, "ID invalid!");
			} else {
				QxUserModel userModel = getSessionModel();
				HdPromotionInfo hdPromotionInfo = new HdPromotionInfo();
				hdPromotionInfo
						.setCountryno(userModel.getBean().getCountryno());
				hdPromotionInfo.setToperatorid(userModel.getBean()
						.getToperatorid());
				hdPromotionInfo.setPromotionid(id);
				boolean delRtn = promotionListService
						.deletePromotion(hdPromotionInfo);
				if (delRtn) {
					log.info("Delete success!");
					sendSuccessMessage(response, "Delete success!", null);
				} else {
					log.info("Delete failure!");
					sendFailureMessage(response, "The promotion are in the process of,can not be deleted!");
				}
			}
		} catch (Exception e) {
			sendFailureMessage(response, "DeLete failure!");
			log.error("The exception of deleting the promotion :" + e);
		}
	}

	@RequestMapping(value = "/saveRelation")
	public void saveRelation(HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody List<?> list) {
		try {
			QxUserModel qxUserModel = getSessionModel();
			promotionListService.saveRelation(list, qxUserModel);
			sendSuccessMessage(response, "", null);
		} catch (Exception e) {
			sendFailureMessage(response, "saveRelation failure!");
			log.error("The exception of deleting the promotion :" + e);
		}
	}

	@RequestMapping(value = "/getPrizeInfo")
	public void getPrizeInfo(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "promotionid") String promotionid) {
		//String rtn = "[{\"bean.prizeid\":\"bbc23d93e67f440296aaaeca04e129fd\",\"prizename\":\"Koi\"},{\"bean.prizeid\":\"1\",\"prizename\":\"Dalmation\"}]";
		try {
			QxUserModel qxUserModel = getSessionModel();
			String rtn = promotionListService.getPrizeCombox(qxUserModel,promotionid);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write(rtn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getDataGrid")
	public void getDataGrid(HttpServletRequest request,
			HttpServletResponse response,String promotionid) {
//		String rtn = "{\"total\":28,\"rows\":["
//				+ "{\"bean.prizeid\":\"bbc23d93e67f440296aaaeca04e129fd\",\"prizename\":\"Koi\",\"ptotalnum\":\"20\",\"prestnum\":10,\"bean.prizelevel\":\"P\",\"bean.prizetotalnum\":5,\"bean.probability\":\"2\"},"
//				+ "{\"bean.prizeid\":\"1\",\"prizename\":\"Dalmation\",\"ptotalnum\":\"30\",\"prestnum\":15,\"bean.prizelevel\":\"q\",\"bean.prizetotalnum\":9,\"bean.probability\":\"3\"}]}";
		try {
			QxUserModel qxUserModel = getSessionModel();
			String rtn = promotionListService.getDataGrid(qxUserModel,promotionid);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write(rtn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
