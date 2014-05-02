package com.naroky.cookingthai;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HelpActivity extends Activity {
	static final int PROGRESS_DIALOG = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		String text = "\n" + "\n"
				+ "1. เมื่อเปิด โปรแกรมจะเห็น ช่องกรอกความฝัน ปุ่ม "
				+ "ค้นหา และ รายการ ความฝัน ในหมวด ก. " + "\n"
				+ "2. ให้ใส่สิ่งที่เห็น และใน ความฝันของคุณ 1 คำ (ให้ "
				+ "คิดถึงสิ่งที่ เห็น) \n"
				+ "3. กดปุ่มค้นหา รอสักครู่  โปรแกรมจะแสดงข้อมูลที่ "
				+ "ใกล้ ในส่งที่ฝันและใกล้เคียง \n"
				+ "4. เลือกความฝันที่ใกล้เคียง "
				+ "เห็นข้อมูลรายละเอียดของ ความฝัน\n";
		TextView txthelp = (TextView) findViewById(R.id.txthelp);
		txthelp.setText(text);
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
