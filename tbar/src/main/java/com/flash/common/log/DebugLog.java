package com.flash.common.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 默认实现所有的Log接口
 *
 * @author Administrator
 */
public class DebugLog // implements Log
{

    private Log log;

    public DebugLog(String name) {
        log = LogFactory.getLog(name);
    }

    public DebugLog(Class c) {
        log = LogFactory.getLog(c);
    }

    // 错误用法wrong
    public void info(Object message) {
        log.info(message);
    }

    public void info(Object message, Throwable t) {
        log.info(message, t);
    }

    public void debug(Object message) {
        log.debug(message);
    }

    public void debug(Object message, Throwable t) {
        log.debug(message, t);
    }

    public void warn(Object message) {
        log.warn(message);
    }

    public void warn(Object message, Throwable t) {
        log.warn(message, t);
    }

    public void error(Object message) {
        log.error(message);
    }

    public void error(Object message, Throwable t) {
        log.error(message, t);
    }

    public void trace(Object message) {
        log.trace(message);
    }

    public void trace(Object message, Throwable t) {
        log.trace(message, t);
    }

    public void fatal(Object arg0) {
        log.fatal(arg0);

    }

    public void fatal(Object arg0, Throwable arg1) {
        log.fatal(arg0, arg1);
    }

    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    public boolean isFatalEnabled() {
        return log.isFatalEnabled();
    }

    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }
}
