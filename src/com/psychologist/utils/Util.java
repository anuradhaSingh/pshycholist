package com.psychologist.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;

public class Util {

	static Gson gson = null;

	private static Gson getGsonInstance() {
		if (null == gson)
			gson = new Gson();
		return gson;
	}
	
	
}
