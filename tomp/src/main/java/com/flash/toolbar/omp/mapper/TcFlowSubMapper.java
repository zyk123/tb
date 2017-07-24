package com.flash.toolbar.omp.mapper;

import java.util.List;

import com.flash.toolbar.omp.model.TcFlowSub;
import com.flash.toolbar.omp.packagesub.bo.TcFlowSubModel;

public interface TcFlowSubMapper {
    int deleteByPrimaryKey(String flowsubid);

    int insert(TcFlowSub record);

    int insertSelective(TcFlowSub record);

    TcFlowSub selectByPrimaryKey(String flowsubid);
    
    int updateByPrimaryKeySelective(TcFlowSub record);

    int updateByPrimaryKey(TcFlowSub record);

    List<TcFlowSub> selectByPageSelective(TcFlowSubModel tcFlowSubModel);
    
	int countByPageSelective(TcFlowSubModel tcFlowSubModel);
}