package com.flash.toolbar.service;

public interface LotteryFileService {
	void readPageLoadCsv();
	
	void savePromtionCsv(String requestSerial, String mobileNo, Boolean lucky, String promotionName, String prizeName);
	
	void readClickEventCsv();
}
