package com.flash.toolbar.omp.packagesub.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flash.toolbar.omp.common.util.CodeUtil;
import com.flash.toolbar.omp.mapper.TcFlowSubMapper;
import com.flash.toolbar.omp.model.TcFlowSub;
import com.flash.toolbar.omp.packagesub.bo.TcFlowSubModel;
import com.flash.toolbar.omp.packagesub.service.PackageSubService;

/**
 * 套餐订单管理业务实现类
 * @author zhulin
 */
@Service
public class PackageSubServiceImpl implements PackageSubService{
	
	@Resource
	private TcFlowSubMapper tcFlowSubMapper;

	@Override
	public List<TcFlowSub> getPackageSubInfo(TcFlowSubModel tcFlowSubModel) {
		
		int totalRows = countPackageSubInfo(tcFlowSubModel);
		tcFlowSubModel.getPager().setTotalRowsCount(totalRows);
		tcFlowSubModel.getPager().doPage();
		
		List<TcFlowSub> list = tcFlowSubMapper.selectByPageSelective(tcFlowSubModel);
		
		//循环码值转文字
		for(TcFlowSub tfs : list){
			//订购状态码值 转 文字
			tfs.setStatus( CodeUtil.parseCode(CodeUtil.FLOW_SUB_STATUS, tfs.getStatus()) );
			
		}
		
		return list;
	}

	private int countPackageSubInfo(TcFlowSubModel tcFlowSubModel) {
		return tcFlowSubMapper.countByPageSelective(tcFlowSubModel);
	}

}
