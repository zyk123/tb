package com.flash.toolbar.omp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.HdPrizeInfo;
import com.flash.toolbar.omp.promotionment.bo.PrizeModel;

public interface HdPrizeInfoMapper {
    int deleteByPrimaryKey(String prizeid);

    int insert(HdPrizeInfo record);

    int insertSelective(HdPrizeInfo record);

    HdPrizeInfo selectByPrimaryKey(String prizeid);

    int updateByPrimaryKeySelective(HdPrizeInfo record);

    int updateByPrimaryKey(HdPrizeInfo record);
    
    int countByCondition(PrizeModel model);
    
    int countPrizeInPromotion(HdPrizeInfo record);
    
    List<PrizeModel> selectByPageSelective(PrizeModel prizeModel);

	List<PrizeModel> selectByPage(@Param(value="prizeModel")PrizeModel prizeModel,@Param(value="pager")Page page);

	void reducePrize(Map<String, String> map);
	
	List<HdPrizeInfo> getPrizeCombox(Map<String, String> map);
}