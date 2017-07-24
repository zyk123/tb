package com.flash.toolbar.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.BusinessLogger;
import com.flash.toolbar.common.util.Constant;
import com.flash.toolbar.common.util.DateUtil;
import com.flash.toolbar.common.util.ParamPropertiesUtil;
import com.flash.toolbar.common.util.StringUtil;
import com.flash.toolbar.model.HdPrizeRecord;
import com.flash.toolbar.model.HdPrizeShip;
import com.flash.toolbar.model.HdPrizeShipUnion;
import com.flash.toolbar.model.HdPromotionInfo;
import com.flash.toolbar.model.HdPromotionPrize;
import com.flash.toolbar.model.HdPromotionPrizeUnion;
import com.flash.toolbar.model.HdPromotionSort;
import com.flash.toolbar.model.HdReceiverInfo;
import com.flash.toolbar.model.SysTelecomOperatorAndHyMember;
import com.flash.toolbar.redis.RedisOperation;
import com.flash.toolbar.service.LotteryFileService;
import com.flash.toolbar.service.MemberService;
import com.flash.toolbar.service.PromotionService;

@Service(value="promotionManager")
public class PromotionManagerImpl implements PromotionManager{
	
	@Resource
	private PromotionService promotionService;
	

	@Resource
	private LotteryFileService lotteryFileService;

	
	@Autowired
	private RedisOperation redisOperation;	
	
	@Resource
	private MemberService memberService;
	
