package com.android.taozywu.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.android.taozywu.R;
import com.android.taozywu.adapter.PullListAdapter;
import com.android.taozywu.parser.JsonParser;
import com.android.taozywu.widget.MyListView;
import com.android.taozywu.widget.MyListView.OnRefreshListener;


public abstract class PullListActivity extends ListActivity {

//	protected PullToRefreshListView listView;
	private PullListAdapter pulllistAdapter;
	protected ProgressDialog oDialog;
	private PullAsyncTask pullAsyncTask;
	
	//分页
	protected int totalPage = 0;	// 总页数
	protected int pageSize = 7;	// 一页条数
	protected int page = 1;			// 当前页数
	
	private Boolean refresh = false;
	private Boolean isLoadingMore = false;
	
	private View footView;
	private View footView2;
	
	private ProgressBar moreProgressBar;
	
	private int lastItem;
	private int firstItem;
	

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		// 载入子类视图
		setContentView(GetContentViewLayout());
//		listView = (PullToRefreshListView) getListView();
//		listView.setVisibility(View.GONE);
		System.out.println("PullListActivity.onCreate适配器开始");
//	    setListAdapter(this.mainAdapter);
		// 载入子类Adapter
		System.out.println("PullListActivity.onCreate适配器开始");
		pulllistAdapter = GetPullListAdapter();
		setListAdapter(pulllistAdapter);
	    System.out.println("PullListActivity.onCreate适配器结束");
	    oDialog = new ProgressDialog(this);
	    if (oDialog.isShowing()) {
	    	oDialog.hide();
	    	oDialog.setMessage("加载中...");
		}
	    // 载入初始化数据
	    InitPullList(new String[] {""});
	    // 监控刷新
	    listview_onfreshlistener();
	    // 监控滚动
	    listview_onscrolllistener();
	}
	
	// 监控刷新
	private void listview_onfreshlistener() {
		((MyListView) getListView()).setonRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				handleRefresh();
			}
		});
	}
	// 监控滚动
	private void listview_onscrolllistener() {

		((MyListView) getListView()).setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lastItem = firstVisibleItem + visibleItemCount - 2;
				firstItem = firstVisibleItem;
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (lastItem == pulllistAdapter.getCount()
						&& scrollState == SCROLL_STATE_IDLE) {
					loadMoreStatus(view);
					return;
				}

				if (firstItem == 0 && scrollState == SCROLL_STATE_IDLE) {
					page = 1;
					handleRefresh();
				}
			}
		});

	}
	
	
	
	
	// 给子类提供适配器
	protected abstract PullListAdapter GetPullListAdapter();
	// 给子类载入子类的视图
	protected abstract int GetContentViewLayout();
	// 给子类提供获取数据的方法.
	protected void InitPullList(String... paras) {
		pullAsyncTask = new PullAsyncTask();
		try {
			pullAsyncTask.execute(paras);
		} catch (Exception e) {
			System.out.println("PullListActivity.InitPullList.catch =" + e.getMessage());
		}
	}
	// 为子类提供URL的方法.
	protected abstract String GetPullListUrl(String... params);
	// 为子类提供参数调用方法
	protected abstract String[] GetAsyncTaskStringParas();
	
	
	
	
	
	
	
	// 获取数据进行处理
	protected ArrayList<HashMap<String, String>> fetchStatus(String... params) {
		// 获取URL。
		String url = GetPullListUrl(params);
		System.out.println("PullListActivity fetchStatus url=" + url);
		if (url != null && url.length() > 0) {
			JsonParser jsonParser = new JsonParser(url);
			totalPage = jsonParser.GetIntDataByKey("total_pages");
			pageSize = jsonParser.GetIntDataByKey("page_sizes");
			pageSize = pageSize == 0 ? 7 : pageSize;
			
			return jsonParser.GetArrayDataByKey("content");
		} else {
			totalPage = 1;
			
			return new ArrayList<HashMap<String, String>>();
		}
	}
	
	// 刷新获取数据
	protected void handleRefresh() {
		if (!refresh) {
			refresh = true;
			cancelTask();
			pullAsyncTask = new PullAsyncTask();
			pullAsyncTask.execute(GetAsyncTaskStringParas());
		}
	}
	
	// 取消任务
	private void cancelTask() {
		// 判断异步是否存在。
		if (pullAsyncTask != null) {
			// 如果存在的话。
			if (pullAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
				// 停止
				pullAsyncTask.cancel(true);
				// 如果还在加载更多
				if (isLoadingMore) {
					page--;
				}
			}
		}
	}
	
	// 加载更多
	private void loadMoreStatus(View paramView) {
		if (!isLoadingMore && (page < totalPage)) {
			cancelTask();
			isLoadingMore = true;
			moreProgressBar.setVisibility(View.VISIBLE);
			page++;
			pullAsyncTask = new PullAsyncTask();
			pullAsyncTask.execute(GetAsyncTaskStringParas());
		}
	}
	
	
	// 添加尾部
	protected void addFootView2() {
		if (footView != null) {
			((MyListView) getListView()).removeFooterView(footView);
		}

		if (footView2 == null) {
			footView2 = LayoutInflater.from(this).inflate(R.layout.foot_view2,
					null);
		}

		if (((MyListView) getListView()).getFooterViewsCount() == 0) {
			((MyListView) getListView()).addFooterView(footView2);
		}
	}
	
	
	private void addFootView() {
		if (footView == null) {
			footView = LayoutInflater.from(this).inflate(R.layout.foot_view,
					null);
		}
		if (footView2 != null) {
			((MyListView) getListView()).removeFooterView(footView2);
		}
		if (((MyListView) getListView()).getFooterViewsCount() == 0) {
			((MyListView) getListView()).addFooterView(footView);
		}
		if (moreProgressBar == null) {
			moreProgressBar = (ProgressBar) findViewById(R.id.more_progressbar);
		}
	}
	
	
	
	// 异步获取数据
	class PullAsyncTask extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {
	
		public PullAsyncTask() {}
		
		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
			return fetchStatus(params);
		}
		
		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			
			// result 是否有值
			if(result!=null) {
				// 添加尾部
				if (page == totalPage && page>0) {
					// 最后一页
					addFootView2();
				} else {
					addFootView();
				}
				if(refresh) {	// 刷新
					refresh = false;
					pulllistAdapter.replaceAll(result);
					((MyListView) getListView()).setSelection(1);
					page = 1;
				} else if(isLoadingMore) { // 更多
					isLoadingMore = false;
					moreProgressBar.setVisibility(View.GONE);
					pulllistAdapter.addAll(result);
				} else {	// 默认
					System.out.println("......................");
					if (result.size() > 0) {
						pulllistAdapter.replaceAll(result);
						((MyListView) getListView()).setSelection(1);
						page = 1;
					}
				}
//				listView.setVisibility(View.VISIBLE);
			} else {
				
			}
			((MyListView) getListView()).onRefreshComplete();
			super.onPostExecute(result);
		}
	}
}
	