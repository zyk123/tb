package com.flash.toolbar.omp.common.io;

/**
 * TempFileProvider.java
 * com.edu.dk.stargate.util.io
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2011-12-31 		<a href="mailto:ff.ge@js-datacraft.com"></a>
 *
 * Copyright (c) 2011, TNT All Rights Reserved.
 */


import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.flash.toolbar.omp.common.util.DateUtil;


public class TempFileProvider {
	/**
	 * the system property key giving us the location of the temp directory
	 */

	private static final Logger log = Logger.getLogger(TempFileProvider.class);

	/**
	 * Static class only
	 */
	private TempFileProvider() {
	}

	/**
	 * @return Returns the system temporary directory i.e.
	 *         <code>isDir == true</code>
	 */
	public static File getSystemTempDir() {
		Configuration cfg = new Configuration(null, "environment.properties");
		String systemTempDirPath = cfg.getValue("tomp.reportstatics.export");
		if (systemTempDirPath == null) {
			log.info("property not available: " + systemTempDirPath);
		}
		return new File(systemTempDirPath);
	}

	/**
	 * @return Returns a temporary directory, i.e. <code>isDir == true</code>
	 */
	public static File getTempDir() {
		File systemTempDir = getSystemTempDir();
		String timeDir = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss");
		File tempDir = new File(systemTempDir, timeDir);
		// ensure that the temp directory exists
		if (tempDir.exists()) {
			// nothing to do
		} else {
			// not there yet
			if (!tempDir.mkdirs()) {
				log.info("Failed to create temp directory: " + tempDir);
			}
			if (log.isDebugEnabled()) {
				log.debug("Created temp directory: " + tempDir);
			}
		}
		// done
		return tempDir;
	}

	public static File createTempFile(String businessName,String suffix) {
		File tempDir = TempFileProvider.getTempDir();
		// we have the directory we want to use
		return createTempFile(suffix, businessName,tempDir);
	}

	public static File createTempFile(String suffix,String businessName, File directory) {
			try {
				File file = new File(directory, businessName+suffix);
				if (!file.exists()) {
					file.createNewFile();
				}
				log.info("Created temp file: "+file);
				return file;
			} catch (IOException e) {
				log.error(e);
			}
			return null;
	}
	
	
	public static void main(String[] args) {
		System.out.println(createTempFile("pageAccess", ".xls"));
	}
	
}
