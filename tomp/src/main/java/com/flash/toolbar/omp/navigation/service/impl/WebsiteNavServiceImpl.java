package com.flash.toolbar.omp.navigation.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.mapper.HyWebsiteNavigationMapper;
import com.flash.toolbar.omp.model.HyWebsiteNavigation;
import com.flash.toolbar.omp.navigation.bo.HyWebsiteNavigationModel;
import com.flash.toolbar.omp.navigation.service.WebsiteNavService;

@Transactional
@Service
public class WebsiteNavServiceImpl implements WebsiteNavService{
	@Resource
	private HyWebsiteNavigationMapper hyWebsiteNavigationMapper;
	
	@Override
	public List<HyWebsiteNavigation> getWebsiteNavList(HyWebsiteNavigationModel model) throws Exception{
		int totalRows = countByCondition(model);
		model.getPager().setTotalRowsCount(totalRows);
		model.getPager().doPage();
		int totalPageNum = model.getPager().getTotalPageNum();
		int pageIndex = model.getPageIndex();
		if(pageIndex>totalPageNum){
			model.setPageIndex(1);
			model.getPager().doPage();
		}
		return hyWebsiteNavigationMapper.selectByPageSelective(model);
	}
	
	
	@Override
	public int countByCondition(HyWebsiteNavigationModel model) throws Exception {
		return hyWebsiteNavigationMapper.countByCondition(model);
	}
	
	
	@Override
	public boolean insertIcon(HyWebsiteNavigation hyWebsiteNavigation) throws Exception{
		if(hyWebsiteNavigationMapper.insert(hyWebsiteNavigation)>0){
			return true;
		}
		return false;
	}
	
	@Override
	public HyWebsiteNavigation selectIconByPrimaryKey(
			HyWebsiteNavigation hyWebsiteNavigation) throws Exception{
		return hyWebsiteNavigationMapper.selectIconByPrimaryKey(hyWebsiteNavigation);
	}
	
	
	@Override
	public void deleteWebsiteNav(String id,int orderCount,int orderNo) throws Exception{
		hyWebsiteNavigationMapper.deleteByPrimaryKey(id);
		updateGameNavOrder(orderCount,orderNo);
	}
	
	private void updateGameNavOrder(int starOrderNo,int endOrderNo) throws Exception{
		int startIndex = starOrderNo;
		int endIndex = endOrderNo;
		String operation = "";
		if( StringUtil.isNull(starOrderNo) || StringUtil.isNull(endOrderNo)){
			return;
		}
		if(endOrderNo>starOrderNo){
			startIndex = starOrderNo;
			endIndex = endOrderNo-1;
			operation = "+1";
		}
		if(starOrderNo>endOrderNo){
			startIndex = endOrderNo+1;
			endIndex = starOrderNo;
			operation = "-1";
		}
		hyWebsiteNavigationMapper.updateOrderSort(operation, startIndex, endIndex);
	}	
	
	
	@Override
	public boolean modifyWebsiteNav(HyWebsiteNavigation hyWebsiteNavigation)
			throws Exception {
		if(hyWebsiteNavigationMapper.updateByPrimaryKeySelective(hyWebsiteNavigation)>0){
			return true;
		}
		return false;
	}
}
