package com.android.taozywu.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.widget.BaseAdapter;


public abstract class PullListAdapter extends BaseAdapter {

	protected ArrayList<HashMap<String, String>> dataArr;
	
	public PullListAdapter(ArrayList<HashMap<String, String>> arrayList)
	{
	    this.dataArr = arrayList;
	}
	
	public void add(HashMap<String, String> map)
	{
	    if (map != null) {
	    	this.dataArr.add(map);
	    	notifyDataSetChanged();
	    }
	}
	
	
	public void addAll(ArrayList<HashMap<String, String>> arrayList)
	{
	    if (!arrayList.isEmpty()) {
	    	this.dataArr.addAll(arrayList);
		    notifyDataSetChanged();
	    }
	}
	
	
	
	public Map<String, String> first()
	{
	    Map<String, String> map;
    	if ((this.dataArr == null) || (this.dataArr.isEmpty())) {
    		map = null;
	    } else {
	    	map = (Map<String, String>)this.dataArr.get(0);
	    }
	    return map;
	}
	
	@Override
	public int getCount() {
		return this.dataArr.size();
	}

	@Override
	public Object getItem(int itemint) {
		return this.dataArr.get(itemint);
	}

	@Override
	public long getItemId(int paraint) {
		return paraint;
	}
	

	public Map<String, String> last()
	{
	    Map<String,String> map;
		if ((this.dataArr == null) || (this.dataArr.isEmpty())) {
			map = null;
	    } else {
	    	map = (Map<String, String>)this.dataArr.get(getCount() - 1);
	    }
        return map;
	}
	
	
	public void replaceAll(ArrayList<HashMap<String, String>> arrayList)
	{
	    this.dataArr = arrayList;
	    notifyDataSetChanged();
	}

	public void unshiftAll(ArrayList<HashMap<String, String>> arrayList)
	{
	    if (!arrayList.isEmpty()) {
	    	this.dataArr.addAll(0, arrayList);
		    notifyDataSetChanged();
	    }
	}
}
