package com.psychologist.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

public class RetrieveStream {

	public static String retrieveStreamGET(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);
		try {
			getRequest.addHeader("content-type", "application/json");
			getRequest.addHeader("Accept","application/json");
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w(RetrieveStream.class.getSimpleName(), "Error " + statusCode
						+ " for URL " + url);
				return null;
			}
			HttpEntity getResponseEntity = getResponse.getEntity();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			getResponseEntity.writeTo(out);
	        String responseString = out.toString();
	        out.close();
			return responseString;
		} catch (IOException e) {
			getRequest.abort();
			Log.w(RetrieveStream.class.getSimpleName(), "Error for URL " + url, e);
		}
		return null;
	}
	
	public static InputStream retrieveStreamPOST(String url,JSONObject requestObj) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(url);
		try {
			StringEntity params =new StringEntity(requestObj.toString());
			postRequest.addHeader("content-type", "application/json");
			postRequest.addHeader("Accept","application/json");
			postRequest.setEntity(params);
			HttpResponse getResponse = client.execute(postRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w(RetrieveStream.class.getSimpleName(), "Error " + statusCode
						+ " for URL " + url);
				return null;
			}
			HttpEntity getResponseEntity = getResponse.getEntity();
			return getResponseEntity.getContent();
		} catch (IOException e) {
			postRequest.abort();
			Log.w(RetrieveStream.class.getSimpleName(), "Error for URL " + url, e);
		}
		return null;
	}

}
