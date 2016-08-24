package com.android.taozywu.parser;

// 解析URL为JSON或ARRAY。
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.taozywu.util.JSONUtils;


public class JsonParser {

	private String result = null;
	private JSONObject jsonObject = null;
	private JSONArray jsonArray = null;
	private String urlString = null;

	private ArrayList<HashMap<String, String>> arrayList = null;

	// 初始化
	public JsonParser(String url) {
		urlString = url;
		if (result == null)
			result = GetJsonData();
	}

	// 将url转成JSON数据。
	private String GetJsonData() {
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpGet localHttpGet = new HttpGet(urlString);
		HttpResponse httpResponse = null;
		// 请求超时
		defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
        // 读取超时
		defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
		  
		try {
			httpResponse = defaultHttpClient.execute(localHttpGet);
			StatusLine statusLine = httpResponse.getStatusLine();
			if (statusLine.getStatusCode() == 200) {
				result = EntityUtils
						.toString(httpResponse.getEntity(), "UTF-8");
			}
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return result;
	}

	// 获取 JSON对象
	public JSONObject GetJsonObject() {
		try {
			jsonObject = new JSONObject(result);
		} catch (JSONException e) {
			return null;
		}
		return jsonObject;
	}

	// 获取带key的JSON对象
	public JSONObject GetJsonObjectByKey(String key) {
		JSONObject jsonKey = null;
		try {
			jsonKey = GetJsonObject().getJSONObject(key);
		} catch (JSONException e) {
			return null;
		}
		return jsonKey;
	}

	// 获取JSON 数组
	public JSONArray GetJsonArrayByKey(String key) {
		try {
			jsonArray = GetJsonObject().getJSONArray(key);
		} catch (JSONException e) {
			return null;
		}

		return jsonArray;
	}

	// 获取INT数据
	public int GetIntDataByKey(String key) {
		int o = 0;
		try {
			o = GetJsonObject().getInt(key);
		} catch (JSONException e) {
			return 0;
		}
		return o;
	}

	// 获取数组。
	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, String>> GetArrayDataByKey(String key) {
		JSONArray jsonArray = GetJsonArrayByKey(key);

		int jsonarraylen = jsonArray.length();

		if (jsonarraylen > 0) {
			arrayList = new ArrayList<HashMap<String, String>>();
			try {
				for (int i = 0; i < jsonarraylen; i++) {
					jsonObject = jsonArray.getJSONObject(i);
					HashMap<String, String> hasmap = new HashMap<String, String>();
					hasmap = ((HashMap<String, String>) JSONUtils
							.mapFromJSONObject(jsonObject));
					arrayList.add(hasmap);
				}
			} catch (JSONException e) {
				return new ArrayList<HashMap<String, String>>();
			}
		}
		return arrayList;
	}
}
