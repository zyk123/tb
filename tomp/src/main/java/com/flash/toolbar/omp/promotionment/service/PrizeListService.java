package com.flash.toolbar.omp.promotionment.service;

import java.util.List;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.HdPrizeInfo;
import com.flash.toolbar.omp.promotionment.bo.PrizeModel;

public interface PrizeListService {
	List<PrizeModel> getPrizeListInfo(PrizeModel prizeModel) throws Exception;
	
	public int countByCondition(PrizeModel prizeModel) throws Exception;

	boolean insertPrize(PrizeModel prizeModel);

	HdPrizeInfo selectByPrimaryKey(HdPrizeInfo hdPrizeInfo);

	String updatePrize(PrizeModel prizeModel);

	boolean deletePrize(HdPrizeInfo hdPrizeInfo);

	List<PrizeModel> selectPrizeListByPage(PrizeModel prizeModel, Page page) throws Exception;
}
