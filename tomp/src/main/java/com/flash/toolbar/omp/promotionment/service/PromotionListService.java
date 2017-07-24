package com.flash.toolbar.omp.promotionment.service;

import java.util.List;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.HdPromotionInfo;
import com.flash.toolbar.omp.promotionment.bo.PromotionModel;
import com.flash.toolbar.omp.promotionment.bo.PromotionPrizeModel;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface PromotionListService {
    List<PromotionModel> getPromotionListInfo(PromotionModel promotionModel) throws Exception;
	
	public int countByCondition(PromotionModel promotionModel) throws Exception;

	boolean insertPromotion(PromotionModel promotionModel);

	HdPromotionInfo selectByPrimaryKey(HdPromotionInfo hdPromotionInfo);

	boolean updatePromotion(PromotionModel promotionModel);

	boolean deletePromotion(HdPromotionInfo hdPromotionInfo);

	List<PromotionModel> selectPromotionListByPage(PromotionModel promotionModel,
			Page page) throws Exception;

	void saveRelation(List<?> list, QxUserModel userModel);

	String getPrizeCombox(QxUserModel qxUserModel, String promotionId);

	String getDataGrid(QxUserModel qxUserModel, String promotionid);
}
