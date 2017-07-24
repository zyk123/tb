package com.flash.toolbar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.model.HdPrizeShip;
import com.flash.toolbar.model.HdPrizeShipUnion;

public interface HdPrizeShipMapper {
    int deleteByPrimaryKey(String shipid);

    int insert(HdPrizeShip record);

    int insertSelective(HdPrizeShip record);

    HdPrizeShip selectByPrimaryKey(String shipid);

    int updateByPrimaryKeySelective(HdPrizeShip record);

    int updateByPrimaryKey(HdPrizeShip record);
    
    List<HdPrizeShipUnion> selectPageByPromotionId(@Param("promotionid")String promotionid, @Param("startnum")int startnum, @Param("endnum")int endnum);
    
    List<HdPrizeShipUnion> selectByMemberId(@Param("memberid")String memberid);
}