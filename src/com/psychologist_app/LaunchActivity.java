package com.psychologist_app;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class LaunchActivity extends MyBaseActivity{
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.launch_screen);
	TextView terms=(TextView)findViewById(R.id.terms);
	final CheckBox chk=(CheckBox)findViewById(R.id.textView1);
	terms.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(LaunchActivity.this,TermsActivity.class);
			startActivity(intent);
		}
	});
	Button next=(Button)findViewById(R.id.btn_continue);
	next.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(chk.isChecked()==true){
			Intent intent=new Intent(LaunchActivity.this,LoginActivity.class);
			startActivity(intent);
			}else{
				Toast toast=Toast.makeText(LaunchActivity.this, "Please check terma and condition", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
			}
		}
	});
	
}

}
