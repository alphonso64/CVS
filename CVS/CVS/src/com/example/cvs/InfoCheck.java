package com.example.cvs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utility.Packet;
import Utility.socketClient;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW) 
		setContentView(R.layout.infocheck);
		
		soC = socketClient.getInstance();
		
		ListView listView = (ListView) this.findViewById(R.id.listView); 
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>(); 
		for(int i=0;i<500;i++)
		{
			HashMap<String, Object> item = new HashMap<String, Object>();  
			item.put("Attr","name");
			item.put("value",i);
			data.add(item); 
			
		}
		
	    SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item,   
                new String[]{"Attr", "value"}, new int[]{R.id.name, R.id.value});
	    
	    listView.setAdapter(adapter); 
	  	
	}
	
	public void requestInfoClick(View view)
	{
	 
	  Packet pkg = new Packet(7," ");
	  soC.Send(pkg.getBuf());
  
	}
	
	
}
