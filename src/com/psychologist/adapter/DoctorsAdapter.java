package com.psychologist.adapter;

import java.util.List;

import com.psychologist.model.Doctors;
import com.psychologist_app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DoctorsAdapter extends  BaseAdapter{
	private Context context;
	private static LayoutInflater inflater=null;
	List<Doctors> doctorsList;
	Holder holder;
	String couponId;
	public DoctorsAdapter(Context context,List<Doctors> cartlist) {
	
		this.context = context;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.doctorsList=cartlist;
		
		
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return doctorsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return doctorsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi=convertView;
		Doctors doctors=doctorsList.get(position);
		if(vi==null)
	    {
			holder = new Holder();
			
	    	vi = inflater.inflate(R.layout.doctor_row_view, null);
	    	holder.thumb_image=(ImageView)vi.findViewById(R.id.thumb_image);
	    	holder.doctor_name=(TextView)vi.findViewById(R.id.txt_doctor_name);
	    	holder.doctorspecility=(TextView)vi.findViewById(R.id.txt_speciality);
	    	holder.work_profile=(TextView)vi.findViewById(R.id.txt_current_profile);
	    	holder.address=(TextView)vi.findViewById(R.id.txt_city_state_country);
	    	holder.ask_appointment=(Button)vi.findViewById(R.id.ask_appointment);
	    	 holder.ask_appointment.setOnClickListener(askAppointmentListener);
	    	vi.setTag(holder);
	    	holder.doctor_name.setText(""+doctors.getUserName());
	    	holder.doctorspecility.setText(doctors.getUserSubCategoryName());
	    	holder.work_profile.setText(doctors.getDescription());
	    	holder.address.setText(doctors.getCityName()+", "+doctors.getStateName()+", "+doctors.getCountryName());
	    }
		 else{
	    	 holder = (Holder) vi.getTag();
	    }
		
		
		
		return vi;
	}
	
	
	
	View.OnClickListener askAppointmentListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			
		}
	};
	static class Holder {
	    
	    ImageView thumb_image;
	    TextView doctor_name,doctorspecility,work_profile,address;
	    Button ask_appointment;
	}
}
