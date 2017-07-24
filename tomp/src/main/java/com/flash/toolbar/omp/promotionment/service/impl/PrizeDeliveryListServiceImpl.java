package com.flash.toolbar.omp.promotionment.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.mapper.HdPrizeShipMapper;
import com.flash.toolbar.omp.model.HdPrizeShip;
import com.flash.toolbar.omp.promotionment.bo.PrizeDeliveryModel;
import com.flash.toolbar.omp.promotionment.service.PrizeDeliveryListService;

@Transactional
@Service
public class PrizeDeliveryListServiceImpl implements PrizeDeliveryListService{
	@Resource
	private HdPrizeShipMapper hdPrizeShipMapper;
	
	@Override
	public List<PrizeDeliveryModel> getPrizeDeliveryListInfo(
			PrizeDeliveryModel prizeDeliveryModel) throws Exception {
		int totalRows = countByCondition(prizeDeliveryModel);
		prizeDeliveryModel.getPager().setTotalRowsCount(totalRows);
		prizeDeliveryModel.getPager().doPage();
		int totalPageNum = prizeDeliveryModel.getPager().getTotalPageNum();
		int pageIndex = prizeDeliveryModel.getPageIndex();
		if(pageIndex>totalPageNum){
			prizeDeliveryModel.setPageIndex(1);
			prizeDeliveryModel.getPager().doPage();
		}
		List<PrizeDeliveryModel> list = hdPrizeShipMapper.selectByPageSelective(prizeDeliveryModel);
		return list;
	}

	@Override
	public int countByCondition(PrizeDeliveryModel prizeDeliveryModel)
			throws Exception {
		return hdPrizeShipMapper.countByCondition(prizeDeliveryModel);
	}

	@Override
	public int updatePrizeDeliveryInfo(HdPrizeShip hdPrizeShip)
			throws Exception {
		return hdPrizeShipMapper.updateByPrimaryKeySelective(hdPrizeShip);
	}
	
	
	

	
}
