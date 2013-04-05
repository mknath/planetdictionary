package com.ppshein.PlanetMyanmarDictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class About extends Activity {
	private TextView txt1;
	private String aboutMessage;
	private ImageButton btnSetting;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		setContentView(R.layout.about);
        
        ViewGroup tabBar = (ViewGroup) findViewById(R.id.tabBar_ref);
		btnSetting = (ImageButton) tabBar.findViewById(R.id.imgBtnSetting);
		btnSetting.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {        		
        	    registerForContextMenu(btnSetting);
                v.showContextMenu();
        	}
        });			
		
        txt1 = (TextView) findViewById(R.id.textView1);        
        aboutMessage = getResources().getString(R.string.about);
        txt1 = (TextView) findViewById(R.id.textView1);
        txt1.setText(Html.fromHtml(aboutMessage));                
    }

	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {	    	
			Intent intent = new Intent();
    		intent.setClass(About.this, MainActivity.class);
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
    		intent.setClass(About.this, MainActivity.class);
    		startActivity(intent);          
            return true;          
        case R.id.menu_bookmarks:
     		intent = new Intent();
    		intent.setClass(About.this, Bookmarks.class);
    		startActivity(intent);          
            return true;              
        case R.id.menu_about:
     		intent = new Intent();
    		intent.setClass(About.this, About.class);
    		startActivity(intent);	         
            return true;
        case R.id.menu_random:
     		intent = new Intent();
    		intent.setClass(About.this, RandomActivity.class);
    		startActivity(intent);	         
            return true;
        default:
            return super.onOptionsItemSelected(item);            
        }
	}
}
