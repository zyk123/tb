package com.flash.toolbar.omp.packagemange.service;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.omp.model.TcFlowpackageDetail;
import com.flash.toolbar.omp.packagemange.bo.TcFlowpackageDetailModel;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface PackageDetailService {
	List<TcFlowpackageDetailModel> getPackageDetailList(TcFlowpackageDetailModel model) throws Exception;
	
	int countByCondition(TcFlowpackageDetailModel model) throws Exception;
	
	int countBySelective(TcFlowpackageDetailModel model) throws Exception;
	
	void deleteDetailList(String[] bListIds,QxUserModel qxUserModel) throws Exception;
	
	List<Map<String, Object>> getParentList(TcFlowpackageDetail bean,String term) throws Exception;
	
	boolean saveDetail(TcFlowpackageDetail bean) throws Exception;
	
	boolean modifyDetail(TcFlowpackageDetail bean) throws Exception;
	
}
