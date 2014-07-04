package com.example.cvs;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW) 
        
        Button btn = (Button) findViewById(R.id.button1);
       // btn.setAlpha(100);
		setContentView(R.layout.activity_main);
	}
	
	public void buttonOnClick(View view)
	{
	  EditText edx1 = (EditText)findViewById(R.id.editText1);
	  EditText edx2 = (EditText)findViewById(R.id.editText2);
	  
	  Editable s1 = edx1.getText();
	  Editable s2 = edx2.getText();
	  String a = "admin";
	  String b = "123456";
//	  if( s1.toString().equals(a) && s2.toString().equals(b))
//	  {
//		  Log.e("aa","login success");
//		  Intent intent = new Intent();
//		  intent.setClass(MainActivity.this, InfoCheck.class);
//		  startActivity(intent);
//	  }else{
//		  new  AlertDialog.Builder(this) .setMessage("Login Error" ).setPositiveButton("OK" ,  null ).show(); 
//	  }
		  
	  Intent intent = new Intent();
	  intent.setClass(MainActivity.this, InfoCheck.class);
	  startActivity(intent);
	  
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
