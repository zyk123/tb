package com.flash.toolbar.facade;

import com.flash.toolbar.common.bean.PageLoadBean;

public interface LotteryFileManager {
	/**
	 * 记录用户访问页面事件
	 * @param pageLoadBean
	 * @return
	 */
	String savePageLoad(PageLoadBean pageLoadBean,String requestSerial);

	/**
	 * 手工执行刷新缓存
	 * @return
	 */
	String manualDoStaticCache();
	
	/**
	 * 手工执行读取页面话单文件
	 * @return
	 */
	String manualReadPageCsv();
	
	/**
	 * 手工执行读取点击话单文件
	 * @return
	 */
	String manualReadClickEventCsv();
	
	/**
	 * 记录用户点击页面按钮事件
	 * @param bean
	 * @param requestSerial
	 * @return
	 */
	String saveClickEventCsv(PageLoadBean bean,String requestSerial);
}
