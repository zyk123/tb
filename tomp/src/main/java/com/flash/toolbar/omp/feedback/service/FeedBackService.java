package com.flash.toolbar.omp.feedback.service;

import java.util.List;

import com.flash.toolbar.omp.feedback.bo.HyFeedBackModel;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface FeedBackService {
	List<HyFeedBackModel> getFeedBackListInfo(HyFeedBackModel hyFeedBackModel) throws Exception;
	
	void deleteFeedBackList(String[] fListIds,QxUserModel qxUserModel) throws Exception;
	
	int countByCondition(HyFeedBackModel hyFeedBackModel) throws Exception;
}
