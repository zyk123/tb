package com.flash.toolbar.omp.feedback.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flash.toolbar.omp.feedback.bo.HyFeedBackModel;
import com.flash.toolbar.omp.feedback.service.FeedBackService;
import com.flash.toolbar.omp.mapper.HyFeedbackMapper;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Service
public class FeedBackServiceImpl implements FeedBackService{
	
	@Resource
	private HyFeedbackMapper hyFeedbackMapper;
	@Override
	public int countByCondition(HyFeedBackModel hyFeedBackModel)
			throws Exception {
		return hyFeedbackMapper.countByCondition(hyFeedBackModel);
	}
	
	@Override
	public void deleteFeedBackList(String[] fListIds, QxUserModel qxUserModel)
			throws Exception {
		hyFeedbackMapper.deleteFeedListBatch(fListIds, qxUserModel);
		
	}
	
	@Override
	public List<HyFeedBackModel> getFeedBackListInfo(
			HyFeedBackModel hyFeedBackModel) throws Exception {
		int totalRows = countByCondition(hyFeedBackModel);
		hyFeedBackModel.getPager().setTotalRowsCount(totalRows);
		hyFeedBackModel.getPager().doPage();
		int totalPageNum = hyFeedBackModel.getPager().getTotalPageNum();
		int pageIndex = hyFeedBackModel.getPageIndex();
		if(pageIndex>totalPageNum){
			hyFeedBackModel.setPageIndex(1);
			hyFeedBackModel.getPager().doPage();
		}
		List<HyFeedBackModel> list = hyFeedbackMapper.selectByPageSelective(hyFeedBackModel);
		return list;
	}
}
