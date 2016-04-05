package com.psychologist.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;


public class SharedPreferencesHelper {

	public static final String SHAREDPREF_NAME = "hey";
	private static SharedPreferences  mPrefs  ;
	private static Editor prefsEditor ;
	public static final String DELIVERY_DATE = "delivery_date";
	
	
	public static void setmPrefs(SharedPreferences mPrefs) {
		SharedPreferencesHelper.mPrefs = mPrefs;
	}

	public static void setPrefsEditor(Editor prefsEditor) {
		SharedPreferencesHelper.prefsEditor = prefsEditor;
	}

	/*public static void setLoggedUserInfo(UserDetail userResponse){
		Gson gson = new Gson();
        String json = gson.toJson(userResponse);
        prefsEditor.putString(Constants.SharedPref.loggedInUserData, json);
        prefsEditor.commit();
	}
	
	public static UserDetail getLoggedInUserInfo(){
		Gson gson = new Gson();
        String json = mPrefs.getString(Constants.SharedPref.loggedInUserData, "");
        UserDetail obj = gson.fromJson(json, UserDetail.class);
        return obj ;
	}
	*/

   
	
}
