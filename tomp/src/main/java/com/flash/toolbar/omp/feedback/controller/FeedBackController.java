package com.flash.toolbar.omp.feedback.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flash.toolbar.omp.common.action.BaseAction;
import com.flash.toolbar.omp.common.util.HtmlUtil;
import com.flash.toolbar.omp.feedback.bo.HyFeedBackModel;
import com.flash.toolbar.omp.feedback.service.FeedBackService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Controller
@RequestMapping(value="/feedback")
public class FeedBackController extends BaseAction{
	private static final Logger log = Logger.getLogger(FeedBackController.class);
	
	@Resource
	private FeedBackService feedBackService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "feedback/feedback";
	}
	
	
	@RequestMapping(value="/list")
	public void list(HttpServletRequest request, HttpServletResponse response,HyFeedBackModel hyFeedBackModel){
		try {
			QxUserModel qxUserModel = getSessionModel();
			if(qxUserModel!=null){
				hyFeedBackModel.getBean().setCountryno(qxUserModel.getBean().getCountryno());
				hyFeedBackModel.getBean().setToperatorid(qxUserModel.getBean().getToperatorid());
			}
			List<HyFeedBackModel> list = feedBackService.getFeedBackListInfo(hyFeedBackModel);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("page", hyFeedBackModel.getPager());
			HtmlUtil.writerJson(response, map);
		} catch (Exception e) {
			log.error("The exception of querying the feedbacklist :"+e);
		}
	}
	
	
	
	@RequestMapping(value="/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "delIds[]") String[] delIds){
        try {
			if (delIds == null || delIds.length == 0) {
				log.info("ID invalid!");
			    sendFailureMessage(response, "ID invalid!");
			} else {
				feedBackService.deleteFeedBackList(delIds,getSessionModel());
				log.info("Delete success!");
			    sendSuccessMessage(response, "Delete success!",delIds.length);
			}
		} catch (Exception e) {
			sendFailureMessage(response, "DeLete failure!");
			log.error("The exception of deleting the feedbacklist :"+e);
		}
	}	
}
