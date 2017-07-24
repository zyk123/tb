package com.flash.toolbar.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.BusinessLogger;
import com.flash.toolbar.common.util.Constant;
import com.flash.toolbar.common.util.ParamPropertiesUtil;
import com.flash.toolbar.common.util.StringUtil;
import com.flash.toolbar.mapper.HdPrizeRecordMapper;
import com.flash.toolbar.mapper.HdPrizeShipMapper;
import com.flash.toolbar.mapper.HdPromotionInfoMapper;
import com.flash.toolbar.mapper.HdPromotionPrizeMapper;
import com.flash.toolbar.mapper.HdPromotionSortMapper;
import com.flash.toolbar.mapper.HdReceiverInfoMapper;
import com.flash.toolbar.mapper.SysTelecomOperatorMapper;
import com.flash.toolbar.model.HdPrizeRecord;
import com.flash.toolbar.model.HdPrizeShip;
import com.flash.toolbar.model.HdPrizeShipUnion;
import com.flash.toolbar.model.HdPromotionInfo;
import com.flash.toolbar.model.HdPromotionPrize;
import com.flash.toolbar.model.HdPromotionPrizeUnion;
import com.flash.toolbar.model.HdPromotionSort;
import com.flash.toolbar.model.HdReceiverInfo;
import com.flash.toolbar.redis.RedisOperation;
import com.flash.toolbar.service.PromotionService;

@Transactional
@Service("promotionService")
public class PromotionServiceImpl implements PromotionService{
	@Autowired
	private HdPromotionInfoMapper hdPromotionInfoMapper;
	
	@Autowired
	private RedisOperation redisOperation;	
	
	@Autowired
	private SysTelecomOperatorMapper sysTelecomOperatorMapper;
	
	@Autowired
	private HdPrizeRecordMapper hdPrizeRecordMapper;
	
	@Autowired
	private HdPromotionPrizeMapper hdPromotionPrizeMapper;
	
	@Autowired
	private HdPrizeShipMapper hdPrizeShipMapper;
	
	@Autowired
	private HdReceiverInfoMapper hdReceiverInfoMapper;
	
	@Autowired
	private HdPromotionSortMapper hdPromotionSortMapper;
	
