package com.flash.toolbar.omp.packagemange.service;

import java.util.List;

import com.flash.toolbar.omp.model.TcFlowpackagetypeRela;
import com.flash.toolbar.omp.model.TcPackageType;
import com.flash.toolbar.omp.packagemange.bo.TcFlowPackageRelation;
import com.flash.toolbar.omp.packagemange.bo.TcPackageTypeModel;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface PackageTypeService{
	List<TcPackageType> getPackageTypeList(TcPackageTypeModel model) throws Exception;
	
	List<TcFlowPackageRelation> getRelationPackageList(String packagetypeid,QxUserModel qxUserModel) throws Exception;
	
	void saveRelation(String packagetypeid,List<TcFlowpackagetypeRela> list) throws Exception;
	
	int countByCondition(TcPackageTypeModel model) throws Exception;
	
	void deleteList(String[] bListIds,QxUserModel qxUserModel) throws Exception;
	
	boolean savePackageType(TcPackageType bean) throws Exception;
	
	boolean modifyPackageType(TcPackageType bean) throws Exception;
	
	void updateOrderNo(String packagetypeid, String type,String preorafterid,String preoraftertype,QxUserModel qxUserModel) throws Exception;
	
	int getNextOrder(TcPackageType bean) throws Exception;
}
