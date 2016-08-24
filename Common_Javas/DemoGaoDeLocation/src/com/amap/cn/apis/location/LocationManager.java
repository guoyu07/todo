package com.amap.cn.apis.location;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.amap.cn.apis.R;
import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.geocoder.Geocoder;
import com.amap.mapapi.location.LocationManagerProxy;

/**
 * 获取定位信息。
 */
public class LocationManager extends Activity implements LocationListener {

	private LocationManagerProxy locationManager = null;
	private TextView myLocation;
	
	private ProgressDialog progDialog = null;
	private Geocoder coder;
	private String addressName;
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			myLocation.setText((String) msg.obj);
		}
	};
	
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		myLocation = (TextView) findViewById(R.id.myLocation);
		locationManager = LocationManagerProxy.getInstance(this);
		coder = new Geocoder(this);
		progDialog=new ProgressDialog(this);
	}
	
	
	public boolean enableMyLocation() {
		boolean result = true;
		Criteria cri = new Criteria();
		cri.setAccuracy(Criteria.ACCURACY_COARSE);
		cri.setAltitudeRequired(false);
		cri.setBearingRequired(false);
		cri.setCostAllowed(false);
		String bestProvider = locationManager.getBestProvider(cri, true);
		locationManager.requestLocationUpdates(bestProvider, 2000, 10, this);
		return result;
	}

	public void disableMyLocation() {
		locationManager.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		enableMyLocation();
	}

	@Override
	protected void onPause() {
		disableMyLocation();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if (locationManager != null) {
			locationManager.removeUpdates(this);
			locationManager.destory();
		}
		locationManager = null;
		super.onDestroy();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if (location != null) {
			//经纬度
			Double geoLat = location.getLatitude();
			Double geoLng = location.getLongitude();
			// 解析地址
			try {
				List<Address> address = coder.getFromLocation(geoLat, geoLng, 3);
				if (address != null && address.size()>0) {
					Address addres = address.get(0);
					addressName=addres.getAdminArea()
							+ addres.getSubLocality() + addres.getFeatureName()
							+ "附近";
				}
			} catch (AMapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String str = ("定位成功:(" + geoLng + "," + geoLat + ","+addressName+" )");
			Message msg = new Message();
			msg.obj = str;
			if (handler != null) {
				handler.sendMessage(msg);
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
