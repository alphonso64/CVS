package com.example.cvs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utility.Packet;
import Utility.socketClient;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class InfoCheck extends FragmentActivity {

	private SlidingMenu menu;
	private socketClient soC;
	private List<HashMap<String, Object>> data;
	private SimpleAdapter adapter;
	private Handler DbInfoHandler;
	private int cnt=0;
	private int cv_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(R.string.app_name);

		// set the Above View
		setContentView(R.layout.infocheck);

		// configure the SlidingMenu
		menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new SampleListFragment())
		.commit();
		
		soC = socketClient.getInstance();
		
		final String [] nameAtrr = {"经度","纬度","电压","气温","加载时间"};
		
		ListView listView = (ListView) this.findViewById(R.id.listView); 
	    data = new ArrayList<HashMap<String,Object>>(); 
		for(int i=0;i<nameAtrr.length;i++)
		{
			HashMap<String, Object> item = new HashMap<String, Object>();  
			item.put("Attr",nameAtrr[i]);
			item.put("value","NULL");
			data.add(item); 
			
		}
		
	    adapter = new SimpleAdapter(this, data, R.layout.item,   
                new String[]{"Attr", "value"}, new int[]{R.id.name, R.id.value});
	    
	    listView.setAdapter(adapter); 
	    
	    DbInfoHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				  List<String> ls = (List<String>) msg.obj;
				  if(ls.size()== nameAtrr.length)
				  {
					  for(int i=0;i<nameAtrr.length;i++)
					  {
						  HashMap<String, Object> item = new HashMap<String, Object>();  
						  StringBuilder sb = new StringBuilder();
						  sb.append(ls.get(i));
						  sb.append(" cnt=");
						  sb.append(cnt++);
						  item.put("Attr",nameAtrr[i]);
					      item.put("value",sb.toString());
						  data.set(i, item);
					  }
					  adapter.notifyDataSetChanged();	
				  }			
			}
			
		};
		
		soC.setDbInfoHandler(DbInfoHandler);
		
		cv_id = 0;
	}

	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			super.onBackPressed();
		}
	}
	
	public void requestInfoClick(View view)
	{
	 if(cv_id == 0)
	 {
			new  AlertDialog.Builder(this) .setMessage("车辆不在线" ).setPositiveButton("OK" ,  null ).show();
			
	 }else
	 {
		  Packet pkg = new Packet(7,Integer.toString(cv_id));
		  soC.Send(pkg.getBuf()); 
	 }

	}

	public void switchContent(int cv_id) {
		this.cv_id = cv_id;
		
		
	}
	

}
