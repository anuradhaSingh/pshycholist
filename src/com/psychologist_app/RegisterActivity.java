package com.psychologist_app;


import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.psychologist.apis.CountryApi;
import com.psychologist.apis.UserApi;
import com.psychologist.model.CityBean;
import com.psychologist.model.CountryBean;
import com.psychologist.model.StateBean;
import com.psychologist.services.CountryServices;
import com.psychologist.services.UserServices;
import com.psychologist.utils.Constants;
import com.psychologist.utils.DateUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RegisterActivity extends Activity implements UserServices,CountryServices{
	
	Button date_of_birth,register;
	public static final int DATE_OF_BIRTH = 0;
	ToggleButton gender;
	EditText name,age,email_id,mobile_number,address,problem_overview,id_text;
	AutoCompleteTextView country,state,city;
	Spinner idSpin,countryId,stateId,cityId;
	String [] IdArrays;
	List<CountryBean> countryList;
	List<String> countryStringList;
	List<StateBean> stateList;
	List<String> stateStringList;
	List<CityBean> cityList;
	List<String> cityStringList;
	int pos=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		// setup the date range buttons.
		date_of_birth = (Button)findViewById(R.id.d_o_b);
		register=(Button)findViewById(R.id.buttonRegister);
		name=(EditText)findViewById(R.id.firstname_edittext);
		age=(EditText)findViewById(R.id.edt_age);
		email_id=(EditText)findViewById(R.id.edt_email_id);
		mobile_number=(EditText)findViewById(R.id.edt_mobile);
		address=(EditText)findViewById(R.id.edt_address);
		problem_overview=(EditText)findViewById(R.id.edt_problem_overview);
		id_text=(EditText)findViewById(R.id.edt_id);
		country=(AutoCompleteTextView)findViewById(R.id.edt_country);
		countryId=(Spinner)findViewById(R.id.country_id);
		stateId=(Spinner)findViewById(R.id.state_id);
		cityId=(Spinner)findViewById(R.id.city_id);
		state=(AutoCompleteTextView)findViewById(R.id.edt_states);
		city=(AutoCompleteTextView)findViewById(R.id.edt_city);
		IdArrays=getResources().getStringArray(R.array.id_Array);
		idSpin=(Spinner)findViewById(R.id.spin_id);
		gender=(ToggleButton)findViewById(R.id.flashlightButton);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RegisterActivity.this,
					                                     android.R.layout.simple_spinner_item, IdArrays);
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    idSpin.setAdapter(dataAdapter);
	    idSpin.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					if(position>0){
						id_text.setVisibility(View.VISIBLE);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
		gender.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gender.setTextOff("M");
				gender.setTextOn("F");
			}
		});
		date_of_birth.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
					showDialog(RegisterActivity.DATE_OF_BIRTH);
					}
				});
		country.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
			}
		});
		country.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(country.getText().toString().length()==3){
					CountryApi.getCountry(RegisterActivity.this, country.getText().toString());
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(country.getText().toString().length()==3){
					CountryApi.getCountry(RegisterActivity.this, country.getText().toString());
				}
			}
		});
        state.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(state.getText().toString().length()==3){
					CountryApi.getStates(RegisterActivity.this, state.getText().toString(),countryList.get(pos).getCountryId());
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(state.getText().toString().length()==3){
					CountryApi.getStates(RegisterActivity.this, state.getText().toString(),countryList.get(pos).getCountryId());
				}
			}
		});
        city.addTextChangedListener(new TextWatcher() {
	
	    @Override
	      public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		if(city.getText().toString().length()==3){
			CountryApi.getCity(RegisterActivity.this, city.getText().toString(),stateList.get(pos).getStateId());
		}
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		if(city.getText().toString().length()==3){
			CountryApi.getCity(RegisterActivity.this, city.getText().toString(),stateList.get(pos).getStateId());
		}
	}
   });
    countryId.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String selection = (String) parent.getItemAtPosition(position);
		         pos = -1;

		        for (int i = 0; i < countryStringList.size(); i++) {
		            if (countryStringList.get(i).equals(selection)) {
		                pos = i;
		                break;
		            }
		        }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
     
      
		updateButtonDisplay(date_of_birth, DateUtil.addDays(new Date(), -0));
		Button register=(Button)findViewById(R.id.buttonRegister);
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				int count =0;
				String addressString = address.getText().toString();
				String nameString = name.getText().toString();
				String dobString = date_of_birth.getText().toString();
				String emailString = email_id.getText().toString();
				String ageString = age.getText().toString();
				String phoneString = mobile_number.getText().toString();
				String cityString = city.getText().toString();
				String stateString = state.getText().toString();
				String countryString = country.getText().toString();
				String idString = id_text.getText().toString();
				String problemOverviewString = problem_overview.getText().toString();
				String genderString = gender.getText().toString();
				if(TextUtils.isEmpty(nameString)){
					Toast toast=Toast.makeText(RegisterActivity.this, "Uh ho! We will need your first name", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
					count =1;
				}
				else if(TextUtils.isEmpty(dobString)){
					Toast toast=Toast.makeText(RegisterActivity.this, "Uh ho! We will need your date of birth", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
					count =1;
				}else if(TextUtils.isEmpty(ageString)){
					Toast toast=Toast.makeText(RegisterActivity.this, "Uh ho! We will need age", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
					count =1;
				}
				else if(!Constants.isValidEmail(emailString)){
					Toast toast=Toast.makeText(RegisterActivity.this, "Uh ho! We will need your email id", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
					count =1;
				}
				
				
				else if(TextUtils.isEmpty(phoneString) || phoneString.length() != 10){
					Toast toast=Toast.makeText(RegisterActivity.this, "Uh ho! We will need your phone number", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
					count =1;
				}else if(TextUtils.isEmpty(addressString)){
					Toast toast=Toast.makeText(RegisterActivity.this, "Uh ho! We will need address", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
					count =1;
				}
				else if(TextUtils.isEmpty(countryString)){
					Toast toast=Toast.makeText(RegisterActivity.this, "Uh ho! We will need country", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
					count =1;
				}
				else if(TextUtils.isEmpty(stateString)){
					Toast toast=Toast.makeText(RegisterActivity.this, "Uh ho! We will need state", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
					count =1;
				}
				else if(TextUtils.isEmpty(cityString)){
					Toast toast=Toast.makeText(RegisterActivity.this, "Uh ho! We will need city", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
					count =1;
				}
				else if(TextUtils.isEmpty(genderString)){
					Toast toast=Toast.makeText(RegisterActivity.this, "Uh ho! We will need your gender", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
					count =1;
				}else {
					if(count == 0)
						try {
							String add = address.getText().toString();
							String namevalue = name.getText().toString();
							String dobvalue = date_of_birth.getText().toString();
							String emailvalue = email_id.getText().toString();
							String agevalue = age.getText().toString();
							String phonevalue = mobile_number.getText().toString();
							String cityvalue = city.getText().toString();
							String statevalue = state.getText().toString();
							String countryvalue = country.getText().toString();
							String idvalue = id_text.getText().toString();
							String problemOverviewvalue= problem_overview.getText().toString();
							String gendervalue = gender.getText().toString();
								JSONObject requestObj = new JSONObject();
								requestObj.put("address", add);
								requestObj.put("age", 32);
								String dateString = dobvalue;
								 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						        Date testDate = null;
						        try {
						            testDate = sdf.parse(dateString);
						        }catch(Exception ex){
						            ex.printStackTrace();
						        }
						        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						        String newFormat = formatter.format(testDate);
							   requestObj.put("dob", newFormat);
								requestObj.put("name",namevalue );
								requestObj.put("gender", gendervalue);
								requestObj.put("mobile", phonevalue);
								requestObj.put("userSubCategoryId", 3);
								requestObj.put("identificationNo", idvalue);
								int countryId = countryList.get(pos).getCountryId();
								requestObj.put("cityId",countryId );
								requestObj.put("email", emailvalue);
								requestObj.put("description", problemOverviewvalue);
								JSONObject timeslot = new JSONObject();
								try {
									timeslot.put("startTime", "09:00 A.M");
									timeslot.put("endTime", "11:00 A.M");
								    

								} catch (JSONException e) {
								    // TODO Auto-generated catch block
								    e.printStackTrace();
								}
								JSONArray arr = new JSONArray();
						        arr.put(timeslot);
						        requestObj.put("userTimeSlots", arr);
								requestObj.put("identificationCategory", idSpin.getSelectedItem().toString());

								UserApi.registerUser(RegisterActivity.this, requestObj);
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					}
			
				
				
			
				//UserApi.registerUser(RegisterActivity.this, jObj);
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog r = null;
		switch (id) {
		case RegisterActivity.DATE_OF_BIRTH:
			try {
				Date startDate = DateUtil.parse(date_of_birth.getText().toString());
				OnDateSetListener listener = new DateSetListener(date_of_birth);
				int year = DateUtil.getYear(startDate);
				int month = DateUtil.month(startDate);
				int dayOfMonth = DateUtil.dayOfMonth(startDate);
				r = new DatePickerDialog(this, listener, year, month, dayOfMonth);
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		
		}
		return r;
	}
	
	public static class DateSetListener implements OnDateSetListener {
		private Button button;

		public DateSetListener(Button button) {
			super();
			this.button = button;
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			Calendar c = Calendar.getInstance();
			c.set(year, monthOfYear, dayOfMonth);
			updateButtonDisplay(button, c.getTime());
		}
	}
	private static void updateButtonDisplay(Button button, Date dateToSet) {
		button.setText(new SimpleDateFormat("dd-MM-yyyy").format(dateToSet));
	}

	@Override
	public void userLoggingIn(boolean userResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerUser(String afterRegisteration) {
		// TODO Auto-generated method stub
		
		
		Intent intent=new Intent(RegisterActivity.this,AfterRegistrationActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void getCountryList(String countryResponse) {
		try{
		Type listType = new TypeToken<ArrayList<CountryBean>>(){}.getType();
		List<CountryBean> list = new GsonBuilder()
		.create().fromJson(countryResponse, listType);
		 countryList = list;
		if(null != countryList){
			countryStringList=new ArrayList<String>();
				for(int i =0;i<countryList.size();i++){
					CountryBean company=countryList.get(i);
			String comName=company.getCountryName();
			countryStringList.add(comName);
		}
				country.setVisibility(View.GONE);
				countryId.setVisibility(View.VISIBLE);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_list_item_1,countryStringList);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				countryId.setAdapter(adapter);
				state.setEnabled(true);

		
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void getStateList(String stateResponse) {
		// TODO Auto-generated method stub
		try{
			Type listType = new TypeToken<ArrayList<StateBean>>(){}.getType();
			List<StateBean> list = new GsonBuilder()
			.create().fromJson(stateResponse, listType);
			 stateList = list;
			if(null != stateList){
				stateStringList=new ArrayList<String>();
			for(int j =0;j<stateList.size();j++){
						StateBean company=stateList.get(j);
				String comName=company.getStateName();
				stateStringList.add(comName);
			}
					state.setVisibility(View.GONE);
					stateId.setVisibility(View.VISIBLE);
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_list_item_1,stateStringList);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					stateId.setAdapter(adapter);
					city.setEnabled(true);

			
			}else{
				Toast toast=Toast.makeText(RegisterActivity.this, "Response does not contain any data.", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
			}
			}catch(Exception e){
				e.printStackTrace();
			}
	}

	@Override
	public void getCityList(String cityResponse) {
		// TODO Auto-generated method stub
		try{
			Type listType = new TypeToken<ArrayList<CityBean>>(){}.getType();
			List<CityBean> list = new GsonBuilder()
			.create().fromJson(cityResponse, listType);
			 cityList = list;
			if(null != cityList){
				cityStringList=new ArrayList<String>();
					for(int i =0;i<cityList.size();i++){
						CityBean city=cityList.get(i);
				String cityName=city.getCityName();
				cityStringList.add(cityName);
			}
					city.setVisibility(View.GONE);
					cityId.setVisibility(View.VISIBLE);
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_list_item_1,cityStringList);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					cityId.setAdapter(adapter);
					

			
			}else{
				Toast toast=Toast.makeText(RegisterActivity.this, "Response does not contain any data.", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
			}
			}catch(Exception e){
				e.printStackTrace();
			}
	}

	@Override
	public void forgotUser(String response) {
		// TODO Auto-generated method stub
		
	}
}
