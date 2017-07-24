package com.flash.toolbar.omp.whiteList.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.mapper.HyWhiteImportBatchMapper;
import com.flash.toolbar.omp.mapper.HyWhiteSectionMapper;
import com.flash.toolbar.omp.model.HyWhiteImportBatch;
import com.flash.toolbar.omp.model.HyWhiteSection;
import com.flash.toolbar.omp.whiteList.service.HyWhiteSectionService;

@Service
public class HyWhiteSectionServiceImpl implements HyWhiteSectionService {
	
	@Autowired
	private HyWhiteSectionMapper mapper;
	
	@Autowired
	private HyWhiteImportBatchMapper batchMapper;

	@Override
	public Map<String, Object> selectHyWhiteSection(HyWhiteSection section,Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("section", section);
		Integer totalCount = mapper.selectCount(map);
		page.setTotalCount(totalCount);	
		if(page.getCurrentPage() > page.getTotalPage())
		{
			page.setCurrentPage(1);
		}
		map.put("page", page);
		if(0 < totalCount){
			map.put("list",mapper.selectHyWhiteSection(map));
		}
		return map;
	}

	@Override
	@Transactional(value="transactionManager",rollbackFor=java.lang.Exception.class)
	public boolean insertHyWhiteSection(List<HyWhiteSection> list,
			HyWhiteImportBatch hwib) {
		mapper.delDuplicateHyWhiteSection(list, hwib);
		int i = mapper.insertHyWhiteSection(list);
		int j = batchMapper.insertHyWhiteImportBatch(hwib);
		if(0 < i && j > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 删除指定白名单号段信息
	 */
	@Override
	@Transactional(value="transactionManager",rollbackFor=java.lang.Exception.class)
	public boolean delWhiteSection(String[] ids,String countryNo,String tOperatorId){
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		map.put("array", ids);
		map.put("countryno", countryNo);
		map.put("toperatorid", tOperatorId);
		int i = mapper.delHyWhiteSection(map);
		if(i>0){
			return true;
		}
		return false;
	}

}
