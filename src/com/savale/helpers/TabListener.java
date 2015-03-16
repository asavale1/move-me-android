package com.savale.helpers;

import com.savale.moveme.R;



import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;

@SuppressWarnings("deprecation")
public class TabListener implements ActionBar.TabListener{

	private Fragment fragment;
	
	public TabListener(Fragment fragment){
		this.fragment = fragment;
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		Log.i("TABRESELECTED","TAB HERE");
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Log.i("INFO", "IN FRAGMENT SELECT");
		ft.replace(R.id.fragment_container, fragment);
		Log.i("INFO", "AFTER FRAGMENT SELECT");
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(fragment);
		
	}

}
