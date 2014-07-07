package com.example.cvs;

import Utility.Packet;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class ItemSelected extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW) 
		setContentView(R.layout.itemselect);
		
	}
	
	public void infoCheckClick(View view)
	{
		  Intent intent = new Intent();
		  intent.setClass(ItemSelected.this, InfoCheck.class);
		  startActivity(intent);
	}

}
