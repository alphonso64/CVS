package com.example.cvs;


import Utility.Packet;
import Utility.socketClient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private socketClient soC;
	private Handler loginHandler;


	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW) 
		setContentView(R.layout.activity_main);
		
		loginHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==0)
				{
					  Intent intent = new Intent();
					  intent.setClass(MainActivity.this, ItemSelected.class);
					  startActivity(intent);
				}else
				{
					new  AlertDialog.Builder(MainActivity.this) .setMessage("Login Error" ).setPositiveButton("OK" ,  null ).show();
				}
				
			}
			
		};
		
		  EditText edx1 = (EditText)findViewById(R.id.editText1);
		  edx1.setOnFocusChangeListener(new OnFocusChangeListener() {
			    public void onFocusChange(View v, boolean hasFocus) {
			        EditText _v=(EditText)v;
			        if (!hasFocus) {// 失去焦点
			            _v.setHint(_v.getTag().toString());
			        } else {
			            String hint=_v.getHint().toString();
			            _v.setTag(hint);
			            _v.setHint("");
			        }
			    }
			});
		  EditText edx2 = (EditText)findViewById(R.id.editText2);
		  edx2.setOnFocusChangeListener(new OnFocusChangeListener() {
			    public void onFocusChange(View v, boolean hasFocus) {
			        EditText _v=(EditText)v;
			        if (!hasFocus) {// 失去焦点
			            _v.setHint(_v.getTag().toString());
			        } else {
			            String hint=_v.getHint().toString();
			            _v.setTag(hint);
			            _v.setHint("");
			        }
			    }
			});
		
		startSocket();
		soC.setLoginHandler(loginHandler);
		
	}
	
	public void buttonOnClick(View view)
	{
	  EditText edx1 = (EditText)findViewById(R.id.editText1);
	  EditText edx2 = (EditText)findViewById(R.id.editText2);
	  
	  Editable s1 = edx1.getText();
	  Editable s2 = edx2.getText();
	  Packet pkg = new Packet(s1.toString(), s2.toString());
	  soC.Send(pkg.getBuf());
  
	}
	
	public void startSocket() {
		Log.e("life", "hit 11");
		soC = socketClient.getInstance();		
		//soC =new socketClient();
		soC.start();
	}

	private void stopSocket() {
		soC.isRun = false;
		soC.close();
		soC.destroyInstance();
		soC = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.e("life","onStart");
	}

	@Override
	protected void onRestart() {
		Log.e("life","onRestart");
		//startSocket();
		super.onRestart();
	
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e("life","onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e("life","onPause");
	}

	@Override
	protected void onStop() {
		//stopSocket();
		Log.e("life","onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		stopSocket();
		super.onDestroy();
		Log.e("life","onDestroy");
	}

}
