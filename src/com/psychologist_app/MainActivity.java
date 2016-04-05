package com.psychologist_app;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.navdrawer.SimpleSideDrawer;
import com.psychologist.adapter.DoctorsAdapter;
import com.psychologist.apis.CategoryApi;
import com.psychologist.apis.DoctorsApi;
import com.psychologist.model.CategoryBean;
import com.psychologist.model.CountryBean;
import com.psychologist.model.Doctors;
import com.psychologist.parser.DoctorsParser;
import com.psychologist.services.DoctorsServices;
import com.psychologist.services.UserCategoryServices;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends MyBaseActivity implements UserCategoryServices,DoctorsServices{
	SimpleSideDrawer slide_me;
	Spinner categoryspin;
	List<CategoryBean> categoryList;
	List<Doctors>doctorslist;
	List<String> categoryStringList;
	ListView doctorsList;
	EditText name,city;
	int pos=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		slide_me = new SimpleSideDrawer(this);
		slide_me.setLeftBehindContentView(R.layout.right_menu);
		ImageView menu=(ImageView)findViewById(R.id.menu_button);
		categoryspin=(Spinner)findViewById(R.id.sub_category_spin);
		CategoryApi.getCategory(MainActivity.this);
		menu.setVisibility(View.VISIBLE);
		doctorsList = (ListView)findViewById(R.id.listdoctors);
		name=(EditText)findViewById(R.id.serach_name_text);
		city=(EditText)findViewById(R.id.search_city_text);
		categoryspin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String selection = (String) parent.getItemAtPosition(position);
		         pos = -1;

		        for (int i = 0; i < categoryStringList.size(); i++) {
		            if (categoryStringList.get(i).equals(selection)) {
		                pos = i;
		                break;
		            }
		        }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		Button search=(Button)findViewById(R.id.btn_search);
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JSONObject json=new JSONObject();
				try {
					if(name.getText().toString()!=null&&name.getText().toString().trim().length()>0){
					json.put("name", name.getText().toString());
					}
					if(pos>0){
					int subid = categoryList.get(pos).getUserSubCategoryId()-1;
					json.put("subCategoryId",subid );
					}
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DoctorsApi.getDoctor(MainActivity.this, json);
			}
		});
		menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				slide_me.toggleLeftDrawer();
			}
		});
		
		
	}
	@Override
	public void getCategorylist(String categoryResponse) {
		// TODO Auto-generated method stub
		try{
			Type listType = new TypeToken<ArrayList<CategoryBean>>(){}.getType();
			List<CategoryBean> list = new GsonBuilder()
			.create().fromJson(categoryResponse, listType);
			categoryList = list;
			if(null != categoryList){
				categoryStringList=new ArrayList<String>();
				categoryStringList.add("Please Select");
					for(int i =0;i<categoryList.size();i++){
						CategoryBean company=categoryList.get(i);
				String comName=company.getUserSubCategoryName();
				categoryStringList.add(comName);
			}
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,categoryStringList);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					categoryspin.setAdapter(adapter);

			
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		
		
	}
	@Override
	public void getdoctorslist(String doctorsResponse) {
		// TODO Auto-generated method stub
		try{
			List<Doctors> doctorsist=DoctorsParser.parseDoctorsList(doctorsResponse);
			if(doctorsist!=null&& !doctorsist.equals("")){
		doctorsList.setAdapter(new DoctorsAdapter(MainActivity.this,doctorsist));
			}
		
	}catch(Exception e){
		e.printStackTrace();
	}
	}

	
}