	/**
	 * 获取已发布的活动列表
	 */
	@Override
	public List<HdPromotionInfo> queryActivePromotion(String countryno, String toperatorid) {
		List<HdPromotionInfo> result = null;
		try{
//			if(redisOperation.exists(Constant.CACHE_PROMOTION_LIST)){
//				BusinessLogger.info("query active promotions from cache");
//				List<String> list = redisOperation.ZRange(Constant.CACHE_PROMOTION_LIST, 0, -1);
//				if(!list.isEmpty()){
//					result = new ArrayList<HdPromotionInfo>();
//					Iterator<String> iter = list.iterator();
//					while(iter.hasNext()){
//						HdPromotionInfo info = JSONObject.toJavaObject(JSONObject.parseObject(iter.next()), HdPromotionInfo.class);
//						result.add(info);
//					}
//				}
//			}
//			else{
				BusinessLogger.info("query active promotions from db");
				List<HdPromotionInfo> list = hdPromotionInfoMapper.selectByStatus(1, countryno, toperatorid);
				if(list.size() > 0){
//					for(int i=0;i<list.size();i++){
//						HdPromotionInfo info = list.get(i);
//						redisOperation.ZAdd(Constant.CACHE_PROMOTION_LIST, JSONObject.toJSONString(info), (double)i+1);
//					}
//					redisOperation.expire(Constant.CACHE_PROMOTION_LIST, Long.parseLong(ParamPropertiesUtil.getCacheTimeOut()), TimeUnit.SECONDS);
					result = list;
				}
//			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	
	/**
	 * 查询活动信息
	 * @param promotionId
	 * @return
	 */
	public HdPromotionInfo queryPromotionInfo(String promotionId){
		HdPromotionInfo result = null;
		try{
//			if(redisOperation.exists(Constant.CACHE_PROMOTION_INFO + promotionId)){
//				BusinessLogger.info("query promotion info from cache: " + promotionId);
//				HdPromotionInfo info = JSONObject.toJavaObject(JSONObject.parseObject(redisOperation.get(Constant.CACHE_PROMOTION_INFO + promotionId)), HdPromotionInfo.class);
//				result = info;
//			}
//			else{
				BusinessLogger.info("query promotion info from db: " + promotionId);
				HdPromotionInfo proInfo = hdPromotionInfoMapper.selectByPrimaryKey(promotionId);
				if(proInfo != null){
//					redisOperation.add(Constant.CACHE_PROMOTION_INFO + promotionId, JSONObject.toJSONString(proInfo));
//					redisOperation.expire(Constant.CACHE_PROMOTION_INFO + promotionId, Long.parseLong(ParamPropertiesUtil.getCacheTimeOut()), TimeUnit.SECONDS);
					result = proInfo;
				}
//			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询抽奖记录
	 * @param memberId
	 * @param promotionId
	 * @return
	 */
	public HdPrizeRecord queryPrizeRecord(String memberId, String promotionId){
		HdPrizeRecord result = null;
		try{
			result = hdPrizeRecordMapper.selectByMemberAndPromotionId(memberId, promotionId);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询活动分配的奖品
	 */
	public List<HdPromotionPrizeUnion> queryAllPrizeByPromotionId(String promotionId){
		List<HdPromotionPrizeUnion> result = null;
		try{
//			if(redisOperation.exists(Constant.CACHE_PROMOTION_PRIZE + promotionId)){
//				BusinessLogger.info("query all promotion prize from cache");
//				List<String> list = redisOperation.ZRange(Constant.CACHE_PROMOTION_PRIZE + promotionId, 0, -1);
//				if(!list.isEmpty()){
//					result = new ArrayList<HdPromotionPrizeUnion>();
//					Iterator<String> iter = list.iterator();
//					while(iter.hasNext()){
//						HdPromotionPrizeUnion info = JSONObject.toJavaObject(JSONObject.parseObject(iter.next()), HdPromotionPrizeUnion.class);
//						result.add(info);
//					}
//				}
//			}
//			else{
				BusinessLogger.info("query all promotion prize from db");
				List<HdPromotionPrizeUnion> union = hdPromotionPrizeMapper.selectAllPrizeByPromotionId(promotionId);
				if(union.size() > 0){
//					for(int i=0;i<union.size();i++){
//						HdPromotionPrizeUnion info = union.get(i);
//						redisOperation.ZAdd(Constant.CACHE_PROMOTION_PRIZE + promotionId, JSONObject.toJSONString(info), (double)i+1);
//					}
//					redisOperation.expire(Constant.CACHE_PROMOTION_PRIZE + promotionId, Long.parseLong(ParamPropertiesUtil.getCacheTimeOut()), TimeUnit.SECONDS);
					result = union;
				}
//			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询收件人信息
	 * @param memberId
	 * @return
	 */
	public HdReceiverInfo queryReceiverInfo(String memberId){
		HdReceiverInfo result = null;
		try{
//			if(redisOperation.exists(Constant.CACHE_PROMOTION_RECEIVERINFO + memberId)){
//				BusinessLogger.info("query receiver info from cache: " + memberId);
//				result = JSONObject.toJavaObject(JSONObject.parseObject(redisOperation.get(Constant.CACHE_PROMOTION_RECEIVERINFO + memberId)), HdReceiverInfo.class);
//			}
//			else{
				BusinessLogger.info("query receiver info from db: " + memberId);
				result = hdReceiverInfoMapper.selectByMemberId(memberId);
//				if(result != null){
//					redisOperation.add(Constant.CACHE_PROMOTION_RECEIVERINFO + memberId, JSONObject.toJSONString(result));
//					redisOperation.expire(Constant.CACHE_PROMOTION_RECEIVERINFO + memberId, Long.parseLong(ParamPropertiesUtil.getCacheTimeOut()), TimeUnit.SECONDS);
//				}
//			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询活动中奖记录
	 * 分页查询
	 */
	public List<HdPrizeShipUnion> queryLuckyList(String promotionId, int startNum, int endNum){
		List<HdPrizeShipUnion> result = null;
		try{
//			if(redisOperation.exists(Constant.CACHE_PROMOTION_PRIZESHIP + promotionId)){
//				BusinessLogger.info("query lucky list from cache");
//				List<String> list = redisOperation.ZRange(Constant.CACHE_PROMOTION_PRIZESHIP + promotionId, startNum-1, endNum-1);
//				if(!list.isEmpty()){
//					result = new ArrayList<HdPrizeShipUnion>();
//					Iterator<String> iter = list.iterator();
//					while(iter.hasNext()){
//						HdPrizeShipUnion info = JSONObject.toJavaObject(JSONObject.parseObject(iter.next()), HdPrizeShipUnion.class);
//						result.add(info);
//					}
//				}
//			}
//			else{
				BusinessLogger.info("query lucky list from db");
				List<HdPrizeShipUnion> union = hdPrizeShipMapper.selectPageByPromotionId(promotionId, startNum, endNum);
				if(union != null && union.size() > 0){
//					for(int i =0;i < union.size(); i++){
//						HdPrizeShipUnion info = union.get(i);
//						redisOperation.ZAdd(Constant.CACHE_PROMOTION_PRIZESHIP + promotionId, JSONObject.toJSONString(info), (double)info.getRn());
//					}
//					redisOperation.expire(Constant.CACHE_PROMOTION_PRIZESHIP + promotionId, Long.parseLong(ParamPropertiesUtil.getCacheTimeOut()), TimeUnit.SECONDS);
					result = union;
				}
			}
//		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取我的中奖记录
	 * @param memberId
	 * @return
	 */
	public List<HdPrizeShipUnion> queryMyLuckyList(String memberId){
		List<HdPrizeShipUnion> result = null;
		try{			
			BusinessLogger.info("query my lucky list from db");
			result = hdPrizeShipMapper.selectByMemberId(memberId);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询分数排名
	 */
	public List<HdPromotionSort> querySortList(String promotionId, int startNum, int endNum){
		List<HdPromotionSort> result = null;
		try{
//			if(redisOperation.exists(Constant.CACHE_PROMOTION_SCORELIST + promotionId)){
//				BusinessLogger.info("query score list from cache");
//				List<String> list = redisOperation.ZRange(Constant.CACHE_PROMOTION_SCORELIST + promotionId, startNum-1, endNum-1);
//				if(!list.isEmpty()){
//					result = new ArrayList<HdPromotionSort>();
//					Iterator<String> iter = list.iterator();
//					while(iter.hasNext()){
//						HdPromotionSort info = JSONObject.toJavaObject(JSONObject.parseObject(iter.next()), HdPromotionSort.class);
//						result.add(info);
//					}
//				}
//			}
//			else{
				BusinessLogger.info("query score list from db");
				List<HdPromotionSort> union = hdPromotionSortMapper.selectPageByPromotionId(promotionId, startNum, endNum);
				if(union != null && union.size() > 0){
//					for(int i =0;i < union.size(); i++){
//						HdPromotionSort info = union.get(i);
//						redisOperation.ZAdd(Constant.CACHE_PROMOTION_SCORELIST + promotionId, JSONObject.toJSONString(info), (double)i+1);
//					}
//					redisOperation.expire(Constant.CACHE_PROMOTION_SCORELIST + promotionId, Long.parseLong(ParamPropertiesUtil.getCacheScoreListTimeOut()), TimeUnit.SECONDS);
					result = union;
				}
//			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 保存发货信息
	 */
	public int saveHdPrizeShip(HdPrizeShip record){
		return hdPrizeShipMapper.insert(record);
	}
	
	/**
	 * 更新发货信息
	 */
	public int updateHdPrizeShip(HdPrizeShip record){
		return hdPrizeShipMapper.updateByPrimaryKeySelective(record);
	}
	
	public int increaseHdPromotionPrize(String promotionprizeid){
		int rtn = hdPromotionPrizeMapper.increaseHdPromotionPrize(promotionprizeid);
		return rtn;
	}
	
	/**
	 * 更新活动奖品
	 * @param record
	 * @return
	 */
	public int updateHdPromotionPrize(HdPromotionPrizeUnion hdPromotionPrizeUnion){
		int rtn;
		synchronized(this){
			HdPromotionPrize pp = new HdPromotionPrize();
			pp.setPromotionprizeid(hdPromotionPrizeUnion.getPromotionprizeid());
			if(hdPromotionPrizeUnion!=null && hdPromotionPrizeUnion.getPrizerestnum()!=null && hdPromotionPrizeUnion.getPrizerestnum()!=0){
				pp.setPrizerestnum(hdPromotionPrizeUnion.getPrizerestnum() - 1);
			}
			if(pp.getPrizerestnum() == 0){
				pp.setProbability(0);//如果剩余数量为0 则将中奖概率置为0
			}
			pp.setModifydate(new Date());
			
//			String promotionId = hdPromotionPrizeUnion.getPromotionid();
//			
//			Double score = redisOperation.ZScore(Constant.CACHE_PROMOTION_PRIZE+ promotionId, JSONObject.toJSONString(hdPromotionPrizeUnion));
//			if(score==null){
//				score = 0.0;
//			}
//			redisOperation.ZRem(Constant.CACHE_PROMOTION_PRIZE+ promotionId, JSONObject.toJSONString(hdPromotionPrizeUnion));
//			
//			hdPromotionPrizeUnion.setPrizerestnum(hdPromotionPrizeUnion.getPrizerestnum() - 1);
//			if(hdPromotionPrizeUnion.getPrizerestnum()==0){
//				hdPromotionPrizeUnion.setProbability(0);
//			}
//			redisOperation.ZAdd(Constant.CACHE_PROMOTION_PRIZE+ promotionId,JSONObject.toJSONString(hdPromotionPrizeUnion),score);
			
			rtn = hdPromotionPrizeMapper.updateByPrimaryKeySelective(pp);
		}
		return rtn;
	}
	
	/**
	 * 保存抽奖记录
	 * @param record
	 * @return
	 */
	public int saveHdPrizeRecord(HdPrizeRecord record){
		return hdPrizeRecordMapper.insert(record);
	}
	
	/**
	 * 更新抽奖记录
	 */
	public int updateHdPrizeRecord(HdPrizeRecord record){
		return hdPrizeRecordMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 保存活动排名
	 * @param record
	 * @return
	 */
	public int saveHdPromotionSort(HdPromotionSort record){
		return hdPromotionSortMapper.insert(record);
	}
	
	/**
	 * 更新活动排名
	 */
	public int updateHdPromotionSort(HdPromotionSort record){
		return hdPromotionSortMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 查询活动排名
	 * @param promotionId
	 * @param memeberId
	 * @return
	 */
	public HdPromotionSort queryPromotionSort(String promotionId, String memberId){
		return hdPromotionSortMapper.selectByPromotionAndMemberId(memberId, promotionId);
	}
	
	/**
	 * 保存收件人信息
	 * @param record
	 * @return
	 */
	public int saveHdReceiverInfo(HdReceiverInfo record){
		return hdReceiverInfoMapper.insert(record);
	}
	
	/**
	 * 更新收件人信息
	 * @param record
	 * @return
	 */
	public int updateHdReceiverInfo(HdReceiverInfo record){
		int count = 0;
		try {
			count = hdReceiverInfoMapper.updateByPrimaryKeySelective(record);
//			String memberId = record.getMemberid();
//			if(StringUtil.isNotNullOrEmpty(memberId)){
//				HdReceiverInfo result = hdReceiverInfoMapper.selectByMemberId(record.getMemberid());
//				if(result != null){
//					redisOperation.del(Constant.CACHE_PROMOTION_RECEIVERINFO + memberId);
//					redisOperation.add(Constant.CACHE_PROMOTION_RECEIVERINFO + memberId, JSONObject.toJSONString(result));
//					redisOperation.expire(Constant.CACHE_PROMOTION_RECEIVERINFO + memberId, Long.parseLong(ParamPropertiesUtil.getCacheTimeOut()), TimeUnit.SECONDS);
//				}			
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 查询活动活跃人数
	 * @param promotionid
	 * @return
	 */
	public int queryActiveMember(String promotionid){
		return hdPrizeRecordMapper.countByPromotionId(promotionid);
	}
	
	/**
	 * 判断抓手机游戏得分是否在档位中
	 * @param bussinessStr
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryIsInSCoreStall(
			HdPromotionPrize hdPromotionPrize) {
		return hdPromotionPrizeMapper.selectStallBySelective(hdPromotionPrize);
	}
	
	/**
	 * 查询活动得分档位内的数据
	 * @param bussinessStr
	 * @return
	 */
	@Override
	public List<HdPromotionPrizeUnion> queryStallPrizeByPromotionId(
			String promotionId,String scoreNum) {
		List<HdPromotionPrizeUnion> result = null;
		try{
//			if(redisOperation.exists(Constant.CACHE_PROMOTION_PRIZE + promotionId)){
//				BusinessLogger.info("query all promotion prize from cache");
//				List<String> list = redisOperation.ZRange(Constant.CACHE_PROMOTION_PRIZE + promotionId, 0, -1);
//				if(!list.isEmpty()){
//					result = new ArrayList<HdPromotionPrizeUnion>();
//					Iterator<String> iter = list.iterator();
//					while(iter.hasNext()){
//						HdPromotionPrizeUnion info = JSONObject.toJavaObject(JSONObject.parseObject(iter.next()), HdPromotionPrizeUnion.class);
//						result.add(info);
//					}
//				}
//			}
//			else{
				BusinessLogger.info("query all promotion prize from db");
				List<HdPromotionPrizeUnion> union = hdPromotionPrizeMapper.selectStallPrizeByPromotionId(promotionId,scoreNum);
				if(union.size() > 0){
//					for(int i=0;i<union.size();i++){
//						HdPromotionPrizeUnion info = union.get(i);
//						redisOperation.ZAdd(Constant.CACHE_PROMOTION_PRIZE + promotionId, JSONObject.toJSONString(info), (double)i+1);
//					}
//					redisOperation.expire(Constant.CACHE_PROMOTION_PRIZE + promotionId, Long.parseLong(ParamPropertiesUtil.getCacheTimeOut()), TimeUnit.SECONDS);
					result = union;
				}
//			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
