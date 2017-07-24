package com.flash.toolbar.mapper;

import com.flash.toolbar.model.CzReloadOrder;

import java.util.List;

public interface CzReloadOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(CzReloadOrder record);

    int insertSelective(CzReloadOrder record);

    CzReloadOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CzReloadOrder record);

    int updateByPrimaryKey(CzReloadOrder record);

    List<CzReloadOrder> select4QueryStateReload(String dateStr );
}