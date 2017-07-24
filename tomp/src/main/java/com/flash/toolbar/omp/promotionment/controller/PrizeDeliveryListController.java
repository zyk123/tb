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
import org.springframework.web.bind.annotation.RequestMapping;

import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.HtmlUtil;
import com.flash.toolbar.omp.model.HdPrizeShip;
import com.flash.toolbar.omp.promotionment.bo.PrizeDeliveryModel;
import com.flash.toolbar.omp.promotionment.service.PrizeDeliveryListService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="/prizedeliverylist")
public class PrizeDeliveryListController extends BaseAction{
	
	private static final Logger log = Logger.getLogger(PrizeDeliveryListController.class);
	
	
	@Resource
	private PrizeDeliveryListService prizeDeliveryListService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "promotionment/prizedeliverylist";
	}
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,PrizeDeliveryModel prizeDeliveryModel) {
			try {
				QxUserModel qxUserModel = getSessionModel();
				if(qxUserModel!=null){
					prizeDeliveryModel.getBean().setCountryno(qxUserModel.getBean().getCountryno());
					prizeDeliveryModel.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
				}
				List<PrizeDeliveryModel> list = prizeDeliveryListService.getPrizeDeliveryListInfo(prizeDeliveryModel);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", list);
				map.put("page", prizeDeliveryModel.getPager());
				HtmlUtil.writerJson(response, map);
			} catch (Exception e) {
				log.error("The exception of querying the prizedeliverylist :"+e);
			}
	}
	
	@RequestMapping(value="/update")
	public void update(HttpServletRequest request, HttpServletResponse response,HdPrizeShip hdPrizeShip) {
			try {
				QxUserModel qxUserModel = getSessionModel();
				if(qxUserModel!=null){
					hdPrizeShip.setCountryno(qxUserModel.getBean().getCountryno());
					hdPrizeShip.setToperatorid(qxUserModel.getBean().getToperatorid());
				}
				hdPrizeShip.setShipstatus("2");
				hdPrizeShip.setShipdate(new Date());
				hdPrizeShip.setModifydate(new Date());
				hdPrizeShip.setModifyman(qxUserModel.getBean().getUsername());
				int count = prizeDeliveryListService.updatePrizeDeliveryInfo(hdPrizeShip);
				sendSuccessMessage(response, "Delete success!",count);
			} catch (Exception e) {
				sendFailureMessage(response, "DeLete failure!");
				log.error("The exception of querying the prizedeliverylist :"+e);
			}
	}	
	
}
