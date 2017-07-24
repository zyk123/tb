package com.flash.toolbar.mapper;

import com.flash.toolbar.model.RpPackageSubReport;

public interface RpPackageSubReportMapper {
    int deleteByPrimaryKey(String psid);

    int insert(RpPackageSubReport record);

    int insertSelective(RpPackageSubReport record);

    RpPackageSubReport selectByPrimaryKey(String psid);

    int updateByPrimaryKeySelective(RpPackageSubReport record);

    int updateByPrimaryKey(RpPackageSubReport record);
}