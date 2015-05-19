package com.frank.myzhihutest.utils;

public class DrawerListItem {
	/**
	 * ImageId
	 * Item
	 */
	private int ImageId;
	private String Item;
	public DrawerListItem(int ImageID , String Item) {
		// TODO Auto-generated constructor stub
		this.ImageId = ImageID;
		this.Item = Item;
	}
	public int getImageId() {
		return ImageId;
	}
	public void setImageId(int imageId) {
		ImageId = imageId;
	}
	public String getItem() {
		return Item;
	}
	public void setItem(String item) {
		Item = item;
	}
	
	
	

}
