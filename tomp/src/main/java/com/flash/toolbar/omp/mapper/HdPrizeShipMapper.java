package com.flash.toolbar.omp.mapper;

import java.util.List;

import com.flash.toolbar.omp.model.HdPrizeShip;
import com.flash.toolbar.omp.promotionment.bo.PrizeDeliveryModel;

public interface HdPrizeShipMapper {
    HdPrizeShip selectByPrimaryKey(String shipid);
    
    List<PrizeDeliveryModel> selectByPageSelective(PrizeDeliveryModel prizeDeliveryModel);
    
    int countByCondition(PrizeDeliveryModel prizeDeliveryModel);

    int updateByPrimaryKeySelective(HdPrizeShip record);

    int updateByPrimaryKey(HdPrizeShip record);
    
}