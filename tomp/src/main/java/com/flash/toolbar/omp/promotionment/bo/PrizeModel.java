package com.flash.toolbar.omp.promotionment.bo;

import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.model.HdPrizeInfo;

/**
 * 包含实体bean和业务需要字段等
 * @author ocean
 *
 */
public class PrizeModel extends BaseModel<HdPrizeInfo>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PrizeModel(){
		bean =  new HdPrizeInfo();
	}
}
