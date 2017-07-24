package com.flash.toolbar.omp.remind.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.mapper.SysRemindConfigMapper;
import com.flash.toolbar.omp.model.SysRemindConfig;
import com.flash.toolbar.omp.remind.bo.SysRemindConfigModel;
import com.flash.toolbar.omp.user.bo.QxUserModel;

@Transactional
@Service
public class RemindServiceImpl implements RemindService{
	@Resource
	private SysRemindConfigMapper sysRemindConfigMapper;
	
	@Override
	public List<SysRemindConfig> getRemindListInfo(
			SysRemindConfigModel sysRemindConfigModel) throws Exception {
		int totalRows = countByCondition(sysRemindConfigModel);
		sysRemindConfigModel.getPager().setTotalRowsCount(totalRows);
		sysRemindConfigModel.getPager().doPage();
		int totalPageNum = sysRemindConfigModel.getPager().getTotalPageNum();
		int pageIndex = sysRemindConfigModel.getPageIndex();
		if(pageIndex>totalPageNum){
			sysRemindConfigModel.setPageIndex(1);
			sysRemindConfigModel.getPager().doPage();
		}
		return sysRemindConfigMapper.selectBySelective(sysRemindConfigModel);
	}
	
	@Override
	public int countByCondition(SysRemindConfigModel sysRemindConfigModel) throws Exception{
		return sysRemindConfigMapper.countByCondition(sysRemindConfigModel);
	}
	
	@Override
	public void batchOnOff(String[] batchIds, String type,
			QxUserModel qxUserModel) throws Exception {
		sysRemindConfigMapper.updateRemindListBatch(batchIds,type,qxUserModel);
		
	}
	
	@Override
	public void batchSetTimeInterval(String[] batchIds, String remindVal,
			QxUserModel qxUserModel) throws Exception {
		sysRemindConfigMapper.updateTimeIntervalBatch(batchIds,remindVal,qxUserModel);
	}
	
	@Override
	public int modify(SysRemindConfig sysRemindConfig) throws Exception {
		return sysRemindConfigMapper.updateByPrimaryKeySelective(sysRemindConfig);
	}
	
	@Override
	public int add(SysRemindConfig sysRemindConfig) throws Exception {
		return sysRemindConfigMapper.insert(sysRemindConfig);
	}
	
	@Override
	public void deleteRemindList(String[] bListIds, QxUserModel qxUserModel)
			throws Exception {
		sysRemindConfigMapper.deleteRemindList(bListIds,qxUserModel);
	}
}
