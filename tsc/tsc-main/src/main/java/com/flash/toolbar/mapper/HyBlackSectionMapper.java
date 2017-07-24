package com.flash.toolbar.mapper;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.model.HyBlackSection;

public interface HyBlackSectionMapper {
    int deleteByPrimaryKey(String bsectionid);

    int insert(HyBlackSection record);

    int insertSelective(HyBlackSection record);

    HyBlackSection selectByPrimaryKey(String bsectionid);
    
    List<HyBlackSection> selectByMobileSeq(String mobileSeq);
    
    int updateByPrimaryKeySelective(HyBlackSection record);

    int updateByPrimaryKey(HyBlackSection record);
    
    List<HyBlackSection> selectByToperator(Map map);
}