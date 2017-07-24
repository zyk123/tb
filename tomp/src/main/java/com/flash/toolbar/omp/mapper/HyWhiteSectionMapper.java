package com.flash.toolbar.omp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.model.HyWhiteImportBatch;
import com.flash.toolbar.omp.model.HyWhiteSection;

public interface HyWhiteSectionMapper {
    
	Integer selectCount(Map<String, Object> map);
	
	List<HyWhiteSection> selectHyWhiteSection(Map<String, Object> map);
	
	int insertHyWhiteSection(List<HyWhiteSection> list);
	
	int delHyWhiteSection(Map<String,Object> map);
	
	int delDuplicateHyWhiteSection(@Param(value="list")List<HyWhiteSection> hwList,@Param(value="hwib")HyWhiteImportBatch hwib);
}