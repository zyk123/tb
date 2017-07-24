package com.flash.toolbar.mapper;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.model.HyWhiteList;

public interface HyWhiteListMapper {
    int deleteByPrimaryKey(String wlistid);

    int insert(HyWhiteList record);

    int insertSelective(HyWhiteList record);

    HyWhiteList selectByPrimaryKey(String wlistid);
    
    List<HyWhiteList> selectByMobileNo(String mobileNo);
    
    int countAllByMobileNo(String mobileNo);
    
    int updateByPrimaryKeySelective(HyWhiteList record);

    int updateByPrimaryKey(HyWhiteList record);
    
    int countByToperator(Map map);
    
    List<String> selectMobileNoPageByToperator(Map map);
}