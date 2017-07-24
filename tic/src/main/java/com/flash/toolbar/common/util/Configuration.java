package com.flash.toolbar.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取.properties文件
 * @Time 10:04:19 AM
 * @author lynn
 */
public class Configuration {

	private Properties props;
	private FileInputStream jdbc;
	private FileOutputStream outputFile;
	Logger log = Logger.getLogger(Configuration.class);

	public Configuration() {
		props = new Properties();
	}

	/**
	 * 初始化Configuration类
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
	
	public Configuration(String path, String fileName){
		
		props = new Properties();
		try {
			InputStream inputStream = null;
			if(path == null || "".equals(path)){
				// 先从当前类所处路径的根目录中寻找属性文件
				inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
			} else {
				inputStream = new FileInputStream(path+fileName);
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
	 *  重载函数，得到key的值
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
	 * @param fileName properties文件的路径+文件名
	 * @param key 取得其值的键
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
	 * @param key  要存入的键
	 * @param value  要存入的值
	 */
	public void setValue(String key, String value) {
		props.setProperty(key, value);
	}

	/**
	 * 将更改后的文件数据存入指定的文件中，该文件可以事先不存在
	 * @param fileName  文件路径+文件名称
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
