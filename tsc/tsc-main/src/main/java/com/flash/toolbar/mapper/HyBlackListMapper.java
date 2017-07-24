package com.flash.toolbar.mapper;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.model.HyBlackList;

public interface HyBlackListMapper {
    int deleteByPrimaryKey(String blistid);

    int insert(HyBlackList record);

    int insertSelective(HyBlackList record);

    HyBlackList selectByPrimaryKey(String blistid);
    
    List<HyBlackList> selectByMobileNo(String mobileNo);
    
    int countAllByMobileNo(String mobileNo);

    int updateByPrimaryKeySelective(HyBlackList record);

    int updateByPrimaryKey(HyBlackList record);
    
    List<HyBlackList> selectByToperator(String countryno, String toperatorid);
    
    int countByToperator(Map map);
    
    List<String> selectMobileNoPageByToperator(Map map);
}