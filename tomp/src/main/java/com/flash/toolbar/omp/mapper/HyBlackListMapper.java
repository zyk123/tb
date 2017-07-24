package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.blacklist.bo.HyBlackListModel;
import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.model.HyBlackList;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface HyBlackListMapper {
	
    HyBlackList selectByPrimaryKey(String bListId);
    
    List<HyBlackListModel> selectByPageSelective(HyBlackListModel blackListModel);
    
    int insert(HyBlackList record);

    int insertSelective(HyBlackList record);    

    int insertBlackListBatch(List<HyBlackList> list);
    
    int deleteByPrimaryKey(String bListId);
    
    int deleteBlackListBatch(@Param("bListIds")String[] bListIds,@Param("qxUserModel")QxUserModel qxUserModel);
    
    int deleteExistMobileNo(@Param("bListIds")String[] bListIds,@Param("qxUserModel")QxUserModel qxUserModel);
    
    int countByCondition(HyBlackListModel model);
    
    int selectCountByMobileno(@Param("mobileno")String mobileno);
}
