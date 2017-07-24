package com.flash.toolbar.mapper;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.model.HdPrizeRecord;

public interface HdPrizeRecordMapper {
    int deleteByPrimaryKey(String recordid);

    int insert(HdPrizeRecord record);

    int insertSelective(HdPrizeRecord record);

    HdPrizeRecord selectByPrimaryKey(String recordid);

    int updateByPrimaryKeySelective(HdPrizeRecord record);

    int updateByPrimaryKey(HdPrizeRecord record);
    
    HdPrizeRecord selectByMemberAndPromotionId(@Param("memberid")String memberid, @Param("promotionid")String promotionid);
    
    int countByPromotionId(@Param("promotionid")String promotionid);
}