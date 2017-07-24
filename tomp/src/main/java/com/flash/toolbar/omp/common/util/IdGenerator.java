package com.flash.toolbar.omp.common.util;

import java.util.UUID;

public class IdGenerator {

	public static String getUUID(){
		String uuid = String.valueOf(UUID.randomUUID());
		uuid = uuid.replace("-", "");
		return uuid;
	}
}
