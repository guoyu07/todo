package com.android.taozywu.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.taozywu.R;
import com.android.taozywu.adapter.MainAdapter;
import com.android.taozywu.adapter.PullListAdapter;
import com.android.taozywu.base.Api;

public class MainActivity extends PullListActivity {

//	private ListView listView;
	private MainAdapter mainAdapter;
	private ProgressDialog oDialog;
	
	private String keyWord = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 监听按钮事件
		InitOnClickListerner();
		// 
		
	}
	
	// 监听按钮
	private void InitOnClickListerner() {
		View rl = findViewById(R.id.search);
		rl.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				searchMainList();
			}
			
		});
	}
	
	
	
	
	
	
	// 搜索
	protected void searchMainList() {
		TextView tvUn = (TextView) findViewById(R.id.keywords);
		String un = (tvUn.getText().toString()).trim();
		if (TextUtils.isEmpty(un)) {
			Toast.makeText(this, "请输入关键词", 0).show();
			tvUn.requestFocus();
			return;
		}
		keyWord = un;
		handleRefresh();
	}

	@Override
	protected int GetContentViewLayout() {
		return R.layout.main;
	}

	@Override
	protected PullListAdapter GetPullListAdapter() {
		return new MainAdapter(new ArrayList<HashMap<String, String>>(), this);
	}

	@Override
	protected String GetPullListUrl(String... params) {
		return Api.GetDemo1Url(params[0], page , pageSize);
	}

	@Override
	protected String[] GetAsyncTaskStringParas() {
		System.out.println("MainActivity.GetAsyncTaskStringParas.key=" + keyWord);
		String[] str={keyWord};
		return str;
	}
}
