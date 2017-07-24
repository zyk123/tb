package com.flash.toolbar.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.model.HdPromotionPrize;
import com.flash.toolbar.model.HdPromotionPrizeUnion;

public interface HdPromotionPrizeMapper {
    int deleteByPrimaryKey(String promotionprizeid);

    int insert(HdPromotionPrize record);

    int insertSelective(HdPromotionPrize record);

    HdPromotionPrize selectByPrimaryKey(String promotionprizeid);

    int updateByPrimaryKeySelective(HdPromotionPrize record);

    int updateByPrimaryKey(HdPromotionPrize record);
    
    List<HdPromotionPrizeUnion> selectPrizeByPromotionId(@Param("promotionid")String promotionid);
    
    List<HdPromotionPrizeUnion> selectAllPrizeByPromotionId(@Param("promotionid")String promotionid);
    
    List<HdPromotionPrizeUnion> selectStallPrizeByPromotionId(@Param("promotionid")String promotionid,@Param("scoreNum") String scoreNum);
    
    List<Map<String, Object>> selectStallBySelective(HdPromotionPrize record);

	int increaseHdPromotionPrize(String promotionprizeid);
}