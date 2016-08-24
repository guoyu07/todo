package com.android.taozywu.base;

import android.text.TextUtils;


public class Api {
	// 基础URL
	private static final String url_base ="http://192.168.10.208/taozywu/demo/main.php";
	/*************************** 优惠券 *******************************/
	// DEMO1列表
	private static final String url_demo1 = url_base
			+ "?m=demo&a=ajaxDemo1&key=%s&page=%d&pagesize=%d";
	

	// DEMO1
	public static String GetDemo1Url(String key, int page, int pageSize) {
		if(TextUtils.isEmpty(key)) key = "";
		return String.format(url_demo1,key,page,pageSize);
	}


	public static String GetUrlMobileNearByCoupons(String string, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
}