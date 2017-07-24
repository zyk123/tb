package com.flash.toolbar.omp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.model.HyWhiteImportBatch;
import com.flash.toolbar.omp.model.HyWhiteList;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface HyWhiteListMapper {
	
	int selectCount(Map<String, Object> map);
	
	List<HyWhiteList> selectHyWhiteList(Map<String, Object> map);
	
	int insertHyWhiteList(List<HyWhiteList> hwList); 
	
	int delHyWhiteList(Map<String,Object> map);
	
	int delDuplicateHyWhiteList(@Param(value="list")List<HyWhiteList> hwList,@Param(value="hwib")HyWhiteImportBatch hwib);
	
	int insert(HyWhiteList record);
	
	int insertBatch(List<HyWhiteList> list);
	
	int deleteExistMobileNo(@Param("bListIds")String[] bListIds,@Param("qxUserModel")QxUserModel qxUserModel);
	
	int selectCountByMobileno(@Param("mobileno")String mobileno);
}