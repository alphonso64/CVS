package com.example.cvs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utility.Packet;
import Utility.socketClient;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.Contacts.Data;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class InfoCheck extends Activity {
	
	socketClient soC;
	List<HashMap<String, Object>> data;
	SimpleAdapter adapter;
	Handler DbInfoHandler;
	int cnt=0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW) 
		setContentView(R.layout.infocheck);
		
		soC = socketClient.getInstance();
		
		final String [] nameAtrr = {"����","γ��","��ѹ","����","����ʱ��"};
		
		ListView listView = (ListView) this.findViewById(R.id.listView); 
	    data = new ArrayList<HashMap<String,Object>>(); 
		for(int i=0;i<nameAtrr.length;i++)
		{
			HashMap<String, Object> item = new HashMap<String, Object>();  
			item.put("Attr",nameAtrr[i]);
			item.put("value",String.valueOf(i));
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
	}
	
	public void requestInfoClick(View view)
	{
	 
	  Packet pkg = new Packet(7," ");
	  soC.Send(pkg.getBuf());

	  
	  
	  
  
	}
	
	
}