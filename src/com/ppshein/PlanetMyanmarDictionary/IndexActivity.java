package com.ppshein.PlanetMyanmarDictionary;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class IndexActivity extends Activity {

	private AdView adView;
	
    private static final String DB_PATH = "/data/data/com.ppshein.PlanetMyanmarDictionary/databases/";        
    private SharedPreferences mPrefs;
	public static final String PREFS_NAME = "GetIDPref";    
	public static final String PREF_LAST = "PrefLast";
	private TextView txtRandom;
	private ImageButton btnSetting;
	
    private String getZipInfo;
    DatabaseUtil dbUtil = new DatabaseUtil(this);	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		setContentView(R.layout.activity_index);
		
		mPrefs = getSharedPreferences(PREFS_NAME, 0);
		
	    adView = new AdView(this, AdSize.BANNER, "a1515a7ad157129");
	    LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
	    layout.addView(adView);
	    AdRequest adRequest = new AdRequest();
	    adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
	    adView.loadAd(adRequest);		
		
		ViewGroup tabBar = (ViewGroup) findViewById(R.id.tabBar_ref);
		btnSetting = (ImageButton) tabBar.findViewById(R.id.imgBtnSetting);		
		
		boolean dbExist = common.checkDBExist(DB_PATH + "dictionary_database.db");
        if(!dbExist){
        	try {
    			dbUtil.open();        
    			Cursor cursor = dbUtil.fetchBookmark("FAKE");
    			String fakeCreatePATH1 = cursor.getString(1);
    			cursor.close();
    			dbUtil.close();
    			if (fakeCreatePATH1.toString() != "") {
    								
    			}
    		} catch (Exception e){
    			
    		}
    		new UnzipInfo().execute("dictionary_database.zip");
        } else {
            dbUtil.open(); 
        		Cursor cursor = dbUtil.getlastRecord();
           		Editor e = mPrefs.edit();
           			e.putString(PREF_LAST, cursor.getString(0));
        		e.commit();        		
        		cursor.close();
        	dbUtil.close();        	        
        	
        	txtRandom = (TextView) findViewById(R.id.txtRandom);
        	txtRandom.setText(Html.fromHtml(common.getMessage()));
        	
        	btnSetting.setImageResource(R.drawable.search_icon);
        	btnSetting.setOnClickListener(new OnClickListener() {
            	@Override
            	public void onClick(View v) {
             		Intent intent = new Intent();
            		intent.setClass(IndexActivity.this, MainActivity.class);
            		startActivity(intent);
            	}
            }); 
        	
        	myTimer.start();
        }		
	}
	
	  @Override
	  public void onDestroy() {
	    // Destroy the AdView.
	    if (adView != null) {
	      adView.destroy();
	    }
	
	    super.onDestroy();
	  }	
	
	private CountDownTimer myTimer = new CountDownTimer(3000, 1000) {
	    public void onFinish() {
     		//Intent intent = new Intent();
    		//intent.setClass(IndexActivity.this, MainActivity.class);
    		//startActivity(intent);
	    }
		@Override
		public void onTick(long arg0) {
			
		}
	};
	
    private class UnzipInfo extends AsyncTask<String, Void, String> {
    	private ProgressDialog progressDialog;
        
        // can use UI thread here
        protected void onPreExecute() {
           progressDialog = ProgressDialog.show(IndexActivity.this, "", "Installing data...");
        }
        // automatically done on worker thread (separate from UI thread)
        protected String doInBackground(final String... args) {
        	getZipInfo = unpackZip(args[0]);                    
        	return getZipInfo.toString();
        }
        // can use UI thread here
        protected void onPostExecute(final String result) {
			progressDialog.dismiss();
			
            dbUtil.open(); 
        		Cursor cursor = dbUtil.getlastRecord();
           		Editor e = mPrefs.edit();
           			e.putString(PREF_LAST, cursor.getString(0));
        		e.commit();        		
        		cursor.close();
        	dbUtil.close();			
			
     		Intent intent = new Intent();
    		intent.setClass(IndexActivity.this, IndexActivity.class);
    		startActivity(intent);			
        }
     }	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	private String unpackZip(String ZIP_NAME)
	{
	     InputStream is;
	     ZipInputStream zis;
	     try 
	     {
	    	 is = getAssets().open("database/" + ZIP_NAME);
	         zis = new ZipInputStream(new BufferedInputStream(is));          
	         ZipEntry ze;

	         while ((ze = zis.getNextEntry()) != null) 
	         {
	             ByteArrayOutputStream baos = new ByteArrayOutputStream();
	             byte[] buffer = new byte[1024];
	             int count;

	             // zapis do souboru
	             String filename = ze.getName();
	             FileOutputStream fout = new FileOutputStream(DB_PATH + filename);

	             // cteni zipu a zapis
	             while ((count = zis.read(buffer)) != -1) 
	             {
	                 baos.write(buffer, 0, count);
	                 byte[] bytes = baos.toByteArray();
	                 fout.write(bytes);             
	                 baos.reset();
	             }

	             fout.close();               
	             zis.closeEntry();
	         }
	         zis.close();   		  
	     } 
	     catch(IOException e)
	     {
	    	 return "Installation failed. " + e.getMessage();
	     }
	     return "Installation completed. Enjoy it.";
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {	    	
     		AlertDialog.Builder alt_setting = new AlertDialog.Builder(IndexActivity.this);
    		alt_setting.setMessage("Do you want to exit?")
    		.setCancelable(false)
    		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				moveTaskToBack(true);
    			}
    		})
    		.setNegativeButton("No", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.cancel();
    			}
    		});
    		AlertDialog alert_setting = alt_setting.create();
    		alert_setting.show();
    		return true;
	    }
	    return false;
	}	

}
