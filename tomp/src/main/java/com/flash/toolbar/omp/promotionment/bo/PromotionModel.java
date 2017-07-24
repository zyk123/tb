package com.flash.toolbar.omp.promotionment.bo;

import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.model.HdPromotionInfo;

/**
 * 包含实体bean和业务需要字段等
 * @author ocean
 *
 */
public class PromotionModel extends BaseModel<HdPromotionInfo>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PromotionModel(){
		bean =  new HdPromotionInfo();
	}
}
