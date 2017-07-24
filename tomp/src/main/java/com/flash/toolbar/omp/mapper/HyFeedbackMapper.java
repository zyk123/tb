package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.feedback.bo.HyFeedBackModel;
import com.flash.toolbar.omp.model.HyFeedback;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface HyFeedbackMapper {

	HyFeedback selectByPrimaryKey(String feedbackid);
	
	List<HyFeedBackModel> selectByPageSelective(HyFeedBackModel hyFeedBackModel);
    
    int countByCondition(HyFeedBackModel model);
    
    int deleteByPrimaryKey(String feedbackid);

    int deleteFeedListBatch(@Param("fListIds")String[] fListIds,@Param("qxUserModel")QxUserModel qxUserModel);
}