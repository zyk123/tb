package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.blacklist.bo.HyBlackListModel;
import com.flash.toolbar.omp.model.SysRemindConfig;
import com.flash.toolbar.omp.remind.bo.SysRemindConfigModel;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface SysRemindConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRemindConfig record);

    int insertSelective(SysRemindConfig record);
    
    int countByCondition(SysRemindConfigModel sysRemindConfigModel);

    SysRemindConfig selectByPrimaryKey(String id);
    
    List<SysRemindConfig> selectBySelective(SysRemindConfigModel sysRemindConfigModel);
    
    int updateRemindListBatch(@Param("batchIds")String[] batchIds,@Param("type")String type,@Param("qxUserModel")QxUserModel qxUserModel);
    
    int updateTimeIntervalBatch(@Param("batchIds")String[] batchIds,@Param("remindVal")String remindVal,@Param("qxUserModel")QxUserModel qxUserModel);

    int updateByPrimaryKeySelective(SysRemindConfig record);

    int updateByPrimaryKey(SysRemindConfig record);
    
    void deleteRemindList(@Param("bListIds")String[] bListIds,@Param("qxUserModel")QxUserModel qxUserModel);
}
