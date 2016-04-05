package com.psychologist.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.psychologist.model.Doctors;

public class DoctorsParser {
	
public static List<Doctors> parseDoctorsList(String response) {
		
		ArrayList<Doctors> doctorsList = new ArrayList<Doctors>();
		
		try {
			JSONArray jArr=new JSONArray(response);
			for (int i = 0; i < (null != jArr ? jArr
				.length() : 0); i++) {
				JSONObject jobject = (JSONObject) jArr.get(i);
				Doctors doctors=new Doctors();
				if(!jobject.isNull("userId"))
					doctors.setUserId(Integer.parseInt(jobject.getString("userId")));
				if(!jobject.isNull("address"))
					doctors.setAddress(jobject.getString("address"));
				if(!jobject.isNull("age"))
					doctors.setAge(Integer.parseInt(jobject.getString("age")));
				if(!jobject.isNull("dob"))
					doctors.setDob(Long.parseLong(jobject.getString("dob")));
				if(!jobject.isNull("email"))
					doctors.setEmail(jobject.getString("email"));
				if(!jobject.isNull("gender"))
					doctors.setGender(jobject.getString("gender"));
				if(!jobject.isNull("imagePath"))
					doctors.setImagePath(jobject.getString("imagePath"));
				if(!jobject.isNull("mobile"))
					doctors.setMobile(jobject.getString("mobile"));
				if(!jobject.isNull("userName"))
					doctors.setUserName(jobject.getString("userName"));
				if(!jobject.isNull("cityName"))
					doctors.setCityName(jobject.getString("cityName"));
				if(!jobject.isNull("stateName"))
					doctors.setStateName(jobject.getString("stateName"));
				if(!jobject.isNull("countryName"))
					doctors.setCountryName(jobject.getString("countryName"));
				if(!jobject.isNull("userSubCategoryName"))
					doctors.setUserSubCategoryName(jobject.getString("userSubCategoryName"));
				if(!jobject.isNull("description"))
					doctors.setDescription(jobject.getString("description"));
				doctorsList.add(doctors);
			}
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return doctorsList;
	}
}
