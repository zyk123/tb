package com.flash.toolbar.service;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.model.HdPrizeRecord;
import com.flash.toolbar.model.HdPrizeShip;
import com.flash.toolbar.model.HdPrizeShipUnion;
import com.flash.toolbar.model.HdPromotionInfo;
import com.flash.toolbar.model.HdPromotionPrize;
import com.flash.toolbar.model.HdPromotionPrizeUnion;
import com.flash.toolbar.model.HdPromotionSort;
import com.flash.toolbar.model.HdReceiverInfo;

public interface PromotionService {
	
	/**
	 * 获取已经发布的活动
	 * @return
	 */
	public List<HdPromotionInfo> queryActivePromotion(String countryno, String toperatorid);
	
	public List<Map<String, Object>> queryIsInSCoreStall(HdPromotionPrize hdPromotionPrize);
	
	public HdPromotionInfo queryPromotionInfo(String promotionId);
	
	public HdPrizeRecord queryPrizeRecord(String memberId, String promotionId);
	
	public List<HdPromotionPrizeUnion> queryAllPrizeByPromotionId(String promotionId);
	
	public List<HdPromotionPrizeUnion> queryStallPrizeByPromotionId(String promotionId,String scoreNum);
	
	public HdReceiverInfo queryReceiverInfo(String memberId);
	
	public int saveHdPrizeShip(HdPrizeShip record);
	
	public int updateHdPromotionPrize(HdPromotionPrizeUnion hdPromotionPrizeUnion);
	
	public int saveHdPrizeRecord(HdPrizeRecord record);
	
	public int updateHdPrizeRecord(HdPrizeRecord record);
	
	public List<HdPrizeShipUnion> queryLuckyList(String promotionId, int startNum, int endNum);
	
	public int saveHdPromotionSort(HdPromotionSort record);
	
	public int updateHdPromotionSort(HdPromotionSort record);
	
	public HdPromotionSort queryPromotionSort(String promotionId, String memberId);
	
	public List<HdPromotionSort> querySortList(String promotionId, int startNum, int endNum);
	
	public List<HdPrizeShipUnion> queryMyLuckyList(String memberId);
	
	public int saveHdReceiverInfo(HdReceiverInfo record);
	
	public int updateHdReceiverInfo(HdReceiverInfo record);
	
	public int queryActiveMember(String promotionid);
	
	public int updateHdPrizeShip(HdPrizeShip record);

	public int increaseHdPromotionPrize(String promotionprizeid);
}
