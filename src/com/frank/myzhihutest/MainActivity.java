package com.frank.myzhihutest;

import java.lang.reflect.Type;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.frank.myzhihutest.fragments.NavigationDrawerFragment;
import com.frank.myzhihutest.fragments.NavigationDrawerFragment.OnDrawerItemSelectedCallback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends ActionBarActivity implements OnDrawerItemSelectedCallback  {
	/*
	 * 整体的layout
	 */
	private DrawerLayout mDrawerLayout;
	
	/**
	 * 菜单栏的fragment
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	
	private CharSequence mTitle;
	
	private Fragment currentFragment;
	private Fragment lastFragment;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		
		//设置抽屉
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout));
		
//		
	}


	
	
	@Override
	public void onDrawerItemSelected(String title) {
		// TODO Auto-generated method stub
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		currentFragment = fm.findFragmentByTag(title);
		if(currentFragment == null){
			currentFragment  = ContentFragment.newInstance(title);
			ft.add(R.id.container,currentFragment , title);
		}
		if(lastFragment != null){
			ft.hide(lastFragment);
		}
		if(currentFragment.isDetached()){
			ft.attach(currentFragment);
		}
		ft.show(currentFragment);
		lastFragment = currentFragment;
		ft.commit();
		onSectionSelected(title);
	}

	private void onSectionSelected(String title) {
		// TODO Auto-generated method stub
		mTitle = title;
	}
	
	public void restoreActionBar(){
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 内容fragment
	 */
	public static class ContentFragment extends Fragment {
		
		private static final String ARG_SECTION_TITLE = "section_title";

		/**
		 * 返回根据title参数创建的fragment
		 */
		public static ContentFragment newInstance(String title) {
			ContentFragment fragment = new ContentFragment();
			Bundle args = new Bundle();
			args.putString(ARG_SECTION_TITLE, title);
			fragment.setArguments(args);
			return fragment;
		}

		public ContentFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			TextView textView = (TextView) rootView.findViewById(R.id.section_label);
			textView.setText(getArguments().getString(ARG_SECTION_TITLE));
			return rootView;
		}
	}
	
	

	
}
