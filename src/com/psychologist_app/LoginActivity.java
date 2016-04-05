package com.psychologist_app;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.psychologist.apis.UserApi;
import com.psychologist.services.UserServices;
import com.psychologist.utils.Util;

public class LoginActivity extends MyBaseActivity implements UserServices{
	Dialog dialogpopUp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	String network=getNetworkClass(LoginActivity.this);
	System.out.println("network is "+network);
		final EditText username=(EditText)findViewById(R.id.editTextusername);
		final EditText password=(EditText)findViewById(R.id.editTextpassword);
		Button login=(Button)findViewById(R.id.buttonLogin);
		
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserApi.getLoggenInUser(LoginActivity.this, username.getText().toString(),password.getText().toString());
			}
		});
		TextView register=(TextView)findViewById(R.id.txtsignup);
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
		TextView forgotPassword=(TextView)findViewById(R.id.txtforgotpassword);
		forgotPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showForgotPasswordPopUp();
			}
		});
	}

	@Override
	public void userLoggingIn(boolean userResponse) {
	if(userResponse==true){
		Intent intent=new Intent(LoginActivity.this,MainActivity.class);
		startActivity(intent);
	}else{
		Toast.makeText(LoginActivity.this, "Invalid credential", Toast.LENGTH_LONG).show();
	}
	}

	@Override
	public void registerUser(String afterRegisteration) {
		// TODO Auto-generated method stub
		
	}
	private void showForgotPasswordPopUp() {
		dialogpopUp = new Dialog(this);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		Window window = dialogpopUp.getWindow();
		window.setBackgroundDrawableResource(android.R.color.transparent);
		window.requestFeature(window.FEATURE_NO_TITLE);
		dialogpopUp.setContentView(R.layout.forgot_password);
		final EditText email_id=(EditText)dialogpopUp.findViewById(R.id.edt_email_id);
		Button buttnclose = (Button) dialogpopUp.findViewById(R.id.btnclose);
		buttnclose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				dialogpopUp.dismiss();
			}

		});
		Button btnrequest = (Button) dialogpopUp.findViewById(R.id.send_email);
		btnrequest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UserApi.getFogotPassword(LoginActivity.this, email_id.getText().toString());
				dialogpopUp.dismiss();
			}

		});
		
		dialogpopUp.show();

	}

	@Override
	public void forgotUser(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject json=new JSONObject(response);
			String status=json.getString("status");
			if(status.equalsIgnoreCase("Success")){
				dialogpopUp.dismiss();
				Toast.makeText(LoginActivity.this, "Your password send to your email id.", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(LoginActivity.this, "Please enter Correct email id", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getNetworkClass(Context context) {
	    TelephonyManager mTelephonyManager = (TelephonyManager)
	            context.getSystemService(Context.TELEPHONY_SERVICE);
	    int networkType = mTelephonyManager.getNetworkType();
	    switch (networkType) {
	        case TelephonyManager.NETWORK_TYPE_GPRS:
	        case TelephonyManager.NETWORK_TYPE_EDGE:
	        case TelephonyManager.NETWORK_TYPE_CDMA:
	        case TelephonyManager.NETWORK_TYPE_1xRTT:
	        case TelephonyManager.NETWORK_TYPE_IDEN:
	            return "2G";
	        case TelephonyManager.NETWORK_TYPE_UMTS:
	        case TelephonyManager.NETWORK_TYPE_EVDO_0:
	        case TelephonyManager.NETWORK_TYPE_EVDO_A:
	        case TelephonyManager.NETWORK_TYPE_HSDPA:
	        case TelephonyManager.NETWORK_TYPE_HSUPA:
	        case TelephonyManager.NETWORK_TYPE_HSPA:
	        case TelephonyManager.NETWORK_TYPE_EVDO_B:
	        case TelephonyManager.NETWORK_TYPE_EHRPD:
	        case TelephonyManager.NETWORK_TYPE_HSPAP:
	            return "3G";
	        case TelephonyManager.NETWORK_TYPE_LTE:
	            return "4G";
	        default:
	            return "Unknown";
	    }
	}
}
