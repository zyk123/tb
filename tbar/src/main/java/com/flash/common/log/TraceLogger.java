package com.flash.common.log;

import org.apache.log4j.Logger;

/**
 * 所有的跟踪日志都从这里面输出
 * 
 * 项目名称：VRSUDP 类名称：TrackLogger 类描述： 创建人：alex 创建时间：2012-3-22 下午07:50:28 修改人：alex
 * 修改时间：2012-3-22 下午07:50:28 修改备注：
 * 
 * @version
 * 
 */
public class TraceLogger {

	private static Logger logger = Logger.getLogger(TraceLogger.class);

	public static void LoggerInfo(String requestSerial, String usrid,
			String interfaceName, String message, String content) {
		StringBuilder info = new StringBuilder();
		info.append(requestSerial);
		info.append("|");
		info.append(usrid);
		info.append("|");
		info.append(interfaceName);
		info.append("|");
		info.append("---------------------" + (message == null ? "" : message)
				+ "---------------------");
		info.append("\n\t");
		info.append(content);
		logger.info(info.toString());
	}

	public static void LoggerDebug(String requestSerial, String usrid,
			String interfaceName, String message, String content) {
		StringBuilder info = new StringBuilder();
		info.append(requestSerial);
		info.append("|");
		info.append(usrid);
		info.append("|");
		info.append(interfaceName);
		info.append("|");
		info.append("---------------------" + (message == null ? "" : message)
				+ "---------------------");
		info.append("\n\t");
		info.append(content);
		logger.debug(info.toString());
	}

	public static void info(Object message) {
		logger.info(message);
	}

	public static void info(Object message, Throwable t) {
		logger.info(message, t);
	}

	public static void debug(Object message) {
		if (TraceLogger.isDebugEnabled())
		{
			logger.debug(message);
		}
	}

	public static void debug(Object message, Throwable t) {
		logger.debug(message, t);
	}

	public static void debugInterface(String sendComponent,
			String receiveComponent, String interfaceName, String action,
			String content) {
		if (TraceLogger.isDebugEnabled()) {
			StringBuilder log = new StringBuilder();
			log.append("------");
			log.append(sendComponent);
			log.append("-->");
			log.append(receiveComponent);
			log.append(",interfaceName=");
			log.append(interfaceName);
			log.append(",action=");
			log.append(action);
			log.append("------");
			logger.debug(log.toString());
			logger.debug(content);
		}
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

	public static void trace(Object message, Throwable t) {
		logger.trace(message, t);
	}

	public static boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

}