package com.flash.toolbar.mapper;

import com.flash.toolbar.model.IdGenerator;

public interface IdGeneratorMapper {

    /**
     * Description:根据ColumnName更新最大ID号 
     * @param idGenerator
     */
    void updateMaxValueByFieldName(IdGenerator idGenerator);

    /**
     * Description:根据字段名查询
     * @param idGenerator
     * @return
     */
    IdGenerator getSequenceByFieldName(IdGenerator idGenerator);

    /**
     * Description:插入一条序列数据记录
     * @param idGenerator
     */
    void insertSequenceByFieldName(IdGenerator idGenerator);

}
