package com.psychologist.apis;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Window;

import com.psychologist.services.DoctorsServices;
import com.psychologist.utils.Constants;
import com.psychologist.utils.HttpConnection;
import com.psychologist.utils.PsyURLs;

public class DoctorsApi {
static DoctorsServices doctorServices;
	
	
	public static void getDoctor(final Context context,final JSONObject jObj){
		doctorServices = (DoctorsServices)context;
		
		class CategoryTask extends AsyncTask<String, Void, String> {
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
				return  HttpConnection.getResponse(PsyURLs.Doctor.doctor,jObj);
			}
			
			protected void onPostExecute(String result) {
				Dialog.dismiss();
				if(null != result){
					
					doctorServices.getdoctorslist(result);
				}else{
					doctorServices.getdoctorslist(null);
				}
			}

		}
		new CategoryTask().execute();
	}

}
