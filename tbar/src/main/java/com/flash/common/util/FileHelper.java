package com.flash.common.util;

import java.io.File;

public class FileHelper {
	
	/**
	 * 获取路径下拆分文件的索引
	 * @param path
	 * @return
	 */
	public static int getSplitFileIndex(String path){
		int index = 1;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File[] files = dir.listFiles();
		if(files!=null && files.length > 0){
			index = files.length;
		}
		return index;
	}
}
