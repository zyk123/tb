package com.flash.toolbar.mapper;

import com.flash.toolbar.model.HyFeedback;

public interface HyFeedbackMapper {
    int deleteByPrimaryKey(String feedbackid);

    int insert(HyFeedback record);

    int insertSelective(HyFeedback record);

    HyFeedback selectByPrimaryKey(String feedbackid);

    int updateByPrimaryKeySelective(HyFeedback record);

    int updateByPrimaryKey(HyFeedback record);
}