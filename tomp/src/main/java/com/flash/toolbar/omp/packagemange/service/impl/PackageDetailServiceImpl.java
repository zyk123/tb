package com.flash.toolbar.omp.packagemange.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.mapper.TcFlowpackageDetailMapper;
import com.flash.toolbar.omp.model.TcFlowpackageDetail;
import com.flash.toolbar.omp.packagemange.bo.TcFlowpackageDetailModel;
import com.flash.toolbar.omp.packagemange.service.PackageDetailService;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Transactional
@Service
public class PackageDetailServiceImpl implements PackageDetailService{
	
	@Resource
	private TcFlowpackageDetailMapper tcFlowpackageDetailMapper;
	
	@Override
	public int countByCondition(TcFlowpackageDetailModel model) throws Exception {
		return tcFlowpackageDetailMapper.countByCondition(model);
	}
	
	@Override
	public List<TcFlowpackageDetailModel> getPackageDetailList(TcFlowpackageDetailModel model) throws Exception {
		int totalRows = countByCondition(model);
		model.getPager().setTotalRowsCount(totalRows);
		model.getPager().doPage();
		int totalPageNum = model.getPager().getTotalPageNum();
		int pageIndex = model.getPageIndex();
		if(pageIndex>totalPageNum){
			model.setPageIndex(1);
			model.getPager().doPage();
		}
		return tcFlowpackageDetailMapper.selectByPageSelective(model);
	}
	
	
	@Override
	public void deleteDetailList(String[] bListIds, QxUserModel qxUserModel)
			throws Exception {
		tcFlowpackageDetailMapper.deleteDetailList(bListIds,qxUserModel);
	}
	
	
	@Override
	public List<Map<String, Object>> getParentList(
			TcFlowpackageDetail bean,String term) throws Exception {
		return tcFlowpackageDetailMapper.selectParent(bean,term);
	}
	
	@Override
	public boolean modifyDetail(TcFlowpackageDetail bean) throws Exception{
		if(tcFlowpackageDetailMapper.updateByPrimaryKeySelective(bean)>0){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean saveDetail(TcFlowpackageDetail bean) throws Exception {
		TcFlowpackageDetailModel model = new TcFlowpackageDetailModel();
		TcFlowpackageDetail detail = new TcFlowpackageDetail();
		detail.setCountryno(bean.getCountryno());
		detail.setToperatorid(bean.getToperatorid());
		detail.setParentid(bean.getParentid());
		model.setBean(detail);
		bean.setOrderno(new BigDecimal(countBySelective(model)+1));
		if( tcFlowpackageDetailMapper.insert(bean)>0){
			return true;
		}
		return false;
	}
	
	
	@Override
	public int countBySelective(TcFlowpackageDetailModel model)
			throws Exception {
		return tcFlowpackageDetailMapper.countBySelective(model);
	}
} 
