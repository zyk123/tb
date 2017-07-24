package com.flash.toolbar.common.util;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 所有的业务日志都从这里面输出
 * 
 * 项目名称：VRSUDP 类名称：TrackLogger 类描述： 创建人：alex 创建时间：2012-3-22 下午07:50:28 修改人：alex
 * 修改时间：2012-3-22 下午07:50:28 修改备注：
 * 
 * @version
 * 
 */
public class BusinessLogger {

	private static Logger logger = Logger.getLogger(BusinessLogger.class);

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
	public static void LoggerInfo(String requestSerial, String component,
			String interfaceName, boolean startFlag, String opResult, String opTime) {
		StringBuilder info = new StringBuilder();
		info.append(requestSerial == null ? "" : requestSerial);
		info.append("|");
		info.append(component == null ? "" : component);
		info.append("|");
		info.append(interfaceName == null ? "" : interfaceName);
		info.append("|");
		if (startFlag) {
			info.append("Start|");
			info.append(new Date().getTime());
			info.append("|");
			info.append(opResult == null ? "" : opResult);
			info.append("|");
		} else {
			info.append("End|");
			info.append(new Date().getTime());
			info.append("|");
			info.append(opResult == null ? "" : opResult);
			info.append("|");
		}
		info.append(opTime == null ? "" : opTime);
		logger.info(info.toString());
	}

	public static void info(Object message) {
		logger.info(message);
	}

	public static void info(Object message, Throwable t) {
		logger.info(message, t);
	}

	public static void debug(Object message) {
		logger.debug(message);
	}

	public static void debug(Object message, Throwable t) {
		logger.debug(message, t);
	}

	public static void warn(Object message) {
		logger.warn(message);
	}

	public static void warn(Object message, Throwable t) {
		logger.warn(message, t);
	}

	public static void error(Object message) {
		logger.error(message);
	}

	public static void error(Object message, Throwable t) {
		logger.error(message, t);
	}

	public static void trace(Object message) {
		logger.trace(message);
	}

	public void trace(Object message, Throwable t) {
		logger.trace(message, t);
	}

	public static boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

}