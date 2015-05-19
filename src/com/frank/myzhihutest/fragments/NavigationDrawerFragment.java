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
 * ��ʱ���Ҳ���R.layout.xxx����Ϊimport ��android.R
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
	 * ��actionBar �� drawer �󶨵����ActionBarDrawerToggle
	 */
	private ActionBarDrawerToggle mDrawerToggle;
	
	private DrawerLayout mDrawerLayout;
	private View mFragmentContainerView;//����drawerlayout.closeDrawer(view mFragmeentView)
	
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
		//�ж��û��Ƿ��Ѿ�֪����drawer����һ�������Զ���drawer���Ժ�Ĭ�Ϲر�
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mUserLearnedDrwaer = sp.getBoolean(PRE_USER_LEARNED_DRAWER, false);
		//ȡ���ϴδ洢��λ�ã�����mFromSavedInstanceΪtrue;
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
			DrawerListItem item = new DrawerListItem(icons[i], items[i]);//�����������
			mList.add(item);
		}
		/**
		 * selectItem()
		 */
		selectItem(mCurrentSelectedItem);//��savedInstace��ȡ����mCurrentSelectedItem
		DrawerListAdapter adapter = new DrawerListAdapter(this.getActivity(), mList);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setItemChecked(mCurrentSelectedItem, true);//����listview���position��λ�õ�item�Ϊѡ�� ���� ��ѡ�� ��״̬
		
		return mDrawerList;
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//���ø�fragment���Լ���actionbar
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
 
		��ĳ��activity��á����ס���ϵͳ����ʱ����activity��onSaveInstanceState�ͻᱻִ�У��統�û�����Home�����ر���Ļ��ʾ�������µ�activity���������л�
		���Ǹ�activity�Ǳ��û��������ٵģ����統�û���BACK����ʱ��
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
        // ��ϵͳ���øı�ʱ����DrawerToggle�ĸı����÷���������������л���ص��˷�����  
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	public void onCreateOptionsMenu(Menu menu , MenuInflater inflater){
		//�������ʱ��ʾȫ�ֵ�actionbar����
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
	 * ���õ���Drawer
	 * @param fragmentId
	 * @param drawerLayout
	 */
	public void setUp(int fragmentId , DrawerLayout drawerLayout){
		mFragmentContainerView = getActivity().findViewById(fragmentId);//�ڵ�һ�δ�drawerʱ�õ�
		mDrawerLayout = drawerLayout;
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);//�����Ͻ�ͼ�����߼���һ�����ص�ͼ�� 
		actionBar.setHomeButtonEnabled(true);//���С��4.0�汾��Ĭ��ֵΪtrue�ġ�������4.0����������false���÷��������ã��������Ͻǵ�ͼ���Ƿ���Ե����û�������Сͼ�ꡣ true ͼ����Ե��  false �����Ե����
		//Ӱ��Actionbar�ϵ�app icon
		actionBar.setDisplayShowHomeEnabled(false) ;
		
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if(!isAdded()){
					return;
				}
				getActivity().supportInvalidateOptionsMenu();//���� onPrepareOptionMenu()
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
				getActivity().supportInvalidateOptionsMenu(); // ���� onPrepareOptionsMenu() 
			}
			
		};
		
		if(!mUserLearnedDrwaer && !mFromSavedInstance){
			mDrawerLayout.openDrawer(mFragmentContainerView);//�������õ���
		}
		
		mDrawerLayout.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mDrawerToggle.syncState();
			}
		});
		/*
		 * ��������setDrawerListener(toggle)
		 */
		mDrawerLayout.setDrawerListener(mDrawerToggle); 
	}
	
	/**
	 * �е��ң�ע������
	 * @return
	 */
	private ActionBar getActionBar() {
		// TODO Auto-generated method stub
		return ((ActionBarActivity)getActivity()).getSupportActionBar();
	}

	protected void selectItem(int position) {
		// TODO Auto-generated method stub
		mCurrentSelectedItem = position;
		//����listview��ǰ��ѡ�е�item
		if(mDrawerList!=null){
			mDrawerList.setItemChecked(position, true);
		}
		//�������굱ǰ��ѡ�е�item�󣬹ر�drawer
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
