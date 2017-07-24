package com.flash.toolbar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.model.HdPromotionSort;

public interface HdPromotionSortMapper {
    int deleteByPrimaryKey(String sortid);

    int insert(HdPromotionSort record);

    int insertSelective(HdPromotionSort record);

    HdPromotionSort selectByPrimaryKey(String sortid);

    int updateByPrimaryKeySelective(HdPromotionSort record);

    int updateByPrimaryKey(HdPromotionSort record);
    
    HdPromotionSort selectByPromotionAndMemberId(@Param("memberid")String memberid, @Param("promotionid")String promotionid);
    
    List<HdPromotionSort> selectPageByPromotionId(@Param("promotionid")String promotionid, @Param("startnum")int startnum, @Param("endnum")int endnum);
}