package com.flash.toolbar.omp.promotionment.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.mapper.HdPrizeInfoMapper;
import com.flash.toolbar.omp.mapper.HdPromotionPrizeMapper;
import com.flash.toolbar.omp.model.HdPrizeInfo;
import com.flash.toolbar.omp.promotionment.bo.PrizeModel;
import com.flash.toolbar.omp.promotionment.service.PrizeListService;

@Transactional
@Service
public class PrizeListServiceImpl implements PrizeListService {
	@Resource
	private HdPrizeInfoMapper hdPrizeInfoMapper;
	
	@Resource
	private HdPromotionPrizeMapper hdPromotionPrizeMapper;

	@Override
	public List<PrizeModel> getPrizeListInfo(PrizeModel prizeModel)
			throws Exception {
		int totalRows = countByCondition(prizeModel);
		prizeModel.getPager().setTotalRowsCount(totalRows);
		prizeModel.getPager().doPage();
		int totalPageNum = prizeModel.getPager().getTotalPageNum();
		int pageIndex = prizeModel.getPageIndex();
		if (pageIndex > totalPageNum) {
			prizeModel.setPageIndex(1);
			prizeModel.getPager().doPage();
		}
		List<PrizeModel> list = hdPrizeInfoMapper
				.selectByPageSelective(prizeModel);
		return list;
	}
	
	@Override
	public List<PrizeModel> selectPrizeListByPage(PrizeModel prizeModel,Page page) throws Exception {
		int totalRows = countByCondition(prizeModel);
		page.setTotalCount(totalRows);
		if(page.getCurrentPage() > page.getTotalPage()){
			page.setCurrentPage(1);
		}
		if(0 < totalRows){
			return hdPrizeInfoMapper.selectByPage(prizeModel, page);
		}
		return null;
	}

	@Override
	public int countByCondition(PrizeModel prizeModel) throws Exception {
		return hdPrizeInfoMapper.countByCondition(prizeModel);
	}

	@Override
	public boolean insertPrize(PrizeModel prizeModel) {
		prizeModel.getBean().setPrizerestnum(prizeModel.getBean().getPrizetotalnum());
		if (hdPrizeInfoMapper.insert(prizeModel.getBean()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public HdPrizeInfo selectByPrimaryKey(HdPrizeInfo hdPrizeInfo) {
		return hdPrizeInfoMapper.selectByPrimaryKey(hdPrizeInfo.getPrizeid());
	}

	@Override
	public String updatePrize(PrizeModel prizeModel) {
		BigDecimal prizetotalnum = prizeModel.getBean().getPrizetotalnum();
		String prizeid = prizeModel.getBean().getPrizeid();
		String countryno = prizeModel.getBean().getCountryno();
		String toperatorid = prizeModel.getBean().getToperatorid();
		
		Map<String, String> map = new HashMap<String,String>();
		map.put("prizeid", prizeid);
		map.put("countryno", countryno);
		map.put("toperatorid", toperatorid);
		
		Integer hasAsignNumT = hdPromotionPrizeMapper.calPrizeHasAsignNum(map);
		int hasAsignNum = 0;
		if(null!=hasAsignNumT){
			hasAsignNum = hasAsignNumT.intValue();
		}
		if(prizetotalnum.intValue() < hasAsignNum){
			return "2";
		}
		if(hdPrizeInfoMapper.updateByPrimaryKeySelective(prizeModel.getBean()) > 0){
			return "0";
		}else{
			return "1";
		}
	}

	@Override
	public boolean deletePrize(HdPrizeInfo hdPrizeInfo) {
		int count = hdPrizeInfoMapper.countPrizeInPromotion(hdPrizeInfo);
		if(count > 0){
			return false;
		}else{
		    if(hdPrizeInfoMapper.deleteByPrimaryKey(hdPrizeInfo.getPrizeid()) > 0){
			    return true;
		    }
		}
		return false;
	}

}
