package com.psychologist.apis;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Window;

import com.psychologist.services.CountryServices;
import com.psychologist.services.UserServices;
import com.psychologist.utils.Constants;
import com.psychologist.utils.HttpConnection;
import com.psychologist.utils.PsyURLs;

public class CountryApi {
	
static CountryServices countryServices;
	
	
	public static void getCountry(final Context context,final String countryText){
		countryServices = (CountryServices)context;
		
		class CountryTask extends AsyncTask<String, Void, String> {
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
				return  HttpConnection.getResponse(PsyURLs.Country.country+countryText,Constants.httpGet);
			}
			
			protected void onPostExecute(String result) {
				Dialog.dismiss();
				if(null != result){
					
					countryServices.getCountryList(result);
				}else{
					countryServices.getCountryList(null);
				}
			}

		}
		new CountryTask().execute();
	}
	public static void getStates(final Context context,final String stateText,final int countryId){
		countryServices = (CountryServices)context;
		
		class StateTask extends AsyncTask<String, Void, String> {
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
				return  HttpConnection.getResponse(PsyURLs.Country.state+stateText+"&countryId="+countryId,Constants.httpGet);
			}
			
			protected void onPostExecute(String result) {
				Dialog.dismiss();
				if(null != result){
					
					countryServices.getStateList(result);
				}else{
					countryServices.getStateList(null);
				}
			}

		}
		new StateTask().execute();
	}
	public static void getCity(final Context context,final String cityText,final int stateId){
		countryServices = (CountryServices)context;
		
		class CityTask extends AsyncTask<String, Void, String> {
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
				return  HttpConnection.getResponse(PsyURLs.Country.city+cityText+"&stateId="+stateId,Constants.httpGet);
			}
			
			protected void onPostExecute(String result) {
				Dialog.dismiss();
				if(null != result){
					
					countryServices.getCityList(result);
				}else{
					countryServices.getCityList(null);
				}
			}

		}
		new CityTask().execute();
	}
}