package com.frank.myzhihutest.utils;

import java.util.List;

import com.frank.myzhihutest.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * ViewHolder 优化BaseAdapter的思路
 * 1、创建Item类，用于封装数据
 * 2、在构造方法中初始化用于映射的数据List
 * 3、创建ViewHolder，创建布局映射关系
 * 4、判断convertView是否为null，若为空，则设置tag，否则通过tag取出ViewHolder
 * 5、给ViewHolder中的控件赋值设置数据。

 */
public class DrawerListAdapter extends BaseAdapter {
	
	private Context context;
	private List<DrawerListItem> mList;
	private LayoutInflater inflater;
	
	public DrawerListAdapter(Context context , List<DrawerListItem> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		mList = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = new ViewHolder();
		//这个if else是为了给holder赋值
		if(convertView == null){
			convertView = inflater.inflate(R.layout.navigation_drawer_item_layout, parent,false);
			holder.imageView = (ImageView) convertView.findViewById(R.id.item_icon);
			holder.textView = (TextView) convertView.findViewById(R.id.item_title);
			convertView.setTag(holder);
		}
		else{
			
			holder = (ViewHolder) convertView.getTag();
			
		}
		DrawerListItem item = mList.get(position);
		/**
         * 通过给viewHolder.xxx赋值的方式，来给convertView赋值。
         * 和普通模式相比，少了每次都要xxxView = convertView.findViewById(xxx),但多了储存空间，空间换时间
         */
		holder.imageView.setImageResource(item.getImageId());
		holder.textView.setText(item.getItem());
		return convertView;
	}
	
	class ViewHolder{
		public ImageView imageView;
		public TextView textView;
	}

}
