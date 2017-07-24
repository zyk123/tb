package com.flash.toolbar.omp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.model.TcFlowpackageDetail;
import com.flash.toolbar.omp.packagemange.bo.TcFlowpackageDetailModel;
import com.flash.toolbar.omp.packagemange.bo.TcFlowpackageDetailRec;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface TcFlowpackageDetailMapper {
    int deleteByPrimaryKey(String packagedetailid);

    int insert(TcFlowpackageDetail record);

    int insertSelective(TcFlowpackageDetail record);

    TcFlowpackageDetail selectByPrimaryKey(String packagedetailid);
    
    List<TcFlowpackageDetailModel> selectByPageSelective(TcFlowpackageDetailModel model);
    
    List<TcFlowpackageDetailModel> selectBySelective(TcFlowpackageDetail record);

    int updateByPrimaryKeySelective(TcFlowpackageDetail record);

    int updateByPrimaryKey(TcFlowpackageDetail record);
    
    int countByCondition(TcFlowpackageDetailModel model);
    
    int countBySelective(TcFlowpackageDetailModel model);
    
    int updateRelationBatch(@Param("packageid")String packageid,@Param("packageDetailIds")String[] packageDetailIds,@Param("qxUserModel")QxUserModel qxUserModel);
    
    int updateOrginal(@Param("packageid")String packageid,@Param("qxUserModel")QxUserModel qxUserModel);
    
    int deleteDetailList(@Param("bListIds")String[] bListIds,@Param("qxUserModel")QxUserModel qxUserModel);
    
    List<Map<String, Object>> selectParent(@Param("record")TcFlowpackageDetail record,@Param("term")String term);
    
    List<TcFlowpackageDetailRec> getFlowPackageDetailRec(TcFlowpackageDetail record);
}