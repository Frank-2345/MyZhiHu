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
 * ViewHolder �Ż�BaseAdapter��˼·
 * 1������Item�࣬���ڷ�װ����
 * 2���ڹ��췽���г�ʼ������ӳ�������List
 * 3������ViewHolder����������ӳ���ϵ
 * 4���ж�convertView�Ƿ�Ϊnull����Ϊ�գ�������tag������ͨ��tagȡ��ViewHolder
 * 5����ViewHolder�еĿؼ���ֵ�������ݡ�

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
		//���if else��Ϊ�˸�holder��ֵ
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
         * ͨ����viewHolder.xxx��ֵ�ķ�ʽ������convertView��ֵ��
         * ����ͨģʽ��ȣ�����ÿ�ζ�ҪxxxView = convertView.findViewById(xxx),�����˴���ռ䣬�ռ任ʱ��
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
