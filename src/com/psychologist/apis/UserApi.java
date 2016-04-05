package com.psychologist.apis;

import org.json.JSONObject;

import com.psychologist.services.UserServices;
import com.psychologist.utils.Constants;
import com.psychologist.utils.HttpConnection;
import com.psychologist.utils.PsyURLs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Window;


public class UserApi {
	
	static UserServices userServices;
	
	
	public static void getLoggenInUser(final Context context,final String username,final String password){
			userServices = (UserServices)context;
		
		class AppLoginTask extends AsyncTask<String, Void, String> {
			ProgressDialog Dialog;
			@Override
			protected void onPreExecute() {
				
		         Dialog = new ProgressDialog(context);
		    	     Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			         Dialog.setMessage("Loading...");
			         Dialog.setCancelable(false);
			         Dialog.show();
			}

			@Override
			protected String doInBackground(String... p) {
				System.out.println(""+PsyURLs.Users.userLoginPost+"?username="+username+"&password="+password);
				return  HttpConnection.getResponse(PsyURLs.Users.userLoginPost+"?username="+username+"&password="+password,Constants.httpPost);
			}
			
			protected void onPostExecute(String result) {
				Dialog.dismiss();
				if(null != result& result.equalsIgnoreCase("true")){
					
					userServices.userLoggingIn(true);
				}else{
					userServices.userLoggingIn(false);
				}
			}

		}
		new AppLoginTask().execute();
	}
	
	
	public static void registerUser(final Context context,final JSONObject jObj){
			userServices = (UserServices)context;
		
		class RegisterUserTask extends AsyncTask<String, Void, String> {
			ProgressDialog Dialog;
			@Override
			protected void onPreExecute() {
				
		         Dialog = new ProgressDialog(context);
		    	     Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			         Dialog.setMessage("Loading...");
			         Dialog.setCancelable(false);
			         Dialog.show();
			}

			@Override
			protected String doInBackground(String... p) {
				return  HttpConnection.getResponse(PsyURLs.Users.registerUser,jObj);
			}
			
			@Override
			protected void onPostExecute(String result) {
				Dialog.dismiss();
				if(null != result && result.length() != 0)
					userServices.registerUser(result);
				else{
					userServices.registerUser(null);
				}
			}
		}
		new RegisterUserTask().execute();
	}
	
	public static void getFogotPassword(final Context context,final String username){
		userServices = (UserServices)context;
	
	class ForgotpasswordTask extends AsyncTask<String, Void, String> {
		ProgressDialog Dialog;
		@Override
		protected void onPreExecute() {
			
	         Dialog = new ProgressDialog(context);
	    	     Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		         Dialog.setMessage("Loading...");
		         Dialog.setCancelable(false);
		         Dialog.show();
		}

		@Override
		protected String doInBackground(String... p) {
			return  HttpConnection.getResponse(PsyURLs.Users.forgotpasswordPost+"username="+username,Constants.httpPost);
		}
		
		protected void onPostExecute(String result) {
			Dialog.dismiss();
			if(null != result){
				
				userServices.forgotUser(result);
			}else{
				userServices.forgotUser(null);
			}
		}

	}
	new ForgotpasswordTask().execute();
}

	
}
