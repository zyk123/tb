package com.flash.toolbar.omp.common.log;

import java.util.Date;

import org.apache.log4j.Logger;


public class AccessLogger {
	private static Logger logger = Logger.getLogger(AccessLogger.class);
	
	
	 /**
	 * 业务日志
	 * 
	 * @param requestSerial	业务唯一标示
	 * @param component	部件名称
	 * @param interfaceName	接口名称
	 * @param startFlag	开始结束标记
	 * @param opResult	业务执行结果
	 * @param opTime	业务执行时间
	 */
	public static void LoggerInfo(String userNo, String userName,
			String pageName, String opTime) {
		StringBuilder info = new StringBuilder();
		info.append(userNo == null ? "" : userNo);
		info.append("|");
		info.append(userName == null ? "" : userName);
		info.append("|");
		info.append(pageName == null ? "" : pageName);
		info.append("|");
		info.append(opTime == null ? "" : opTime);
		logger.info(info.toString());
	}
}
