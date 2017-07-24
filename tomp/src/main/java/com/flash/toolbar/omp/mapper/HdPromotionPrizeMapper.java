package com.flash.toolbar.omp.mapper;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.omp.model.HdPromotionPrize;
import com.flash.toolbar.omp.promotionment.bo.PromotionRelation;

public interface HdPromotionPrizeMapper {
    int deleteByPrimaryKey(String promotionprizeid);

    int insert(HdPromotionPrize record);

    int insertSelective(HdPromotionPrize record);

    HdPromotionPrize selectByPrimaryKey(String promotionprizeid);

    int updateByPrimaryKeySelective(HdPromotionPrize record);

    int updateByPrimaryKey(HdPromotionPrize record);

	void returnPrize(String promotionid);

	void deleteRelation(String promotionid);

	int getDataGridCount(Map<String, String> map);

	List<PromotionRelation> getDataGrid(Map<String, String> map);

	Integer calPrizeHasAsignNum(Map<String, String> map);
}