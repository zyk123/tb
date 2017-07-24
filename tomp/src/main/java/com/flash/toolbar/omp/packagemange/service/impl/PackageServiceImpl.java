package com.flash.toolbar.omp.packagemange.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.mapper.TcFlowPackageMapper;
import com.flash.toolbar.omp.mapper.TcFlowpackageDetailMapper;
import com.flash.toolbar.omp.model.TcFlowPackage;
import com.flash.toolbar.omp.model.TcFlowpackageDetail;
import com.flash.toolbar.omp.packagemange.bo.TcFlowPackageModel;
import com.flash.toolbar.omp.packagemange.bo.TcFlowpackageDetailModel;
import com.flash.toolbar.omp.packagemange.bo.TcFlowpackageDetailRec;
import com.flash.toolbar.omp.packagemange.service.PackageService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Transactional
@Service
public class PackageServiceImpl implements PackageService{
	
	@Resource
	private TcFlowPackageMapper tcFlowPackageMapper;
	
	@Resource
	private TcFlowpackageDetailMapper tcFlowpackageDetailMapper;
	
	@Override
	public int countByCondition(TcFlowPackageModel model) throws Exception {
		return tcFlowPackageMapper.countByCondition(model);
	}
	
	@Override
	public List<TcFlowPackage> getPackageList(TcFlowPackageModel model)
			throws Exception {
		int totalRows = countByCondition(model);
		model.getPager().setTotalRowsCount(totalRows);
		model.getPager().doPage();
		int totalPageNum = model.getPager().getTotalPageNum();
		int pageIndex = model.getPageIndex();
		if(pageIndex>totalPageNum){
			model.setPageIndex(1);
			model.getPager().doPage();
		}
		return tcFlowPackageMapper.selectByPageSelective(model);
	}
	
	@Override
	public List<TcFlowpackageDetailRec> getPackageDetailRecList(
			TcFlowpackageDetail bean) throws Exception {
		return tcFlowpackageDetailMapper.getFlowPackageDetailRec(bean);
	}
	
	@Override
	public void deleteList(String[] bListIds, QxUserModel qxUserModel)
			throws Exception {
		tcFlowPackageMapper.batchUpdatePackageDel(bListIds,qxUserModel);
	}
	
	
	@Override
	public void updateStatus(String[] bListIds, String status,
			QxUserModel qxUserModel) throws Exception {
		tcFlowPackageMapper.batchUpdateStatus(bListIds,status,qxUserModel);
	}
	
	
	@Override
	public void saveRelation(String packageid, String[] packageDetailIds,
			QxUserModel qxUserModel) throws Exception {
		setOrginalDetail(packageid,qxUserModel);
		tcFlowpackageDetailMapper.updateRelationBatch(packageid, packageDetailIds, qxUserModel);
	}
	
	
	@Override
	public boolean modifyPackage(TcFlowPackage bean) {
		if(tcFlowPackageMapper.updateByPrimaryKeySelective(bean)>0){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean savePackage(TcFlowPackage bean) {
		if(tcFlowPackageMapper.insert(bean)>0){
			return true;
		}
		return false;
	}
	
	@Override
	public void setOrginalDetail(String packageid, QxUserModel qxUserModel)
			throws Exception {
		tcFlowpackageDetailMapper.updateOrginal(packageid, qxUserModel);
	}
	
}
