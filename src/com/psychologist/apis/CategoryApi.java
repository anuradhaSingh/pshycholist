package com.psychologist.apis;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Window;

import com.psychologist.services.CountryServices;
import com.psychologist.services.UserCategoryServices;
import com.psychologist.utils.Constants;
import com.psychologist.utils.HttpConnection;
import com.psychologist.utils.PsyURLs;

public class CategoryApi {
	
static UserCategoryServices categoryServices;
	
	
	public static void getCategory(final Context context){
		categoryServices = (UserCategoryServices)context;
		
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
				return  HttpConnection.getResponse(PsyURLs.Category.category,Constants.httpGet);
			}
			
			protected void onPostExecute(String result) {
				Dialog.dismiss();
				if(null != result){
					
					categoryServices.getCategorylist(result);
				}else{
					categoryServices.getCategorylist(null);
				}
			}

		}
		new CategoryTask().execute();
	}

}
