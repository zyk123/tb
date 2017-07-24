package com.flash.toolbar.omp.navigation.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.mapper.HyGameNavigationMapper;
import com.flash.toolbar.omp.model.HyGameNavigation;
import com.flash.toolbar.omp.navigation.bo.HyGameNavigationModel;
import com.flash.toolbar.omp.navigation.service.GameNavService;

@Transactional
@Service
public class GameNavServiceImpl implements GameNavService{

	@Resource
	private HyGameNavigationMapper hyGameNavigationMapper;
	
	@Override
	public List<HyGameNavigation> getGameNavList(HyGameNavigationModel model) throws Exception{
		int totalRows = countByCondition(model);
		model.getPager().setTotalRowsCount(totalRows);
		model.getPager().doPage();
		int totalPageNum = model.getPager().getTotalPageNum();
		int pageIndex = model.getPageIndex();
		if(pageIndex>totalPageNum){
			model.setPageIndex(1);
			model.getPager().doPage();
		}
		return hyGameNavigationMapper.selectByPageSelective(model);
	}
	
	
	@Override
	public int countByCondition(HyGameNavigationModel model) throws Exception {
		return hyGameNavigationMapper.countByCondition(model);
	}
	
	
	@Override
	public boolean insertIcon(HyGameNavigation hyGameNavigation) throws Exception{
		if(hyGameNavigationMapper.insert(hyGameNavigation)>0){
			return true;
		}
		return false;
	}
	
	@Override
	public HyGameNavigation selectIconByPrimaryKey(
			HyGameNavigation hyGameNavigation) throws Exception{
		return hyGameNavigationMapper.selectIconByPrimaryKey(hyGameNavigation);
	}
	
	
	@Override
	public void deleteGameNav(String id,int orderCount,int orderNo) throws Exception{
		hyGameNavigationMapper.deleteByPrimaryKey(id);
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
		hyGameNavigationMapper.updateOrderSort(operation, startIndex, endIndex);
	}	
	
	
	@Override
	public boolean modifyGameNav(HyGameNavigation hyGameNavigation)
			throws Exception {
		if(hyGameNavigationMapper.updateByPrimaryKeySelective(hyGameNavigation)>0){
			return true;
		}
		return false;
	}
}
