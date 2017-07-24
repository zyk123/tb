package com.flash.toolbar.omp.promotionment.service;

import java.util.List;

import com.flash.toolbar.omp.model.HdPrizeShip;
import com.flash.toolbar.omp.promotionment.bo.PrizeDeliveryModel;

public interface PrizeDeliveryListService {
	List<PrizeDeliveryModel> getPrizeDeliveryListInfo(PrizeDeliveryModel prizeDeliveryModel) throws Exception;
	
	int updatePrizeDeliveryInfo(HdPrizeShip hdPrizeShip) throws Exception;
	
	int countByCondition(PrizeDeliveryModel prizeDeliveryModel) throws Exception;
	
}
