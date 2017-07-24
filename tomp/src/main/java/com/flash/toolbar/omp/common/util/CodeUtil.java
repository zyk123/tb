package com.flash.toolbar.omp.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库码值转换文字工具类
 * @author zhulin
 */
public class CodeUtil {
	
	public static final String DEL_FLAG = "delFlag";
	
	public static final String FLOW_SUB_STATUS = "flowSubStatus";

	public static Map<String,String> delFlag = new HashMap<String,String>();
	
	public static Map<String,String> flowSubStatus = new HashMap<String,String>();
	
	static{
		delFlag.put("0", "invalid");
		delFlag.put("1", "valid");
		
		flowSubStatus.put("0", "charging");
		flowSubStatus.put("1", "charging succeeded");
		flowSubStatus.put("2", "charging failed");
	}
	
	
	/**
	 * 码值转换文字
	 * @param codeType String 码值类型
	 * @param code String 码值
	 * @return String 码值对应文字
	 * */
	public static String parseCode(String codeType,String code){
		
		String name = "";
		
		if(FLOW_SUB_STATUS.equals(codeType)){
			name = flowSubStatus.get(code);
		}
		else if(DEL_FLAG.equals(codeType)){
			name = delFlag.get(code);
		}
		else{
			name = code;
		}
		
		return name;
	}
	
}
