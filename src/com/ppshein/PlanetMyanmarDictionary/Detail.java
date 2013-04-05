package com.ppshein.PlanetMyanmarDictionary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Detail extends Activity {
	
	private ProgressBar progressBar;
	private SyncTask InstallData1;
	
	public static final String PREFS_NAME = "GetIDPref";    
	public static final String PREF_WORD = "PrefWord";
	public static final String PREF_LAST = "PrefLast";
	
	private static String NOWORD;
	private String getDefination;
	private String FadeButton;
	private ImageButton btnSetting;
	private static ImageView btnaddbookmark;	
	
	private SharedPreferences mPrefs;
	DatabaseUtil dbUtil = new DatabaseUtil(this);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		setContentView(R.layout.detail);
        
        final Context cont = getBaseContext();
        
        ViewGroup tabBar = (ViewGroup) findViewById(R.id.tabBar_ref);
		btnSetting = (ImageButton) tabBar.findViewById(R.id.imgBtnSetting);
		btnSetting.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {        		
        	    registerForContextMenu(btnSetting);
                v.showContextMenu();       		
        	}
        });	      
        
        mPrefs = getSharedPreferences(PREFS_NAME, 0);                       
        
        btnaddbookmark = (ImageView) findViewById(R.id.addbookmark);
        btnaddbookmark.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {        		
        		FadeButton = common.ProcessBookmark(mPrefs.getString(PREF_WORD, ""), cont);
                if (FadeButton.equals("fade")) {
                	btnaddbookmark.setImageResource(R.drawable.bookmarks);
                } else {
                	btnaddbookmark.setImageResource(R.drawable.nobookmarks);
                }        		
        	}
        });
        
        new DisplayMeaning().execute(mPrefs.getString(PREF_WORD, ""));
        
        /* check whether bookmark button will be disable or not */
        FadeButton = common.checkBookmark(mPrefs.getString(PREF_WORD, ""), cont);
        if (FadeButton.equals("fade")) {
        	btnaddbookmark.setImageResource(R.drawable.bookmarks);
        } else {
        	btnaddbookmark.setImageResource(R.drawable.nobookmarks);
        }
        
        progressBar = (ProgressBar) tabBar.findViewById(R.id.progressBar);
        if (common.isNetAvailable(this) == true) {
        	InstallData1 = new SyncTask(Detail.this, mPrefs.getString(PREF_LAST, ""), progressBar);
			InstallData1.execute(mPrefs.getString(PREF_LAST, ""));
			
            dbUtil.open(); 
        		Cursor cursor = dbUtil.getlastRecord();
           		Editor e = mPrefs.edit();
           			e.putString(PREF_LAST, cursor.getString(0));
        		e.commit();        		
        		cursor.close();
        	dbUtil.close();			
        }
        
    }
    
    private void goRelated() {    	
 		Intent intent = new Intent();
		intent.setClass(Detail.this, related.class);
		startActivity(intent);     	
    }
    
    protected class DisplayMeaning extends AsyncTask<String, Void, String> {
    	private ProgressDialog progressDialog;
        protected void onPreExecute() {
        	progressDialog = ProgressDialog.show(Detail.this, "", "Loading..."); 
        }

        protected String doInBackground(String... args) {        	                     
        	try {
   	         dbUtil.open();        
   	         	Cursor cursor = dbUtil.fetchDictionary(args[0]);
   	         		getDefination = cursor.getString(2);
   	         	cursor.close();
   	         dbUtil.close();
        	} catch (Exception e){
        		getDefination = "NULL";
        	}
        	
            return getDefination;
        }

        protected void onPostExecute(String getDefination) {
        	CreateDisplay(getDefination);
        	progressDialog.dismiss();
        }
    }    
    
    public void CreateDisplay (String getword) {
        NOWORD = getResources().getString(R.string.noword);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Zawgyi-One.ttf");
        
        TextView tv1 = (TextView)findViewById(R.id.textView1);
        TextView tv2 = (TextView)findViewById(R.id.textView2);
        TextView tv3 = (TextView)findViewById(R.id.textView3);
        Button relatedbtn = (Button)findViewById(R.id.relatedbtn);
        
 		if (getword.toString().equals("NULL")) {            
 			relatedbtn.setVisibility(View.VISIBLE);
            tv1.setText(mPrefs.getString(PREF_WORD, ""));
            tv1.setPadding(5, 5, 5, 5);
            tv2.setText(Html.fromHtml(NOWORD));
            tv2.setPadding(5, 5, 5, 5);
            tv2.setTypeface(font);
            tv3.setText("Click below button to search similar words");
			relatedbtn.setOnClickListener(new OnClickListener() {
	        	@Override
	        	public void onClick(View v) {        		
	        		goRelated();       		
	        	}
	        });            
            
 		} else {
 			relatedbtn.setVisibility(View.INVISIBLE);
            relatedbtn.setVisibility(View.GONE); 									
	        tv1.setText(mPrefs.getString(PREF_WORD, ""));
	        tv1.setPadding(5, 5, 5, 5);
	        tv2.setText(Html.fromHtml(getword));
	        tv2.setPadding(5, 5, 5, 5);
	        tv2.setTypeface(font);
	        tv3.setText("");
 		}     	
    }
    
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {	    	
			Intent intent = new Intent();
    		intent.setClass(Detail.this, MainActivity.class);
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
    		intent.setClass(Detail.this, MainActivity.class);
    		startActivity(intent);          
            return true;          
        case R.id.menu_bookmarks:
     		intent = new Intent();
    		intent.setClass(Detail.this, Bookmarks.class);
    		startActivity(intent);          
            return true;              
        case R.id.menu_about:
     		intent = new Intent();
    		intent.setClass(Detail.this, About.class);
    		startActivity(intent);	         
            return true;
        case R.id.menu_random:
     		intent = new Intent();
    		intent.setClass(Detail.this, RandomActivity.class);
    		startActivity(intent);	         
            return true;
        default:
            return super.onOptionsItemSelected(item);            
        }
	}
}
