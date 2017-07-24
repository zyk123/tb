package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.blacksegmentlist.bo.HyBlackSectionModel;
import com.flash.toolbar.omp.model.HyBlackSection;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface HyBlackSectionMapper {

    HyBlackSection selectByPrimaryKey(String wSectionId);
    
    List<HyBlackSectionModel> selectByPageSelective(HyBlackSectionModel blackSectionListModel);
    
    int insert(HyBlackSection record);

    int insertSelective(HyBlackSection record); 
    
    int insertBlackSectionListBatch(List<HyBlackSection> list);    
    
    int deleteByPrimaryKey(String wSectionId);
    
    int deleteBlackSectionListBatch(@Param("bSectionListIds")String[] bSectionListIds,@Param("qxUserModel")QxUserModel qxUserModel);
    
    int deleteExistSection(List<HyBlackSection> list);

    int countByCondition(HyBlackSectionModel model);

}
