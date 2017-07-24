package com.flash.toolbar.omp.whiteList.service;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.HyWhiteImportBatch;
import com.flash.toolbar.omp.model.HyWhiteSection;

public interface HyWhiteSectionService {
	
	Map<String, Object> selectHyWhiteSection(HyWhiteSection section,Page page);
	
	boolean insertHyWhiteSection(List<HyWhiteSection> list,HyWhiteImportBatch hwib);
	
	boolean delWhiteSection(String[] ids,String countryNo,String tOperatorId);
}
