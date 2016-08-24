package com.android.taozywu.adapter;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.taozywu.R;

public class MainAdapter extends PullListAdapter {

	private Activity act;
	
	public MainAdapter(ArrayList<HashMap<String, String>> arrayList, Activity activity) {
		super(arrayList);
		// TODO Auto-generated constructor stub
		act = activity;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		HashMap<?, ?> hm = (HashMap<?, ?>) getItem(position);
		ViewHolder vh;
		if (view == null) {
			view = act.getLayoutInflater().inflate(
					R.layout.list_item, null);
			vh = new ViewHolder();
			
			vh.title = (TextView) view
					.findViewById(R.id.title);

			vh.content = (TextView) view
					.findViewById(R.id.content);
			view.setTag(vh);
		} else {
			vh = (ViewHolder) view.getTag();
		}
		// 将HashMap的值放到vh
		vh.title.setText(hm.get("title").toString().trim());
		vh.content.setText(hm.get("content").toString().trim());
		
		return view;
	}
	
	
	final class ViewHolder {
		// 标题
		public TextView title;
		// 描述
		public TextView content;

		private ViewHolder() {
		}
	}

}
