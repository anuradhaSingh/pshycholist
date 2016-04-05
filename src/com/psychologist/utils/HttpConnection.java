package com.psychologist.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

	public class HttpConnection {
		
		public static String getResponse(String URL,String token,boolean isToken){
			 String responseString= "";
			 HttpClient httpclient = new DefaultHttpClient();
			try{
			HttpGet request = new HttpGet(URL);
		    if(isToken){
		    	request.addHeader("content-type", "application/json");
		    	request.addHeader("Accept","application/json");
		    	request.addHeader("X-CSRF-Token",token);
		    }
		    else{
		    	request.addHeader("Cookie",token);
		    }
		    HttpResponse response = httpclient.execute(request);
		    StatusLine statusLine = response.getStatusLine();
		    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        response.getEntity().writeTo(out);
		        responseString = out.toString();
		        out.close();
		    } else{
		        //Closes the connection.
		        response.getEntity().getContent().close();
		        throw new IOException(statusLine.getReasonPhrase());
		    }
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				httpclient.getConnectionManager().shutdown();
			}
			return responseString;
		}
		public static String getResponse(String URL,String username,String password,String method){
			 String responseString= "";
			 HttpClient httpclient = new DefaultHttpClient();
			try{
			HttpGet request = new HttpGet(URL);
		    	request.addHeader("content-type", "application/json");
		    	request.addHeader("Accept","application/json");
		    	
		  
		    HttpResponse response = httpclient.execute(request);
		    StatusLine statusLine = response.getStatusLine();
		    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        response.getEntity().writeTo(out);
		        responseString = out.toString();
		        out.close();
		    } else{
		        //Closes the connection.
		        response.getEntity().getContent().close();
		        throw new IOException(statusLine.getReasonPhrase());
		    }
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				httpclient.getConnectionManager().shutdown();
			}
			return responseString;
		}
		
		public static String getResponse(String URL,JSONObject requestObj){
			
			HttpClient httpClient = new DefaultHttpClient();
			String responseString= "";
			try {
			    HttpPost request = new HttpPost(URL);
			    StringEntity params =new StringEntity(requestObj.toString());
			    request.addHeader("content-type", "application/json");
			    request.addHeader("Accept","application/json");
			    request.setEntity(params);
			    HttpResponse response = httpClient.execute(request);
			    StatusLine statusLine = response.getStatusLine();
			    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
			        ByteArrayOutputStream out = new ByteArrayOutputStream();
			        response.getEntity().writeTo(out);
			        responseString = out.toString();
			        out.close();
			    } else{
			        response.getEntity().getContent().close();
			        throw new IOException(statusLine.getReasonPhrase());
			    }
			}catch (Exception ex) {
				ex.printStackTrace();
			} finally {
			    httpClient.getConnectionManager().shutdown();
			}
			 return responseString;
		}
		
		public static String getResponse(String URL,String token,String cookie,JSONObject requestObj,String method){
			
			HttpClient httpClient = new DefaultHttpClient();
			String responseString= "";
			try {
				StringEntity params =new StringEntity(requestObj.toString());
				HttpResponse response =null;
			    if(method.equals(Constants.httpPost)){
			    	HttpPost request = new HttpPost(URL);
			    	request.addHeader("content-type", "application/json");
			    	request.addHeader("Accept","application/json");
			    	request.addHeader("X-CSRF-Token",token);
			    	request.addHeader("Cookie",cookie);
			    	request.setEntity(params);
			    	response = httpClient.execute(request);
			    }else if(method.equals(Constants.httpGet)){
			    	HttpGet request = new HttpGet(URL);
			    	request.addHeader("content-type", "application/json");
			    	request.addHeader("Accept","application/json");
			    	request.addHeader("X-CSRF-Token",token);
			    	request.addHeader("Cookie",cookie);
			    	response = httpClient.execute( request);
			    }else if(method.equals(Constants.httpPut)){
			    	HttpPut request = new HttpPut(URL);
			    	request.addHeader("content-type", "application/json");
			    	request.addHeader("Accept","application/json");
			    	request.addHeader("X-CSRF-Token",token);
			    	request.addHeader("Cookie",cookie);
			    	request.setEntity(params);
			    	response = httpClient.execute( request);
			    }else if(method.equals(Constants.httpDelete)){
			    	HttpDelete request = new HttpDelete(URL);
			    	request.addHeader("content-type", "application/json");
			    	request.addHeader("Accept","application/json");
			    	request.addHeader("X-CSRF-Token",token);
			    	request.addHeader("Cookie",cookie);
			    	response = httpClient.execute( request);
			    }
			    StatusLine statusLine = response.getStatusLine();
			    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
			        ByteArrayOutputStream out = new ByteArrayOutputStream();
			        response.getEntity().writeTo(out);
			        responseString = out.toString();
			        out.close();
			    } else{
			        response.getEntity().getContent().close();
			        throw new IOException(statusLine.getReasonPhrase());
			    }
			}catch (Exception ex) {
				ex.printStackTrace();
			} finally {
			    httpClient.getConnectionManager().shutdown();
			}
			 return responseString;
		}
		
		
		public static String getResponse(String URL,String method){
			HttpClient httpClient = new DefaultHttpClient();
			String responseString= "";
			try {
			    HttpRequest request = null;
			    if(method.equals(Constants.httpGet)){
			    	request = new HttpGet(URL);
			    }else if(method.equals(Constants.httpPost)){
			    	request = new HttpPost(URL);
			    }
			    request.addHeader("content-type", "application/json");
			    request.addHeader("Accept","application/json");
			    HttpResponse response = httpClient.execute((HttpUriRequest) request);
			    StatusLine statusLine = response.getStatusLine();
			    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
			        ByteArrayOutputStream out = new ByteArrayOutputStream();
			        response.getEntity().writeTo(out);
			        responseString = out.toString();
			        out.close();
			    } else{
			        response.getEntity().getContent().close();
			        throw new IOException(statusLine.getReasonPhrase());
			    }
			   return responseString;
			}catch (Exception ex) {
				ex.printStackTrace();
			} finally {
			    httpClient.getConnectionManager().shutdown();
			}
			return responseString;
			
		}
		
public static String getResponse(String URL,String token,String cookie,JSONObject requestObj){
			
			HttpClient httpClient = new DefaultHttpClient();
			String responseString= "";
			try {
			    HttpPost request = new HttpPost(URL);
			    StringEntity params =new StringEntity(requestObj.toString());
			    request.addHeader("content-type", "application/json");
			    request.addHeader("Accept","application/json");
			    request.addHeader("X-CSRF-Token",token);
			    request.addHeader("Cookie",cookie);
			    request.setEntity(params);
			    HttpResponse response = httpClient.execute(request);
			    StatusLine statusLine = response.getStatusLine();
			    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
			        ByteArrayOutputStream out = new ByteArrayOutputStream();
			        response.getEntity().writeTo(out);
			        responseString = out.toString();
			        out.close();
			    } else{
			        response.getEntity().getContent().close();
			        throw new IOException(statusLine.getReasonPhrase());
			    }
			}catch (Exception ex) {
				ex.printStackTrace();
			} finally {
			    httpClient.getConnectionManager().shutdown();
			}
			 return responseString;
		}
		
}
