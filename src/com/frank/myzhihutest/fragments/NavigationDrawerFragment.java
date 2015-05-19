package com.frank.myzhihutest.fragments;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.frank.myzhihutest.R;
import com.frank.myzhihutest.utils.DrawerListAdapter;
import com.frank.myzhihutest.utils.DrawerListItem;
//import android.R;
import android.app.Activity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
/**
 * 有时候找不到R.layout.xxx是因为import 了android.R
 * @author Frank
 *
 */
public class NavigationDrawerFragment extends Fragment {
	
	private OnDrawerItemSelectedCallback mCallback;
	
	private final String PRE_USER_LEARNED_DRAWER = "navigation_drawer_learned";
	private final String STATE_SELECTED_POSITON = "selected_navigation_position";
	
	private boolean mUserLearnedDrwaer;
	
	private int mCurrentSelectedItem = 0 ;
	private boolean mFromSavedInstance;
	
	/**
	 * 将actionBar 和 drawer 绑定的组件ActionBarDrawerToggle
	 */
	private ActionBarDrawerToggle mDrawerToggle;
	
	private DrawerLayout mDrawerLayout;
	private View mFragmentContainerView;//用于drawerlayout.closeDrawer(view mFragmeentView)
	
	private ListView mDrawerList;
	
	private List<DrawerListItem> mList = new ArrayList<DrawerListItem>();
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try{
			mCallback = (OnDrawerItemSelectedCallback)activity;
		}
		catch(ClassCastException e){
			throw new ClassCastException("Activity must implements OnDrawerSelectedCallback");
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//判断用户是否已经知道有drawer，第一次启动自动打开drawer，以后默认关闭
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mUserLearnedDrwaer = sp.getBoolean(PRE_USER_LEARNED_DRAWER, false);
		//取出上次存储的位置，设置mFromSavedInstance为true;
		if(savedInstanceState != null){
			mCurrentSelectedItem = savedInstanceState.getInt(STATE_SELECTED_POSITON);
			mFromSavedInstance = true;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mDrawerList = (ListView)inflater.inflate(R.layout.fragment_drawer_list, container	, false);
		View headerView = inflater.inflate(R.layout.navigation_drawer_header_layout, container, false);
		mDrawerList.addHeaderView(headerView);
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectItem(position);
			}
		});
		
		String[] items = getResources().getStringArray(R.array.fragment_navigation_items);
//		int[] icons = getResources().getIntArray(R.array.fragment_navigation_icons);
		int[] icons = {
				R.drawable.ic_drawer_home_normal,
				R.drawable.ic_drawer_explore_normal,
				R.drawable.ic_drawer_collect_normal,
				R.drawable.ic_drawer_draft_normal,
				R.drawable.ic_drawer_search_normal,
				R.drawable.ic_drawer_question_normal
		};
		for(int i =0 ; i<items.length ; i++	){
			DrawerListItem item = new DrawerListItem(icons[i], items[i]);//这可能有问题
			mList.add(item);
		}
		/**
		 * selectItem()
		 */
		selectItem(mCurrentSelectedItem);//从savedInstace中取出的mCurrentSelectedItem
		DrawerListAdapter adapter = new DrawerListAdapter(this.getActivity(), mList);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setItemChecked(mCurrentSelectedItem, true);//设置listview点击position的位置的item项，为选中 或者 非选中 的状态
		
		return mDrawerList;
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//设置该fragment有自己的actionbar
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		mCallback = null;
	}
	
	/**
	 * Android calls onSaveInstanceState() before the activity becomes vulnerable to being destroyed by the system, 
	 * but does not bother calling it when the instance is actually being destroyed by a user action (such as pressing the BACK key)
 
		当某个activity变得“容易”被系统销毁时，该activity的onSaveInstanceState就会被执行，如当用户按下Home见，关闭屏幕显示，启动新的activity，横竖屏切换
		除非该activity是被用户主动销毁的，例如当用户按BACK键的时候。
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_POSITON, mCurrentSelectedItem);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
        // 当系统配置改变时调用DrawerToggle的改变配置方法（例如横竖屏切换会回调此方法）  
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	public void onCreateOptionsMenu(Menu menu , MenuInflater inflater){
		//当抽屉打开时显示全局的actionbar设置
		if(mDrawerLayout != null && isDrawerOpen()){
			inflater.inflate(R.menu.global, menu);
			showGlobalContextActionBar();
		}
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	public boolean isDrawerOpen() {
		// TODO Auto-generated method stub
		return mDrawerLayout!=null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(mDrawerToggle.onOptionsItemSelected(item)){
			return true;
		}
//		if(item.getItemId() == R.id.action_exa)
		
		return super.onOptionsItemSelected(item);
	}
	
	private void showGlobalContextActionBar(){
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
//		actionBar.setNavigationMode(mCurrentSelectedItem);
		actionBar.setTitle(R.string.app_name);
	}
	
	/**
	 * 设置导航Drawer
	 * @param fragmentId
	 * @param drawerLayout
	 */
	public void setUp(int fragmentId , DrawerLayout drawerLayout){
		mFragmentContainerView = getActivity().findViewById(fragmentId);//在第一次打开drawer时用的
		mDrawerLayout = drawerLayout;
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);//给左上角图标的左边加上一个返回的图标 
		actionBar.setHomeButtonEnabled(true);//这个小于4.0版本的默认值为true的。但是在4.0及其以上是false，该方法的作用：决定左上角的图标是否可以点击。没有向左的小图标。 true 图标可以点击  false 不可以点击。
		//影藏Actionbar上的app icon
		actionBar.setDisplayShowHomeEnabled(false) ;
		
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if(!isAdded()){
					return;
				}
				getActivity().supportInvalidateOptionsMenu();//调用 onPrepareOptionMenu()
			};
			
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if(!isAdded()){
					return;
				}
				
				if(!mUserLearnedDrwaer){
					mUserLearnedDrwaer=true;
					SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
					sp.edit().putBoolean(PRE_USER_LEARNED_DRAWER, true).commit();
				}
				getActivity().supportInvalidateOptionsMenu(); // 调用 onPrepareOptionsMenu() 
			}
			
		};
		
		if(!mUserLearnedDrwaer && !mFromSavedInstance){
			mDrawerLayout.openDrawer(mFragmentContainerView);//在这里用到了
		}
		
		mDrawerLayout.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mDrawerToggle.syncState();
			}
		});
		/*
		 * 最后别忘了setDrawerListener(toggle)
		 */
		mDrawerLayout.setDrawerListener(mDrawerToggle); 
	}
	
	/**
	 * 有点乱，注意括号
	 * @return
	 */
	private ActionBar getActionBar() {
		// TODO Auto-generated method stub
		return ((ActionBarActivity)getActivity()).getSupportActionBar();
	}

	protected void selectItem(int position) {
		// TODO Auto-generated method stub
		mCurrentSelectedItem = position;
		//设置listview当前被选中的item
		if(mDrawerList!=null){
			mDrawerList.setItemChecked(position, true);
		}
		//在设置完当前被选中的item后，关闭drawer
		if(mDrawerLayout!=null){
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if(mCallback != null){
			if(mCurrentSelectedItem == 0){
				mCallback.onDrawerItemSelected(getString(R.string.app_name));
				return;
			}
			mCallback.onDrawerItemSelected(mList.get(position-1).getItem());
		}
	}
	
	public static interface OnDrawerItemSelectedCallback{
		public void onDrawerItemSelected(String title);
	}

}
