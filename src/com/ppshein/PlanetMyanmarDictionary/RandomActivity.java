package com.ppshein.PlanetMyanmarDictionary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class RandomActivity extends Activity {

	private TextView txtRandom;
	private ImageButton btnSetting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		setContentView(R.layout.activity_random);
		
		ViewGroup tabBar = (ViewGroup) findViewById(R.id.tabBar_ref);
		btnSetting = (ImageButton) tabBar.findViewById(R.id.imgBtnSetting);		
		
    	txtRandom = (TextView) findViewById(R.id.txtRandom);
    	txtRandom.setText(Html.fromHtml(common.getMessage()));	
    	
    	btnSetting.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        	    registerForContextMenu(btnSetting);
                v.showContextMenu();        		
        	}
        }); 
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {	    	
			Intent intent = new Intent();
    		intent.setClass(RandomActivity.this, MainActivity.class);
    		startActivity(intent);       		
	    }
	    return false;
	}	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	}

    @Override
	public boolean onContextItemSelected(MenuItem item) {
    	Intent intent;        
        switch (item.getItemId()) {
        case R.id.menu_home:
     		intent = new Intent();
    		intent.setClass(RandomActivity.this, MainActivity.class);
    		startActivity(intent);          
            return true;          
        case R.id.menu_bookmarks:
     		intent = new Intent();
    		intent.setClass(RandomActivity.this, Bookmarks.class);
    		startActivity(intent);          
            return true;              
        case R.id.menu_about:
     		intent = new Intent();
    		intent.setClass(RandomActivity.this, About.class);
    		startActivity(intent);	         
            return true;
        case R.id.menu_random:
     		intent = new Intent();
    		intent.setClass(RandomActivity.this, RandomActivity.class);
    		startActivity(intent);	         
            return true;
        default:
            return super.onOptionsItemSelected(item);            
        }
	}	
}
