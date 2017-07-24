package com.flash.toolbar.omp.packagemange.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.mapper.TcFlowPackageMapper;
import com.flash.toolbar.omp.mapper.TcFlowpackagetypeRelaMapper;
import com.flash.toolbar.omp.mapper.TcPackageTypeMapper;
import com.flash.toolbar.omp.model.TcFlowpackagetypeRela;
import com.flash.toolbar.omp.model.TcPackageType;
import com.flash.toolbar.omp.packagemange.bo.TcFlowPackageRelation;
import com.flash.toolbar.omp.packagemange.bo.TcPackageTypeModel;
import com.flash.toolbar.omp.packagemange.service.PackageTypeService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Transactional
@Service
public class PackageTypeServiceImpl implements PackageTypeService{
	
	@Resource
	private TcPackageTypeMapper  tcPackageTypeMapper;
	
	@Resource
	private TcFlowPackageMapper tcFlowPackageMapper;
	
	@Resource
	private TcFlowpackagetypeRelaMapper tcFlowpackagetypeRelaMapper;
	
	@Override
	public int countByCondition(TcPackageTypeModel model) throws Exception {
		return tcPackageTypeMapper.countByCondition(model);
	}
	
	@Override
	public void deleteList(String[] bListIds, QxUserModel qxUserModel)
			throws Exception {
		tcPackageTypeMapper.deleteBacth(bListIds, qxUserModel);
		tcFlowpackagetypeRelaMapper.deleteBatch(bListIds);
	}
	
	@Override
	public List<TcPackageType> getPackageTypeList(TcPackageTypeModel model)
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
		return tcPackageTypeMapper.selectByPageSelective(model);
	}
	
	
	@Override
	public List<TcFlowPackageRelation> getRelationPackageList(String packagetypeid,
			QxUserModel qxUserModel) throws Exception {
		return tcFlowPackageMapper.getRelationPackageList(packagetypeid,qxUserModel);
	}
	
	
	@Override
	public void saveRelation(String packagetypeid,List<TcFlowpackagetypeRela> list) throws Exception {
		TcFlowpackagetypeRela rela = new TcFlowpackagetypeRela();
		rela.setPackagetypeid(packagetypeid);
		tcFlowpackagetypeRelaMapper.deleteSelective(rela);
		if(list.size()>0){
			tcFlowpackagetypeRelaMapper.insertBatch(list);
		}
	}
	
	
	@Override
	public boolean modifyPackageType(TcPackageType bean) throws Exception{
		if(tcPackageTypeMapper.updateByPrimaryKeySelective(bean)>0){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean savePackageType(TcPackageType bean) throws Exception{
		bean.setOrderno(getNextOrder(bean));
		if(tcPackageTypeMapper.insert(bean)>0){
			return true;
		}
		return false;
	}
	
	
	@Override
	public void updateOrderNo(String packagetypeid, String type,
			String preorafterid, String preoraftertype,QxUserModel qxUserModel) throws Exception {
		tcPackageTypeMapper.updateOrderNo(packagetypeid, type, qxUserModel);
		tcPackageTypeMapper.updateOrderNo(preorafterid, preoraftertype, qxUserModel);
	}
	
	@Override
	public int getNextOrder(TcPackageType bean) throws Exception {
		TcPackageTypeModel model = new TcPackageTypeModel();
		TcPackageType type = new TcPackageType();
		type.setCountryno(bean.getCountryno());
		type.setToperatorid(bean.getToperatorid());
		type.setBrand(StringUtil.isNull(bean.getBrand())?"":bean.getBrand().trim());
		model.setBean(type);
		return tcPackageTypeMapper.countBySelective(model)+1;
	}
}
