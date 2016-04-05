package com.psychologist_app;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public abstract class MyBaseActivity extends Activity 
{
	LinearLayout tab_content;
	Fragment currentFragment;
	public Boolean mKillApp = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		tab_content = new LinearLayout(this);
		tab_content.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT));
		tab_content.setScrollContainer(true);
		tab_content.setId(585);
		setContentView(tab_content);
		
	}

	/**
	 * @param newFragment fragment which you want to display in the tab
	 */
	public void navigateTo(Fragment newFragment,String tag) 
	{
		currentFragment = newFragment;
		
		FragmentManager manager = getFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		
		// What if i press home  ????????????????
		
		ft.replace(tab_content.getId(), newFragment,tag);
		ft.addToBackStack(null);
		
		ft.commit();
	}

	@Override
	public void onBackPressed() 
	{
		FragmentManager manager = getFragmentManager();
	
		if(currentFragment!=null)
		{
			String tag = currentFragment.getTag();
			Log.d("tag", ""+tag);
			
			if(tag.equalsIgnoreCase("something"))
			{
				// do something 
			}
		}
		
		if (manager.getBackStackEntryCount() > 1) 
		{
			super.onBackPressed();
		} 
		else 
		{
			finish();
		}
	}
	
	public void clearStack()
	{
		FragmentManager manager = getFragmentManager();
		
		while(manager.getBackStackEntryCount() > 1)
	    manager.popBackStack();
		
	}
	
   public  Activity getMyActivity(){
	   
	   
	   
	return MyBaseActivity.this;
	   
   }
}
