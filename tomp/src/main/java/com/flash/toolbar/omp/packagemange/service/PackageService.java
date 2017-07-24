package com.flash.toolbar.omp.packagemange.service;

import java.util.List;

import com.flash.toolbar.omp.model.TcFlowPackage;
import com.flash.toolbar.omp.model.TcFlowpackageDetail;
import com.flash.toolbar.omp.packagemange.bo.TcFlowPackageModel;
import com.flash.toolbar.omp.packagemange.bo.TcFlowpackageDetailRec;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface PackageService {
	List<TcFlowPackage> getPackageList(TcFlowPackageModel model) throws Exception;
	
	List<TcFlowpackageDetailRec> getPackageDetailRecList(TcFlowpackageDetail bean) throws Exception;
	
	int countByCondition(TcFlowPackageModel model) throws Exception;
	
	void deleteList(String[] bListIds,QxUserModel qxUserModel) throws Exception;
	
	void updateStatus(String[] bListIds,String status,QxUserModel qxUserModel) throws Exception;
	
	void saveRelation(String packageid,String[] packageDetailIds,QxUserModel qxUserModel) throws Exception;
	
	boolean savePackage(TcFlowPackage bean); 
	
	boolean modifyPackage(TcFlowPackage bean);
	
	void setOrginalDetail(String packageid,QxUserModel qxUserModel) throws Exception;
}
