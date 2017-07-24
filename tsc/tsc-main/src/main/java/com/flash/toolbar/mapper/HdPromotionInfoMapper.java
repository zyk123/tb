package com.flash.toolbar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.model.HdPromotionInfo;

public interface HdPromotionInfoMapper {
    int deleteByPrimaryKey(String promotionid);

    int insert(HdPromotionInfo record);

    int insertSelective(HdPromotionInfo record);

    HdPromotionInfo selectByPrimaryKey(String promotionid);

    int updateByPrimaryKeySelective(HdPromotionInfo record);

    int updateByPrimaryKey(HdPromotionInfo record);
    
    List<HdPromotionInfo> selectByStatus(@Param("status")int status, @Param("countryno")String countryno, @Param("toperatorid")String toperatorid);
}