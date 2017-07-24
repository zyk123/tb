package com.flash.common.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonExceptionLog {

    private static Log log = LogFactory
            .getLog(CommonLogConstants.EXCETPION_LOG);

    public static void info(Object message) {
        log.info(message);
    }

    public static void info(Object message, Throwable t) {
        log.info(message, t);
    }

    public static void debug(Object message) {
        log.debug(message);
    }

    public static void debug(Object message, Throwable t) {
        log.debug(message, t);
    }

    public static void warn(Object message) {
        log.warn(message);
    }

    public static void warn(Object message, Throwable t) {
        log.warn(message, t);
    }

    public static void error(Object message) {
        log.error(message);
    }

    public static void error(Object message, Throwable t) {
        log.error(message, t);
    }

    public static void trace(Object message) {
        log.trace(message);
    }

    public static void trace(Object message, Throwable t) {
        log.trace(message, t);
    }

    public static void fatal(Object arg0) {
        log.fatal(arg0);

    }

    public static void fatal(Object arg0, Throwable arg1) {
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

    public static boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    public static boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }
}
