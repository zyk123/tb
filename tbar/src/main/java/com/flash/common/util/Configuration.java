/**
 * Configuration.java Created on May 27, 2009
 * Copyright 
 * All right reserved. 
 */
package com.flash.common.util;

import com.flash.common.log.DebugLog;

import java.io.*;
import java.util.Properties;

/**
 * 读取.properties文件
 *
 * @author lynn
 * @Time 10:04:19 AM
 */
public class Configuration {

    private Properties props;
    private FileInputStream jdbc;
    private FileOutputStream outputFile;
    private DebugLog log = new DebugLog(Configuration.class);

    public Configuration() {
        props = new Properties();
    }

    /**
     * 初始化Configuration类
     *
     * @param filepath 要读取的配置文件的路径+名称
     */
    public Configuration(String filepath) {

        props = new Properties();
        try {
            jdbc = new FileInputStream(filepath);
            props.load(jdbc);
            jdbc.close();
        } catch (FileNotFoundException e) {
            log.info("文件不存在");
            e.printStackTrace();
        } catch (IOException e) {
            log.info("装载文件失败");
            e.printStackTrace();
        }
    }

    public Configuration(String path, String fileName) {

        props = new Properties();
        try {
            InputStream inputStream = null;
            if (path == null || "".equals(path)) {
                // 先从当前类所处路径的根目录中寻找属性文件
                inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
            } else {
                inputStream = new FileInputStream(path + fileName);
            }
            props.load(inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            log.info("文件不存在");
            e.printStackTrace();
        } catch (IOException e) {
            log.info("装载文件失败");
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String fileName = "jdbc.properties";
        Configuration rc = new Configuration(null, fileName);//相对路径

        String driverClassName = rc.getValue("dataSource.driverClassName");//以下读取properties文件的值
        String url = rc.getValue("dataSource.url");
        String username = rc.getValue("dataSource.username");
        String password = rc.getValue("dataSource.password");

        System.out.println("driverClassName = " + driverClassName);//以下输出properties读出的值
        System.out.println("url = " + url);
        System.out.println("username = " + username);
        System.out.println("password = " + password);

//		String filePath = "";
//		Configuration cf = new Configuration();
//		String ipp = cf.getValue(filePath, "ip");
//		System.out.println("ipp = " + ipp);
//		//	        cf.clear();
//		cf.setValue("min", "999");
//		cf.setValue("max", "1000");
//		cf.saveFile(filePath, "test");
    }

    /**
     * 重载函数，得到key的值
     *
     * @param key 取得其值的键
     * @return key的值
     */
    public String getValue(String key) {
        if (props.containsKey(key)) {
            String value = props.getProperty(key);
            return value;
        } else
            return "";

    }

    /**
     * 重载函数，得到key的值
     *
     * @param fileName properties文件的路径+文件名
     * @param key      取得其值的键
     * @return key的值
     */
    public String getValue(String fileName, String key) {
        try {
            String value = null;
            jdbc = new FileInputStream(fileName);
            props.load(jdbc);
            jdbc.close();
            if (props.containsKey(key)) {
                value = props.getProperty(key);
                return value;
            } else
                return value;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * 清除properties文件中所有的key和其值
     */
    public void clear() {
        props.clear();
    }

    /**
     * 改变或添加一个key的值，当key存在于properties文件中时该key的值被value所代替，
     * 当key不存在时，该key的值是value
     *
     * @param key   要存入的键
     * @param value 要存入的值
     */
    public void setValue(String key, String value) {
        props.setProperty(key, value);
    }

    /**
     * 将更改后的文件数据存入指定的文件中，该文件可以事先不存在
     *
     * @param fileName    文件路径+文件名称
     * @param description 对该文件的描述
     */
    public void saveFile(String fileName, String description) {
        try {
            outputFile = new FileOutputStream(fileName);
            props.store(outputFile, description);
            outputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
