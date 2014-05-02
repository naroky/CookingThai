package com.naroky.cookingthai;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends Activity {
	static final int PROGRESS_DIALOG = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		TextView txtAppName = (TextView) findViewById(R.id.txtAppName);
		txtAppName.setText("Cable Series");
		
		TextView txtDevName = (TextView) findViewById(R.id.txtDevName);
		txtDevName.setText("Naroky dot com");
		
		TextView txtVersion = (TextView) findViewById(R.id.txtVersion);
		txtVersion.setText("0.1.0");
		
		TextView txtSupport = (TextView) findViewById(R.id.txtSupport);
		txtSupport.setText("naroky@live.com");

		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void onBackPressed() 
	{
		startActivity(new Intent(this, MainActivity.class));
		 finish();
	}	
	public boolean onOptionsItemSelected(MenuItem item) {
		// respond to menu item selection

		switch (item.getItemId()) {
		case android.R.id.home:            
			 startActivity(new Intent(this, MainActivity.class));           
			 finish();
	         return true; 
		case R.id.action_about:
			startActivity(new Intent(this, AboutActivity.class));
			finish();
			return true;
		case R.id.action_help:
			startActivity(new Intent(this, HelpActivity.class));
			finish();
			return true;

		case R.id.action_exit:

			finish();  
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}	
}
