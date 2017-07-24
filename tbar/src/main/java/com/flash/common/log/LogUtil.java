package com.flash.common.log;

import org.apache.log4j.PropertyConfigurator;

public class LogUtil {
    /**
     * 修改日志文件的目录
     *
     * @param log4jPath 日志配置文件所在目录
     */
    public static void changeLogHome(String log4jPath) {
        PropertyConfigurator.configure(log4jPath);
    }

    /**
     * @param path            日志所在目录
     * @param watchingLogTime 监控时间
     */
    public static void configureAndWatch(String path, long watchingLogTime) {
        PropertyConfigurator.configureAndWatch(path, watchingLogTime);
    }

    /**
     * 默认60秒
     *
     * @param path 日志所在目录
     */
    public static void configureAndWatch(String path) {
        PropertyConfigurator.configureAndWatch(path);
    }
}
