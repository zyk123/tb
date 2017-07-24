package com.flash.toolbar.omp.promotionment.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.common.util.DateUtil;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.common.util.RuleUtil;
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.mapper.HdPrizeInfoMapper;
import com.flash.toolbar.omp.mapper.HdPromotionInfoMapper;
import com.flash.toolbar.omp.mapper.HdPromotionPrizeMapper;
import com.flash.toolbar.omp.model.HdPrizeInfo;
import com.flash.toolbar.omp.model.HdPromotionInfo;
import com.flash.toolbar.omp.model.HdPromotionPrize;
import com.flash.toolbar.omp.promotionment.bo.PromotionModel;
import com.flash.toolbar.omp.promotionment.bo.PromotionRelation;
import com.flash.toolbar.omp.promotionment.common.PromotionStatus;
import com.flash.toolbar.omp.promotionment.service.PromotionListService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Transactional
@Service
public class PromotionListServiceImpl implements PromotionListService {
	@Resource
	private HdPromotionInfoMapper hdPromotionInfoMapper;

	@Resource
	private HdPrizeInfoMapper hdPrizeInfoMapper;

	@Resource
	private HdPromotionPrizeMapper hdPromotionPrizeMapper;

	@Override
	public List<PromotionModel> getPromotionListInfo(
			PromotionModel promotionModel) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PromotionModel> selectPromotionListByPage(
			PromotionModel promotionModel, Page page) throws Exception {
		int totalRows = countByCondition(promotionModel);
		page.setTotalCount(totalRows);
		if (page.getCurrentPage() > page.getTotalPage()) {
			page.setCurrentPage(1);
		}
		if (0 < totalRows) {
			return hdPromotionInfoMapper.selectByPage(promotionModel, page);
		}
		return null;
	}

	@Override
	public int countByCondition(PromotionModel promotionModel) throws Exception {
		// TODO Auto-generated method stub
		return hdPromotionInfoMapper.countByCondition(promotionModel);
	}