	/**
	 * 获取已发布的活动
	 */
	@Override
	public String getPromotionList(String bussinessStr) {
		JSONObject json = (JSONObject) JSONObject.parse(bussinessStr);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getPromotionList", true, bussinessStr, String.valueOf(new Date().getTime()));
			String countryno = json.getString("countryNo");
			String toperatorid = json.getString("tOperatorId");
			
			JSONArray jsonArr = new JSONArray();
			List<HdPromotionInfo> list = promotionService.queryActivePromotion(countryno, toperatorid);
			if(list != null && list.size() > 0){
				for(int i = 0; i< list.size(); i++){
					JSONObject newobj = new JSONObject();
					newobj.put("promotionId", list.get(i).getPromotionid());
					newobj.put("bannerUrl", list.get(i).getBannerurl());
					newobj.put("name", list.get(i).getPromotionname());
					
					Date sDate = list.get(i).getStartdate();
					Date eDate = list.get(i).getEnddate();
					int days = DateUtil.daysBetween(DateUtil.getFormatDate(sDate), DateUtil.getFormatDate(eDate));
					newobj.put("dateDesc", DateUtil.getFormatDate(sDate) + " on the "+days + " day");
					
					int activeNum = promotionService.queryActiveMember(list.get(i).getPromotionid());
					newobj.put("activeNum", activeNum);
					
					jsonArr.add(newobj);
				}
			}
			
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getPromotionList", false, null, String.valueOf(new Date().getTime()));
			return jsonArr.toJSONString();
		}
		else{
			BusinessLogger.LoggerInfo("", "tsc", "getPromotionList", false, "json empty", String.valueOf(new Date().getTime()));
		}
		return null;
	}

	/**
	 * 获取奖品信息
	 */
	@Override
	public String getPromotionPrize(String bussinessStr) {
		JSONObject json = (JSONObject) JSONObject.parse(bussinessStr);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getPromotionPrize", true, bussinessStr, String.valueOf(new Date().getTime()));
			String memberId = json.getString("memberId");
			String promotionId = json.getString("promotionId");
//			String countryno = json.getString("countryNo");
//			String toperatorid = json.getString("tOperatorId");
			
			JSONObject jsonBody = new JSONObject();
			try{
				HdPromotionInfo proInfo = promotionService.queryPromotionInfo(promotionId);
				if(proInfo != null){
					jsonBody.put("promotionId", promotionId);
					jsonBody.put("promotionName", proInfo.getPromotionname());
					jsonBody.put("promotionDesc", proInfo.getPromotiondesc());
					jsonBody.put("prizeDesc", proInfo.getPrizedesc());
					jsonBody.put("promotionLimits", proInfo.getOnedaytimes());
					jsonBody.put("countDown", proInfo.getCountdown());
					
					Date start = proInfo.getStartdate();
					Date end = proInfo.getEnddate();		
					
					Date now = new Date();
					String timeZone = null;
					//查询时区
					SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(memberId);
					if(memsys != null){
						timeZone = memsys.getTimezone();
					}
					if(timeZone != null){
						now = DateUtil.getTimeZoneDate(new Date(), timeZone, "yyyyMMddHHmmss");
					}
					if(now.compareTo(start) > 0 && now.compareTo(end) < 0){
						jsonBody.put("promotionStatus", "1");
					}
					else{
						jsonBody.put("promotionStatus", "0");
					}
					
					//查询用户剩余抽奖次数
					HdPrizeRecord prizeRecord = promotionService.queryPrizeRecord(memberId, promotionId);
					if(prizeRecord != null){
						int dayNum = prizeRecord.getDaynum();
						jsonBody.put("userLimits", proInfo.getOnedaytimes() - dayNum);
					}
					else{
						jsonBody.put("userLimits", proInfo.getOnedaytimes());
					}
					//查询活动分配的真实奖品
					List<HdPromotionPrizeUnion> union = promotionService.queryAllPrizeByPromotionId(promotionId);
					JSONArray arr = new JSONArray();
					if(union != null && union.size() > 0){
						for(HdPromotionPrizeUnion ppu : union){
							if(ppu.getPrizetype().equals("1")){
								JSONObject obj = new JSONObject();
								obj.put("prizeId", ppu.getPrizeid());
								obj.put("prizeLevel", ppu.getPrizelevel());
								obj.put("prizeTotalNum", ppu.getPrizetotalnum());
								obj.put("prizeRestNum", ppu.getPrizerestnum());
								obj.put("probability", ppu.getProbability());
								obj.put("prizeName", ppu.getPrizename());
								obj.put("prizeDesc", ppu.getPrizedesc());
								obj.put("iconUrl", ppu.getIconurl());
								obj.put("backgroundUrl", ppu.getBackgroundurl());
								arr.add(obj);
							}
						}
						jsonBody.put("promotionPrizes", arr);
					}
					else{
						jsonBody.put("promotionPrizes", arr);
					}
					BusinessLogger.LoggerInfo(requestSerial, "tsc", "getPromotionPrize", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
					return jsonBody.toJSONString();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}
		else{
			BusinessLogger.LoggerInfo("", "tsc", "getPromotionPrize", false, "json empty", String.valueOf(new Date().getTime()));
		}
		return null;
	}

	/**
	 * 获取奖品
	 */
	@Override
	public String winPrize(String bussinessStr) {
		try{
	    Boolean lock = redisOperation.setNX("winprizelock", "winPrizeLock");
	    if(lock){
		JSONObject json = (JSONObject) JSONObject.parse(bussinessStr);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "winPrize", true, bussinessStr, String.valueOf(new Date().getTime()));
			String memberId = json.getString("memberId");
			String promotionId = json.getString("promotionId");
			String countryNo = json.getString("countryNo");
			String tOperatorId = json.getString("tOperatorId");
			
			JSONObject jsonBody = new JSONObject();
			try{
				BusinessLogger.info("query promotion");
				//获取活动过期时间
				HdPromotionInfo proInfo = promotionService.queryPromotionInfo(promotionId);
				if(proInfo != null){
					BusinessLogger.info("query prize record");
					//查询用户剩余抽奖次数
					HdPrizeRecord prizeRecord = promotionService.queryPrizeRecord(memberId, promotionId);
					if(prizeRecord != null){
						Date modifyDate = prizeRecord.getModifydate();
						String modifDateStr = DateUtil.getFormatDate(modifyDate, "yyyy-MM-dd");
						Date newDate = new Date();
						String nowDate = DateUtil.getFormatDate(newDate, "yyyy-MM-dd");
						int dayNum = 0;
						if(!nowDate.equals(modifDateStr)){//如果不是当天的记录
							dayNum = 1;
							HdPrizeRecord prizeRecordT = new HdPrizeRecord();
							prizeRecordT.setRecordid(prizeRecord.getRecordid());
							prizeRecordT.setDaynum(0);//在下面更新当前抽奖次数，所以设置为0
							prizeRecordT.setModifydate(newDate);
							prizeRecord.setDaynum(0);
							promotionService.updateHdPrizeRecord(prizeRecordT);
						}else{
							dayNum = prizeRecord.getDaynum()+1;
						}
						int total = proInfo.getOnedaytimes();
						if(dayNum > total){
							jsonBody.put("retCode", Constant.RETURN_FAILURE);
							jsonBody.put("retMsg", "out of count");
							BusinessLogger.LoggerInfo(requestSerial, "tsc", "winPrize", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
							redisOperation.del("winprizelock");
							return jsonBody.toJSONString();
						}
					}
					BusinessLogger.info("query all prize");
					//查询所有分配的奖品
					List<HdPromotionPrizeUnion> union = promotionService.queryAllPrizeByPromotionId(promotionId);
					if(union != null && union.size() > 0){
						SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(memberId);
						String mobileno = memsys == null?"":memsys.getMobileno();
						BusinessLogger.info("get prize probability");
						//概率数组{序号, 概率, 是否真实奖品}
						List<Integer[]> prob = new ArrayList<Integer[]>();
						for(int i = 0; i < union.size(); i++){
							HdPromotionPrizeUnion ppu = union.get(i);
							prob.add(new Integer[]{i, ppu.getProbability(), Integer.parseInt(ppu.getPrizetype())});
						}
						BusinessLogger.info("get prize");
						//计算概率获取奖项				
						int index = getRandomId(prob);
						HdPromotionPrizeUnion hdPromotionPrizeUnion = union.get(index);
						String shipId = "";
						String promotionprizeid = "";
						if(hdPromotionPrizeUnion.getPrizetype().equals("1")||hdPromotionPrizeUnion.getPrizetype().equals("2")){
							BusinessLogger.info("Congratulations! You got " + hdPromotionPrizeUnion.getPrizelevel() + ":" + hdPromotionPrizeUnion.getPrizename());
							jsonBody.put("lucky", true);
							promotionprizeid = union.get(index).getPromotionprizeid();
							//如果中奖，则初始化奖品发货信息
							HdPrizeShip ship = new HdPrizeShip();
							shipId = StringUtil.formatUUID(false);
							ship.setShipid(shipId);
							ship.setMemberid(memberId);
							ship.setMobileno(mobileno);
							ship.setPromotionid(union.get(index).getPromotionid());
							ship.setPrizeid(union.get(index).getPrizeid());
							ship.setShipstatus("0");
							ship.setWinningdate(new Date());
							ship.setCountryno(countryNo);
							ship.setToperatorid(tOperatorId);
							ship.setModifyman("");
							ship.setModifydate(new Date());
							ship.setManorrobot("1");
							//查询是否填写过联系人信息 有则将发货状态置为1
							HdReceiverInfo receiver = promotionService.queryReceiverInfo(memberId);
							if(receiver != null){
								ship.setShipstatus("1");
								ship.setReceiverid(receiver.getReceiverid());
							}
							promotionService.saveHdPrizeShip(ship);
							//同时将活动奖品-1
							promotionService.updateHdPromotionPrize(hdPromotionPrizeUnion);
						}
						else{
							BusinessLogger.info("Sorry! Try next time.");
							jsonBody.put("lucky", false);
						}
						jsonBody.put("prizeId", union.get(index).getPrizeid());
						jsonBody.put("orderno", union.get(index).getOrderno());
						jsonBody.put("prizetype", union.get(index).getPrizetype());
						jsonBody.put("angle", 0);
						jsonBody.put("mobileNo", mobileno);
						jsonBody.put("prizeLevel", union.get(index).getPrizelevel());
						jsonBody.put("prizeName", union.get(index).getPrizename());
						jsonBody.put("shipId", shipId);
						jsonBody.put("promotionprizeid", promotionprizeid);
						//更新用户抽奖记录
						if(prizeRecord != null){
							int dayNum = prizeRecord.getDaynum();
							jsonBody.put("userLimits", proInfo.getOnedaytimes() - dayNum - 1);						
							//更新用户每日抽奖次数 +1
							prizeRecord.setDaynum(prizeRecord.getDaynum()+1);
							prizeRecord.setTotalnum(prizeRecord.getTotalnum()+1);
							prizeRecord.setModifydate(new Date());
							promotionService.updateHdPrizeRecord(prizeRecord);
						}
						else{
							jsonBody.put("userLimits", proInfo.getOnedaytimes() - 1);
							//新增用户抽奖记录
							prizeRecord = new HdPrizeRecord();
							prizeRecord.setRecordid(StringUtil.formatUUID(false));
							prizeRecord.setMemberid(memberId);
							prizeRecord.setMobileno(mobileno);
							prizeRecord.setPromotionid(promotionId);
							prizeRecord.setDaynum(1);
							prizeRecord.setTotalnum(1);
							prizeRecord.setCountryno(countryNo);
							prizeRecord.setToperatorid(tOperatorId);
							prizeRecord.setModifyman("");
							prizeRecord.setModifydate(new Date());
							promotionService.saveHdPrizeRecord(prizeRecord);
						}
						//记录话单
						lotteryFileService.savePromtionCsv(requestSerial, mobileno, jsonBody.getBoolean("lucky"), proInfo.getPromotionname(), union.get(index).getPrizename());

						BusinessLogger.LoggerInfo(requestSerial, "tsc", "winPrize", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
						redisOperation.del("winprizelock");
						return jsonBody.toJSONString();
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			BusinessLogger.LoggerInfo("", "tsc", "winPrize", false, "json empty", String.valueOf(new Date().getTime()));
		}
		redisOperation.del("winprizelock");
		}
		}catch(Exception e){
			redisOperation.del("winprizelock");
		}
		return null;
	}
	
	
	/**
	 * 获取抽手机游戏奖品
	 */
	@Override
	public String phoneGameLuckDraw(String bussinessStr) {
		try{
	    Boolean lock = redisOperation.setNX("phonegameprizelock", "phonegameprizelock");
	    if(lock){
		JSONObject json = (JSONObject) JSONObject.parse(bussinessStr);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "phoneGameLuckDraw", true, bussinessStr, String.valueOf(new Date().getTime()));
			String memberId = json.getString("memberId");
			String promotionId = json.getString("promotionId");
			String countryNo = json.getString("countryNo");
			String tOperatorId = json.getString("tOperatorId");
			String mobileNo = json.getString("mobileNo");
			String scoreNum = json.getString("scoreNum");
			
			JSONObject jsonBody = new JSONObject();
			try{
				BusinessLogger.info("query promotion");
				//获取活动过期时间
				HdPromotionInfo proInfo = promotionService.queryPromotionInfo(promotionId);
				if(proInfo != null){
					BusinessLogger.info("query prize record");
					//查询用户剩余抽奖次数
					HdPrizeRecord prizeRecord = promotionService.queryPrizeRecord(memberId, promotionId);
					if(prizeRecord != null){
						Date modifyDate = prizeRecord.getModifydate();
						String modifDateStr = DateUtil.getFormatDate(modifyDate, "yyyy-MM-dd");
						Date newDate = new Date();
						String nowDate = DateUtil.getFormatDate(newDate, "yyyy-MM-dd");
						int dayNum = 0;
						if(!nowDate.equals(modifDateStr)){//如果不是当天的记录
							dayNum = 1;
							HdPrizeRecord prizeRecordT = new HdPrizeRecord();
							prizeRecordT.setRecordid(prizeRecord.getRecordid());
							prizeRecordT.setDaynum(0);//在下面更新当前抽奖次数，所以设置为0
							prizeRecordT.setModifydate(newDate);
							prizeRecord.setDaynum(0);
							promotionService.updateHdPrizeRecord(prizeRecordT);
						}else{
							dayNum = prizeRecord.getDaynum()+1;
						}
						int total = proInfo.getOnedaytimes();
//						if(dayNum > total){
//							jsonBody.put("retCode", Constant.RETURN_FAILURE);
//							jsonBody.put("retMsg", "out of count");
//							BusinessLogger.LoggerInfo(requestSerial, "tsc", "phoneGameLuckDraw", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
//							redisOperation.del("phonegameprizelock");
//							return jsonBody.toJSONString();
//						}
					}
					BusinessLogger.info("query all prize");
					//查询所有分配的奖品
					List<HdPromotionPrizeUnion> union = promotionService.queryStallPrizeByPromotionId(promotionId,scoreNum);
					if(union != null && union.size() > 0){
						String mobileno = mobileNo;
						BusinessLogger.info("get prize probability");
						//概率数组{序号, 概率, 是否真实奖品}
						List<Integer[]> prob = new ArrayList<Integer[]>();
						for(int i = 0; i < union.size(); i++){
							HdPromotionPrizeUnion ppu = union.get(i);
							prob.add(new Integer[]{i, ppu.getProbability(), Integer.parseInt(ppu.getPrizetype())});
						}
						BusinessLogger.info("get prize");
						//计算概率获取奖项				
						int index = getRandomId(prob);
						HdPromotionPrizeUnion hdPromotionPrizeUnion = union.get(index);
						if("1".equals(hdPromotionPrizeUnion.getPrizetype())){
							BusinessLogger.info("Congratulations! You got " + hdPromotionPrizeUnion.getPrizelevel() + ":" + hdPromotionPrizeUnion.getPrizename());
							jsonBody.put("lucky", true);
							//如果中奖，则初始化奖品发货信息
							String shipId = StringUtil.formatUUID(false);
							jsonBody.put("shipId", shipId);
							jsonBody.put("promotionPrizeId", union.get(index).getPromotionprizeid());
							HdPrizeShip ship = new HdPrizeShip();
							ship.setShipid(shipId);
							ship.setMemberid(memberId);
							ship.setMobileno(mobileno);
							ship.setPromotionid(union.get(index).getPromotionid());
							ship.setPrizeid(union.get(index).getPrizeid());
							ship.setShipstatus("0");
							ship.setWinningdate(new Date());
							ship.setCountryno(countryNo);
							ship.setToperatorid(tOperatorId);
							ship.setModifyman("");
							ship.setModifydate(new Date());
							ship.setManorrobot("1");
							//查询是否填写过联系人信息 有则将发货状态置为1
							HdReceiverInfo receiver = promotionService.queryReceiverInfo(memberId);
							if(receiver != null){
								ship.setShipstatus("1");
								ship.setReceiverid(receiver.getReceiverid());
								jsonBody.put("receiverId", receiver.getReceiverid());
							}
							promotionService.saveHdPrizeShip(ship);
							//同时将活动奖品-1
							promotionService.updateHdPromotionPrize(hdPromotionPrizeUnion);
						}else if("2".equals(hdPromotionPrizeUnion.getPrizetype())){
							BusinessLogger.info("Congratulations! You got " + hdPromotionPrizeUnion.getPrizelevel() + ":" + hdPromotionPrizeUnion.getPrizename());
							jsonBody.put("lucky", true);
							//如果中奖，则初始化奖品发货信息
							String shipId = StringUtil.formatUUID(false);
							jsonBody.put("shipId", shipId);
							jsonBody.put("promotionPrizeId", union.get(index).getPromotionprizeid());
							HdPrizeShip ship = new HdPrizeShip();
							ship.setShipid(shipId);
							ship.setMemberid(memberId);
							ship.setMobileno(mobileno);
							ship.setPromotionid(union.get(index).getPromotionid());
							ship.setPrizeid(union.get(index).getPrizeid());
							ship.setShipstatus("0");
							ship.setWinningdate(new Date());
							ship.setCountryno(countryNo);
							ship.setToperatorid(tOperatorId);
							ship.setModifyman("");
							ship.setModifydate(new Date());
							ship.setManorrobot("1");
							promotionService.saveHdPrizeShip(ship);
							//同时将活动奖品-1
							promotionService.updateHdPromotionPrize(hdPromotionPrizeUnion);
						}else{
							BusinessLogger.info("Sorry! Try next time.");
							jsonBody.put("lucky", false);
						}
						jsonBody.put("prizeId", union.get(index).getPrizeid());
						jsonBody.put("prizeType", union.get(index).getPrizetype());
						jsonBody.put("angle", 0);
						jsonBody.put("mobileNo", mobileno);
						jsonBody.put("prizeLevel", union.get(index).getPrizelevel());
						jsonBody.put("prizeName", union.get(index).getPrizename());
						//更新用户抽奖记录
						if(prizeRecord != null){
							int dayNum = prizeRecord.getDaynum();
							jsonBody.put("userLimits", proInfo.getOnedaytimes() - dayNum - 1);						
							//更新用户每日抽奖次数 +1
							prizeRecord.setDaynum(prizeRecord.getDaynum()+1);
							prizeRecord.setTotalnum(prizeRecord.getTotalnum()+1);
							prizeRecord.setModifydate(new Date());
							promotionService.updateHdPrizeRecord(prizeRecord);
						}
						else{
							jsonBody.put("userLimits", proInfo.getOnedaytimes() - 1);
							//新增用户抽奖记录
							prizeRecord = new HdPrizeRecord();
							prizeRecord.setRecordid(StringUtil.formatUUID(false));
							prizeRecord.setMemberid(memberId);
							prizeRecord.setMobileno(mobileno);
							prizeRecord.setPromotionid(promotionId);
							prizeRecord.setDaynum(1);
							prizeRecord.setTotalnum(1);
							prizeRecord.setCountryno(countryNo);
							prizeRecord.setToperatorid(tOperatorId);
							prizeRecord.setModifyman("");
							prizeRecord.setModifydate(new Date());
							promotionService.saveHdPrizeRecord(prizeRecord);
						}
						//记录话单
						lotteryFileService.savePromtionCsv(requestSerial, mobileno, jsonBody.getBoolean("lucky"), proInfo.getPromotionname(), union.get(index).getPrizename());

						BusinessLogger.LoggerInfo(requestSerial, "tsc", "phoneGameLuckDraw", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
						redisOperation.del("phonegameprizelock");
						return jsonBody.toJSONString();
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			BusinessLogger.LoggerInfo("", "tsc", "phoneGameLuckDraw", false, "json empty", String.valueOf(new Date().getTime()));
		}
		redisOperation.del("phonegameprizelock");
		}
		}catch(Exception e){
			e.printStackTrace();
			redisOperation.del("phonegameprizelock");
		}
		return null;
	}	
	
	
	
	
	/**
	 * 获得随机奖品
	 * @param prob 概率数组new Integer[]{序号, 概率, 是否真实奖品}
	 * @return
	 */
	private int getRandomId(List<Integer[]> prob){
		Integer result = null;
		try{
			//计算总概率
			int sum = 0;
			for(int i=0;i<prob.size();i++){
				sum += prob.get(i)[1];
			}
			//重新打乱奖品的排序
			List<Integer[]> newprob = new ArrayList<Integer[]>();
			while(prob.size()>0){
				int random = new Random().nextInt(prob.size());
				if(random != prob.size()){
					newprob.add(prob.get(random));
					prob.remove(random);
				}
			}
			//随机概率
			for(int i=0;i<newprob.size();i++){
				int randomNum = new Random().nextInt(sum);//随机生成0到sum的整数
				if(randomNum < newprob.get(i)[1]){
					result = newprob.get(i)[0];
					break;
				}
				else{
					sum -= newprob.get(i)[1];
				}
			}
			//如果通过概率没有随机到，则从虚拟奖品中随机一个
			if(result == null){
				BusinessLogger.info("not random anyone");
				List<Integer[]> virprob = new ArrayList<Integer[]>();
				for(int i=0;i<newprob.size();i++){
					if(newprob.get(i)[2] == 0){
						virprob.add(newprob.get(i));
					}
				}
				for(int i=0;i<virprob.size();i++){
					int random = new Random().nextInt(virprob.size());
					if(random != virprob.size()){
						result = virprob.get(i)[0];
						break;
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取中奖记录
	 */
	@Override
	public String getLuckyList(String bussinessStr) {
		JSONObject json = (JSONObject) JSONObject.parse(bussinessStr);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getLuckyList", true, bussinessStr, String.valueOf(new Date().getTime()));
			String promotionId = json.getString("promotionId");
			int startNum = json.getIntValue("startNum");
			int endNum = json.getIntValue("endNum");
			
			try{
				List<HdPrizeShipUnion> list = promotionService.queryLuckyList(promotionId, startNum, endNum);
				if(list != null && list.size()>0){
					return JSONArray.toJSONString(list);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			BusinessLogger.LoggerInfo("", "tsc", "getLuckyList", false, "json empty", String.valueOf(new Date().getTime()));
		}
		return null;
	}

	/**
	 * 保存手速活动分数
	 */
	@Override
	public String saveSpeedRecord(String bussinessStr) {
		JSONObject json = (JSONObject) JSONObject.parse(bussinessStr);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "saveSpeedRecord", true, bussinessStr, String.valueOf(new Date().getTime()));
			String promotionId = json.getString("promotionId");
			String memberId = json.getString("memberId");
			int score = json.getInteger("score");
			String countryNo = json.getString("countryNo");
			String tOperatorId = json.getString("tOperatorId");
			int startNum = json.getIntValue("startNum");
			int endNum = json.getIntValue("endNum");

			JSONObject jsonBody = new JSONObject();
			try{
				SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(memberId);
				if(memsys != null){
					String mobileno = memsys.getMobileno();	
					//设置过期时间到活动截止时间
					long expireTimes = Long.parseLong(ParamPropertiesUtil.getCacheTimeOut());
					HdPromotionInfo proInfo = promotionService.queryPromotionInfo(promotionId);
					if(proInfo != null){
						BusinessLogger.info("get expire time from now to the promotion end date");
						Date end = proInfo.getEnddate();
						String timeZone = memsys.getTimezone();
						Date now = DateUtil.getTimeZoneDate(new Date(), timeZone, "yyyyMMddHHmmss");
						if(end.compareTo(now) > 0){
							expireTimes = (end.getTime() - now.getTime())/1000;
							int topScore = score;//历史最高分
							BusinessLogger.info("query member score from db");
							HdPromotionSort record = promotionService.queryPromotionSort(promotionId, memberId);
							if(record != null){
								record.setTotalnum(record.getTotalnum()+1);
								record.setLastscore(score);
								record.setLastscoredate(new Date());
								record.setModifydate(new Date());
								//如果本次分数大于历史最高分则更新历史最高分
								if(score > record.getTopscore()){
									BusinessLogger.info("update top score");
									record.setTopscore(score);
									record.setTopscoredate(new Date());
									jsonBody.put("topScoreDate", DateUtil.getFormatDate(new Date()));
								}
								else{
									topScore = record.getTopscore();
									jsonBody.put("topScoreDate", DateUtil.getFormatDate(record.getTopscoredate()));
								}
								promotionService.updateHdPromotionSort(record);
								
								jsonBody.put("isFirstPlay", 0);
								jsonBody.put("lastScore", record.getLastscore());
								jsonBody.put("lastScoreDate", DateUtil.getFormatDate(record.getLastscoredate()));
								jsonBody.put("topScore", topScore);
							}
							else{
								record = new HdPromotionSort();
								record.setSortid(StringUtil.formatUUID(false));
								record.setMemberid(memberId);
								record.setPromotionid(promotionId);
								record.setMobileno(mobileno);
								record.setTotalnum(1);
								record.setLastscore(score);
								record.setLastscoredate(new Date());
								record.setTopscore(score);
								record.setTopscoredate(new Date());
								record.setCountryno(countryNo);
								record.setToperatorid(tOperatorId);
								record.setModifyman("");
								record.setModifydate(new Date());
								promotionService.saveHdPromotionSort(record);
								
								jsonBody.put("isFirstPlay", 1);
								jsonBody.put("lastScore", 0);
								jsonBody.put("lastScoreDate", 0);
								jsonBody.put("topScore", score);
								jsonBody.put("topScoreDate", DateUtil.getFormatDate(new Date()));
							}
							
							//更新redis缓存
							BusinessLogger.info("update promotion sort to cache");
							redisOperation.ZAdd(Constant.CACHE_PROMOTION_SCORE + promotionId, mobileno, (double)topScore);
							redisOperation.expire(Constant.CACHE_PROMOTION_SCORE + promotionId, expireTimes, TimeUnit.SECONDS);
							
							//从redis中获取分数排名
							BusinessLogger.info("get ranking from cache");
							long sortNum = redisOperation.ZRevRank(Constant.CACHE_PROMOTION_SCORE + promotionId, mobileno) + 1;//当前排名
							long totalNum = redisOperation.ZCard(Constant.CACHE_PROMOTION_SCORE + promotionId);//总人数							
							jsonBody.put("sortNum", sortNum);
							jsonBody.put("totalNum", totalNum);
							
							//获取分数排名
							BusinessLogger.info("get promotion rank");
							JSONArray array = new JSONArray();
							List<HdPromotionSort> list = promotionService.querySortList(promotionId, startNum, endNum);
							if(list != null && list.size() > 0){
								for(int i = 0; i<list.size();i++){
									HdPromotionSort info = list.get(i);
									JSONObject obj = new JSONObject();
									obj.put("sortNum", i+1);
									obj.put("mobileNo", info.getMobileno());
									obj.put("topScore", info.getTopscore());
									obj.put("topScoreDate", DateUtil.getFormatDate(info.getTopscoredate()));
									array.add(obj);
								}
							}
							jsonBody.put("sortList", array);
							
							jsonBody.put("retCode", Constant.RETURN_SUCCESS);
							jsonBody.put("retMsg", "success");
							return jsonBody.toJSONString();
						}
						else{
							BusinessLogger.info("promotion out of end date");
						}
					}
					else{
						BusinessLogger.info("promotion not found");
					}
				}
				else{
					BusinessLogger.info("member not found");
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			BusinessLogger.LoggerInfo("", "tsc", "saveSpeedRecord", false, "json empty", String.valueOf(new Date().getTime()));
		}
		return null;
	}

	/**
	 * 获取手速活动分数排名
	 */
	@Override
	public String getSpeedList(String bussinessStr) {		
		return null;
	}

	/**
	 * 获取我的中奖记录
	 */
	@Override
	public String getMyLuckyList(String bussinessStr) {
		JSONObject json = (JSONObject) JSONObject.parse(bussinessStr);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getMyLuckyList", true, bussinessStr, String.valueOf(new Date().getTime()));
			String memberId = json.getString("memberId");
			
			try{
				List<HdPrizeShipUnion> list = promotionService.queryMyLuckyList(memberId);
				if(list != null && list.size()>0){
					return JSONArray.toJSONString(list);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			BusinessLogger.LoggerInfo("", "tsc", "getMyLuckyList", false, "json empty", String.valueOf(new Date().getTime()));
		}
		return null;
	}

	/**
	 * 保存收件人信息
	 */
	@Override
	public String saveReceiverInfo(String bussinessStr) {
		JSONObject json = (JSONObject) JSONObject.parse(bussinessStr);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getReceiverInfo", true, bussinessStr, String.valueOf(new Date().getTime()));
			String countryNo = json.getString("countryNo");
			String tOperatorId = json.getString("tOperatorId");
			String memberId = json.getString("memberId");
			String receiverName = json.getString("receiverName");
			String gender = json.getString("gender");
			String identityCard = json.getString("identityCard");
			String email = json.getString("email");
			String address = json.getString("address");
			String age = json.getString("age");
			String receiverId = json.getString("receiverid");
			String remark = json.getString("remark");
			
			try{
				if("".equals(receiverId)){
					SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(memberId);
					if(memsys != null){
						String mobileno = memsys.getMobileno();	
						
						HdReceiverInfo info = new HdReceiverInfo();
						info.setReceiverid(StringUtil.formatUUID(false));
						info.setMemberid(memberId);
						info.setMobileno(mobileno);
						info.setReceivername(receiverName);
						info.setGender(gender);
						info.setEmail(email);
						info.setIdentitycard(identityCard);
						info.setAge(age);
						info.setReceiveraddress(address);
						info.setCountryno(countryNo);
						info.setToperatorid(tOperatorId);
						info.setModifyman("");
						info.setModifydate(new Date());
						info.setRemark(remark);
						promotionService.saveHdReceiverInfo(info);
						
						JSONObject jsonBody = new JSONObject();
						jsonBody.put("retCode", Constant.RETURN_SUCCESS);
						jsonBody.put("retMsg", "success");
						return jsonBody.toJSONString();
					}
					else{
						BusinessLogger.info("member not found");
					}					
				}else{
					HdReceiverInfo info = new HdReceiverInfo();
					info.setReceiverid(StringUtil.formatUUID(false));
					info.setMemberid(memberId);
					info.setReceiverid(receiverId);
					info.setReceivername(receiverName);
					info.setGender(gender);
					info.setEmail(email);
					info.setIdentitycard(identityCard);
					info.setReceiveraddress(address);
					info.setCountryno(countryNo);
					info.setToperatorid(tOperatorId);
					info.setModifyman("");
					info.setModifydate(new Date());
					info.setRemark(remark);
					promotionService.updateHdReceiverInfo(info);
					JSONObject jsonBody = new JSONObject();
					jsonBody.put("retCode", Constant.RETURN_SUCCESS);
					jsonBody.put("retMsg", "success");
					return jsonBody.toJSONString();
				}

			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			BusinessLogger.LoggerInfo("", "tsc", "getReceiverInfo", false, "json empty", String.valueOf(new Date().getTime()));
		}
		return null;
	}
	
	/**
	 * 更新manOrRobot
	 * @param bussinessStr
	 * @return
	 */
	@Override
	public String updateManOrRobotInfo(String bussinessStr) {
		JSONObject json = (JSONObject) JSONObject.parse(bussinessStr);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "updateManOrRobotInfo", true, bussinessStr, String.valueOf(new Date().getTime()));
			String countryNo = json.getString("countryNo");
			String tOperatorId = json.getString("tOperatorId");
			String shipId = json.getString("shipId");
			String status = json.getString("status");
			String promotionprizeid = json.getString("promotionprizeid");
			
			try{
				    HdPrizeShip ship = new HdPrizeShip();
				    ship.setShipid(shipId);
				    ship.setManorrobot(status);
					promotionService.updateHdPrizeShip(ship);
					if("2".equals(status)){
						promotionService.increaseHdPromotionPrize(promotionprizeid);
					}
					JSONObject jsonBody = new JSONObject();
					jsonBody.put("retCode", Constant.RETURN_SUCCESS);
					jsonBody.put("retMsg", "success");
					return jsonBody.toJSONString();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			BusinessLogger.LoggerInfo("", "tsc", "updateManOrRobotInfo", false, "json empty", String.valueOf(new Date().getTime()));
		}
		return null;
	}

	/**
	 * 获取收件人信息
	 */
	@Override
	public String getReceiverInfo(String bussinessStr) {
		JSONObject json = (JSONObject) JSONObject.parse(bussinessStr);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getReceiverInfo", true, bussinessStr, String.valueOf(new Date().getTime()));
			String memberId = json.getString("memberId");
			
			try{
				HdReceiverInfo info = promotionService.queryReceiverInfo(memberId);
				if(info != null){
					return JSONObject.toJSONString(info);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			BusinessLogger.LoggerInfo("", "tsc", "getReceiverInfo", false, "json empty", String.valueOf(new Date().getTime()));
		}
		return null;
	}
	
	/**
	 * 查询每日剩余次数
	 * @param bussinessStr
	 * @return
	 */
	@Override
	public String getRestOnedaytimes(String bussinessStr) {
		JSONObject json = (JSONObject) JSONObject.parse(bussinessStr);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getRestOnedaytimes", true, bussinessStr, String.valueOf(new Date().getTime()));
			String memberId = json.getString("memberId");
			String promotionId = json.getString("promotionId");
			JSONObject jsonBody = new JSONObject();
			//获取活动过期时间
			HdPromotionInfo proInfo = promotionService.queryPromotionInfo(promotionId);
			if(proInfo != null){
				int total = proInfo.getOnedaytimes();
				//查询用户剩余抽奖次数
				HdPrizeRecord prizeRecord = promotionService.queryPrizeRecord(memberId, promotionId);
				if(prizeRecord != null){
					    int dayNum = prizeRecord.getDaynum();
					    Date modifyDate = prizeRecord.getModifydate();
						String modifDateStr = DateUtil.getFormatDate(modifyDate, "yyyy-MM-dd");
						Date newDate = new Date();
						String nowDate = DateUtil.getFormatDate(newDate, "yyyy-MM-dd");
						if(!nowDate.equals(modifDateStr)){//如果不是当天的记录
							dayNum = 0;
						}
						jsonBody.put("retCode", Constant.RETURN_SUCCESS);
						jsonBody.put("retMsg", "getRestOnedaytimes success");
						jsonBody.put("restOnedaytimes", total-dayNum);
						return jsonBody.toJSONString();
				}else{
					jsonBody.put("retCode", Constant.RETURN_SUCCESS);
					jsonBody.put("retMsg", "getRestOnedaytimes success");
					jsonBody.put("restOnedaytimes", total);
					return jsonBody.toJSONString();
				}
		  }else{
			    jsonBody.put("retCode", Constant.RETURN_SUCCESS);
				jsonBody.put("retMsg", "getRestOnedaytimes success");
				jsonBody.put("restOnedaytimes", 0);
				return jsonBody.toJSONString();
		  }
		}
		else{
			BusinessLogger.LoggerInfo("", "tsc", "getRestOnedaytimes", false, "json empty", String.valueOf(new Date().getTime()));
		}
		return null;
	}
	
	/**
	 * 判断抓手机游戏得分是否在档位中
	 * @param bussinessStr
	 * @return
	 */
	@Override
	public String judgeLimits(String bussinessStr) {
		JSONObject json = (JSONObject) JSONObject.parse(bussinessStr);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getJudgeLimits", true, bussinessStr, String.valueOf(new Date().getTime()));
			String promotionId = json.getString("promotionId");
			String countryno = json.getString("countryNo");
			String toperatorid = json.getString("tOperatorId");
			String scoreNum = json.getString("scoreNum");
			int scoreNumInt = StringUtil.isNotNullOrEmpty(scoreNum)?Integer.parseInt(scoreNum):0;
			boolean inStall = false;
			JSONObject jsonBody = new JSONObject();
			//获取活动过期时间
			HdPromotionPrize promotionPrizeInfo = new HdPromotionPrize();
			promotionPrizeInfo.setCountryno(countryno);
			promotionPrizeInfo.setToperatorid(toperatorid);
			promotionPrizeInfo.setPromotionid(promotionId);
			List<Map<String, Object>> list = promotionService.queryIsInSCoreStall(promotionPrizeInfo);
			
			if(list != null && list.size()>0){
					int start = Integer.valueOf(list.get(0).get("SCORESTART").toString());
					int end = Integer.valueOf(list.get(0).get("SCOREEND").toString());
					if(scoreNumInt>=start){
						inStall = true;
					}
				//查询用户剩余抽奖次数
					jsonBody.put("inStall", inStall);
					return jsonBody.toJSONString();
		  }else{
				jsonBody.put("inStall", false);
				return jsonBody.toJSONString();
		  }
		}
		else{
			BusinessLogger.LoggerInfo("", "tsc", "getJudgeLimits", false, "json empty", String.valueOf(new Date().getTime()));
		}
		return null;
	}

}
