package com.psychologist.utils;

import java.io.InputStream;
import java.io.OutputStream;

import android.text.TextUtils;

public class Constants {
	
	public static final String httpGet = "GET";
	public static final String httpPost = "POST";
	public static final String httpPut = "PUT";
	public static final String httpDelete = "DELETE";
	public static final String loginFailed = "Invalid Creadentials";
	public static final String connection_error = "Something went wrong for the Connection ";
	public static final String tokenFailed = "Failed To recieve token"; 
	public static final String passwordMisMatch = "Password Mismatch";
	public static final String mobileNumUpdated = "Mobile number successfully updated";
	
	 public static void CopyStream(InputStream is, OutputStream os)
	    {
	        final int buffer_size=1024;
	        try
	        {
	            byte[] bytes=new byte[buffer_size];
	            for(;;)
	            {
	              int count=is.read(bytes, 0, buffer_size);
	              if(count==-1)
	                  break;
	              os.write(bytes, 0, count);
	            }
	        }
	        catch(Exception ex){}
	    }
	 interface SharedPref{
			String loggedInUserData = "LoggedInUserData";
		}
		 public final static boolean isValidEmail(CharSequence target) {
			  if (TextUtils.isEmpty(target)) {
			    return false;
			  } else {
			    return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
			  }
			}
	
		
		 
		 
	
		 
}
