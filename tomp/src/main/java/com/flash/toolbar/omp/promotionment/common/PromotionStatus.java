package com.flash.toolbar.omp.promotionment.common;

/**
 * 活动
 * @author ocean
 *
 */
public interface PromotionStatus {

	/**
	 * 待发布
	 */
    public static final int statusInit=0;
	
    /**
     * 已发布
     */
	public static final int statusShift=1;
	
	/**
	 * 结束
	 */
	public static final int statusFinish=2;
}
