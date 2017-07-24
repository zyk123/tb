package com.flash.toolbar.mapper;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.model.HyWhiteSection;

public interface HyWhiteSectionMapper {
    int deleteByPrimaryKey(String wsectionid);

    int insert(HyWhiteSection record);

    int insertSelective(HyWhiteSection record);

    HyWhiteSection selectByPrimaryKey(String wsectionid);
    
    List<HyWhiteSection> selectByMobileNo(String mobileSeq);
    
    int countByMobileNo(String mobileSeq);

    int updateByPrimaryKeySelective(HyWhiteSection record);

    int updateByPrimaryKey(HyWhiteSection record);
    
    List<HyWhiteSection> selectByToperator(Map map);
}