package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.HdPromotionInfo;
import com.flash.toolbar.omp.promotionment.bo.PrizeModel;
import com.flash.toolbar.omp.promotionment.bo.PromotionModel;

public interface HdPromotionInfoMapper {
    int deleteByPrimaryKey(String promotionid);

    int insert(HdPromotionInfo record);

    int insertSelective(HdPromotionInfo record);

    HdPromotionInfo selectByPrimaryKey(String promotionid);

    int updateByPrimaryKeySelective(HdPromotionInfo record);

    int updateByPrimaryKey(HdPromotionInfo record);

	List<PromotionModel> selectByPage(@Param(value="promotionModel")PromotionModel promotionModel, @Param(value="pager")Page page);
	
	int countByCondition(PromotionModel promotionModel);
}