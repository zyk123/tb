package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.HdPromotionSort;

public interface HdPromotionSortMapper {
    int deleteByPrimaryKey(String sortid);

    int insert(HdPromotionSort record);

    int insertSelective(HdPromotionSort record);

    HdPromotionSort selectByPrimaryKey(String sortid);

    int updateByPrimaryKeySelective(HdPromotionSort record);

    int updateByPrimaryKey(HdPromotionSort record);
}