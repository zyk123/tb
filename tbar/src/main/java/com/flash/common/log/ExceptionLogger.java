package com.flash.common.log;

import org.apache.log4j.Logger;

/**
 * 所有的异常日志都从这里面输出
 *    
 * 项目名称：VRSUDP   
 * 类名称：TrackLogger   
 * 类描述：   
 * 创建人：alex   
 * 创建时间：2012-3-22 下午07:50:28   
 * 修改人：alex   
 * 修改时间：2012-3-22 下午07:50:28   
 * 修改备注：   
 * @version    
 *
 */
public class ExceptionLogger {
    
    private static Logger logger = Logger.getLogger ( ExceptionLogger.class ) ; 
    
    public static void LoggerInfo(String requestSerial,String usrid,String message,Throwable t)
    {
	StringBuilder info = new StringBuilder();
	info.append(requestSerial);
	info.append("|");
	info.append(usrid);
	info.append("|");
	info.append(message);
	logger.info(info.toString(),t);
    }    
    
    public static void info(Object message){
	logger.info(message);
    }
    
    public  static void info(Object message,Throwable t){
	logger.info(message,t);
    }
    
    public static void debug(Object message){
	logger.debug(message);
    }
    
    public static void debug(Object message,Throwable t){
	logger.debug(message,t);
    }
    
    public static void warn(Object message){
	logger.warn(message);
    }
    
    public static void warn(Object message,Throwable t){
	logger.warn(message,t);
    }
    
    public static void error(Object message){
	logger.error(message);
    }
    
    public static void error(Object message,Throwable t){
	logger.error(message,t);
    }
    
    public static void trace(Object message){
	logger.trace(message);
    }
    
    public static void trace(Object message,Throwable t){
	logger.trace(message,t);
    }
    
    public static boolean isInfoEnabled(){
    	return logger.isInfoEnabled();
    }
 
}
