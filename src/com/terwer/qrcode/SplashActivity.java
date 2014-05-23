package com.terwer.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 宽高全屏显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_screen);
		
		
		//3秒后跳转到主界面
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this,
						MainActivity.class);
				SplashActivity.this.startActivity(intent);
				SplashActivity.this.finish();
				
			}
		}, 3000);
	}

}
