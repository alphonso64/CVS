package com.example.cvs;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;


public class InfoCheck extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW) 
		setContentView(R.layout.infocheck);
	}
	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event){
//		   if (keyCode == KeyEvent.KEYCODE_BACK)
//		   {
//		      return false;		   
//		   }
//		   return super.onKeyDown(keyCode, event);
//	}
}