	@Override
	public boolean insertPromotion(PromotionModel promotionModel) {
		if (hdPromotionInfoMapper.insert(promotionModel.getBean()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public HdPromotionInfo selectByPrimaryKey(HdPromotionInfo hdPromotionInfo) {
		return hdPromotionInfoMapper.selectByPrimaryKey(hdPromotionInfo
				.getPromotionid());
	}

	@Override
	public boolean updatePromotion(PromotionModel promotionModel) {
		if (hdPromotionInfoMapper.updateByPrimaryKeySelective(promotionModel
				.getBean()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deletePromotion(HdPromotionInfo hdPromotionInfo) {
		String promotionid = hdPromotionInfo.getPromotionid();
		HdPromotionInfo hpi = hdPromotionInfoMapper
				.selectByPrimaryKey(promotionid);
		BigDecimal status = hpi.getStatus();
		if (PromotionStatus.statusShift == status.intValue()
				|| PromotionStatus.statusFinish == status.intValue()) {
			return false;
		} else {
			hdPromotionPrizeMapper.returnPrize(promotionid);// 归还奖品
			hdPromotionPrizeMapper.deleteRelation(promotionid);// 删除关联关系
			hdPromotionInfoMapper.deleteByPrimaryKey(promotionid);// 删除活动
			return true;
		}
	}

	@Override
	public void saveRelation(List<?> list,QxUserModel userModel) {
			String countryno = userModel.getBean().getCountryno();
			String toperatorid = userModel.getBean().getToperatorid();
			String username = userModel.getBean().getUsername();
			Date date = DateUtil.getTimeZoneDate(new Date(), userModel.getTimeZone(), "yyyyMMdd");
			
			String promotionid = (String) ((Map)list.get(0)).get("promotionid");
			Map<String,String> map1 = new HashMap<String,String>();
			map1.put("toperatorid", toperatorid);
			map1.put("countryno", countryno);
			map1.put("promotionid", promotionid);
			List<PromotionRelation> listR = hdPromotionPrizeMapper.getDataGrid(map1);
			
			hdPromotionPrizeMapper.returnPrize(promotionid);// 归还奖品
			hdPromotionPrizeMapper.deleteRelation(promotionid);// 删除关联关系
			                                                   
			for(int i=1;i<list.size();i++){//扣减奖品数量并保存关系
				Map promotionPrizeModel = (Map) list.get(i);
				String prizeId = (String) promotionPrizeModel.get("prizeid");
				Object prizetotalnumO = promotionPrizeModel.get("prizetotalnum");
			    Integer prizetotalnum = (prizetotalnumO instanceof Integer) ? (Integer)prizetotalnumO:Integer.parseInt((String)prizetotalnumO);
			    String prizelevel = (String) promotionPrizeModel.get("prizelevel");
			    Integer probability = Integer.parseInt((String)promotionPrizeModel.get("probability"));
			    Integer scorestart = StringUtil.isNull(promotionPrizeModel.get("scorestart"))||promotionPrizeModel.get("scorestart").equals("null")? 0:Integer.parseInt((String)promotionPrizeModel.get("scorestart"));
			    Integer scoreend = StringUtil.isNull(promotionPrizeModel.get("scoreend"))||promotionPrizeModel.get("scoreend").equals("null")? 0:Integer.parseInt((String)promotionPrizeModel.get("scoreend"));
			    Integer orderno = StringUtil.isNull(promotionPrizeModel.get("orderno"))||promotionPrizeModel.get("orderno").equals("null")? 0:Integer.parseInt((String)promotionPrizeModel.get("orderno"));
			    String iconurl = (String) promotionPrizeModel.get("iconurl");
			    Map<String,String> map = new HashMap<String,String>();
			    map.put("prizeid", prizeId);
			    map.put("prizetotalnum", prizetotalnum.toString());
			    hdPrizeInfoMapper.reducePrize(map);//扣减奖品数量
				HdPromotionPrize hdPromotionPrize = new HdPromotionPrize();
				final String uuid = RuleUtil.generateUUID();
				hdPromotionPrize.setPromotionprizeid(uuid);
				hdPromotionPrize.setPrizeid(prizeId);
				hdPromotionPrize.setPrizelevel(prizelevel);
				int restNum = calPrizerestnum(listR,prizetotalnum,prizeId,promotionid);
				hdPromotionPrize.setPrizerestnum(restNum);
				hdPromotionPrize.setPrizetotalnum(prizetotalnum);
				hdPromotionPrize.setProbability(probability);
				hdPromotionPrize.setToperatorid(toperatorid);
				hdPromotionPrize.setCountryno(countryno);
				hdPromotionPrize.setModifydate(date);
				hdPromotionPrize.setModifyman(username);
				hdPromotionPrize.setBackgroundurl("");
				hdPromotionPrize.setPromotionid(promotionid);
				hdPromotionPrize.setScorestart(scorestart);
				hdPromotionPrize.setScoreend(scoreend);
				hdPromotionPrize.setOrderno(orderno);
				hdPromotionPrize.setIconurl(iconurl);
				hdPromotionPrizeMapper.insert(hdPromotionPrize);//插入关联关系
			}
	}
	
	private int calPrizerestnum(List<PromotionRelation> listR,Integer prizetotalnum,String prizeId,String promotionid){
		int rtn = 0;
		boolean hasFlag = false;
		for(int i=0;i<listR.size();i++){
			PromotionRelation promotionRelation = listR.get(i);
			String prizeIdT = promotionRelation.getPrizeid();
			String promotionidT = promotionRelation.getPromotionid();
			if(prizeId.equals(prizeIdT) && promotionid.equals(promotionidT)){
				hasFlag = true;
				rtn = promotionRelation.getPrizerestnum()+(prizetotalnum - promotionRelation.getPrizetotalnum());
				break;
			}
		}
		
		if(!hasFlag){
			rtn = prizetotalnum;
		}
		return rtn;
	}

	@Override
	public String getPrizeCombox(QxUserModel qxUserModel,String promotionid) {
		//String rtn = "[{\"bean.prizeid\":\"bbc23d93e67f440296aaaeca04e129fd\",\"prizename\":\"Koi\"},{\"bean.prizeid\":\"1\",\"prizename\":\"Dalmation\"}]";
		StringBuffer rtn = new StringBuffer("[");
		String toperatorid = qxUserModel.getBean().getToperatorid();
		String countryno = qxUserModel.getBean().getCountryno();
		Map<String,String> map = new HashMap<String,String>();
		map.put("toperatorid", toperatorid);
		map.put("countryno", countryno);
		map.put("promotionid", promotionid);
		List<HdPrizeInfo> list = hdPrizeInfoMapper.getPrizeCombox(map);
		
		for(int i=0;i<list.size();i++){
			HdPrizeInfo hdPrizeInfo= list.get(i);
			String prizeId = hdPrizeInfo.getPrizeid();
			String prizeName = hdPrizeInfo.getPrizename();
			rtn.append("{\"prizeid\":\"");
			rtn.append(prizeId);
			rtn.append("\",");
			rtn.append("\"prizename\":\"");
			rtn.append(prizeName);
			rtn.append("\"}");
			if(i<list.size()-1){
				rtn.append(",");
			}
		}
		rtn.append("]");
		return rtn.toString();
	}

	@Override
	public String getDataGrid(QxUserModel qxUserModel,String promotionid) {
//		String rtn = "{\"total\":28,\"rows\":["
//		+ "{\"bean.prizeid\":\"bbc23d93e67f440296aaaeca04e129fd\",\"prizename\":\"Koi\",\"ptotalnum\":\"20\",\"prestnum\":10,\"bean.prizelevel\":\"P\",\"bean.prizetotalnum\":5,\"bean.probability\":\"2\"},"
//		+ "{\"bean.prizeid\":\"1\",\"prizename\":\"Dalmation\",\"ptotalnum\":\"30\",\"prestnum\":15,\"bean.prizelevel\":\"q\",\"bean.prizetotalnum\":9,\"bean.probability\":\"3\"}]}";
		String toperatorid = qxUserModel.getBean().getToperatorid();
		String countryno = qxUserModel.getBean().getCountryno();
		Map<String,String> map = new HashMap<String,String>();
		map.put("toperatorid", toperatorid);
		map.put("countryno", countryno);
		map.put("promotionid", promotionid);
		int count = hdPromotionPrizeMapper.getDataGridCount(map);
		StringBuffer rtn = new StringBuffer("{\"total\":");
		rtn.append(count);
		rtn.append(",\"rows\":[");
		List<PromotionRelation> list = hdPromotionPrizeMapper.getDataGrid(map);
		for(int i=0;i<list.size();i++){
			PromotionRelation promotionRelation = list.get(i);
			String prizeId = promotionRelation.getPrizeid();
			String prizeName = promotionRelation.getHdPrizeInfo().getPrizename();
			BigDecimal ptotalnum = promotionRelation.getHdPrizeInfo().getPrizetotalnum();
			BigDecimal prestnum = promotionRelation.getHdPrizeInfo().getPrizerestnum();
			String prizelevel = promotionRelation.getPrizelevel();
			Integer prizetotalnum = promotionRelation.getPrizetotalnum();
			Integer probability = promotionRelation.getProbability();
			Integer scorestart = promotionRelation.getScorestart();
			Integer scoreend = promotionRelation.getScoreend();
			Integer orderno = promotionRelation.getOrderno();
			String iconurl = promotionRelation.getIconurl();
			rtn.append("{\"prizeid\":\"");
			rtn.append(prizeId);
			rtn.append("\",");
			rtn.append("\"prizename\":\"");
			rtn.append(prizeName);
			rtn.append("\",");
			rtn.append("\"ptotalnum\":\"");
			rtn.append(ptotalnum);
			rtn.append("\",");
			rtn.append("\"prestnum\":\"");
			rtn.append(prestnum);
			rtn.append("\",");
			rtn.append("\"prizelevel\":\"");
			rtn.append(prizelevel);
			rtn.append("\",");
			rtn.append("\"prizetotalnum\":\"");
			rtn.append(prizetotalnum);
			rtn.append("\",");
			rtn.append("\"probability\":\"");
			rtn.append(probability);
			rtn.append("\",");
			rtn.append("\"scorestart\":\"");
			rtn.append(scorestart);
			rtn.append("\",");
			rtn.append("\"scoreend\":\"");
			rtn.append(scoreend);
			rtn.append("\",");
			rtn.append("\"orderno\":\"");
			rtn.append(orderno);
			rtn.append("\",");
			rtn.append("\"iconurl\":\"");
			rtn.append(iconurl);
			rtn.append("\"}");
			if(i<list.size()-1){
				rtn.append(",");
			}
		}
		rtn.append("]}");
		return rtn.toString();
	}

}
