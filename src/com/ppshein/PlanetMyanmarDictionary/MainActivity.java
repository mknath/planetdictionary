package com.ppshein.PlanetMyanmarDictionary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.view.Gravity;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

@SuppressLint({"DefaultLocale" }) 
public class MainActivity extends Activity {
    
	private DatabaseUtil dbHelper;
	private ImageButton btn1, btnSetting;
	private EditText ed1;
	private ProgressBar progressBar;
	private SyncTask InstallData1;
	private ImageView imgLogo;
	private TextView txtLogo;

	public static final String PREFS_NAME = "GetIDPref";    
	public static final String PREF_WORD = "PrefWord";
	public static final String PREF_LAST = "PrefLast";	
	
	private ListView listView;
	private SimpleCursorAdapter dataAdapter;
	
    private SharedPreferences mPrefs;
    DatabaseUtil dbUtil = new DatabaseUtil(this);
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		setContentView(R.layout.main);
		
        ViewGroup tabBar = (ViewGroup) findViewById(R.id.tabBar_ref);
		btnSetting = (ImageButton) tabBar.findViewById(R.id.imgBtnSetting);		
		btnSetting.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {        		
        	    registerForContextMenu(btnSetting);
                v.showContextMenu();        		       		
        	}
        });	
		
        ed1 = (EditText) findViewById(R.id.searchWord);
        btn1 = (ImageButton) findViewById(R.id.searchButton);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        txtLogo = (TextView) findViewById(R.id.txtLogo);
        
        
        mPrefs = getSharedPreferences(PREFS_NAME, 0);        
        
        progressBar = (ProgressBar) tabBar.findViewById(R.id.progressBar);
        if (common.isNetAvailable(this) == true) {
        	InstallData1 = new SyncTask(MainActivity.this, mPrefs.getString(PREF_LAST, ""), progressBar);
			InstallData1.execute(mPrefs.getString(PREF_LAST, ""));
			
            dbUtil.open(); 
        		Cursor cursor = dbUtil.getlastRecord();
           		Editor e = mPrefs.edit();
           			e.putString(PREF_LAST, cursor.getString(0));
        		e.commit();        		
        		cursor.close();
        	dbUtil.close();			
        }
        
        btn1.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		if (ed1.getText().toString().equals("")) {
        			Toast toast = Toast.makeText(MainActivity.this, "Type any word to search", Toast.LENGTH_SHORT);
    				toast.setGravity(Gravity.CENTER, 0, 0);
    				toast.show();        			
        		} else {
               		Editor e = mPrefs.edit();
            		e.putString(PREF_WORD, ed1.getText().toString().toLowerCase().replace(" ", ""));
            		e.commit();
            		
             		Intent intent = new Intent();
            		intent.setClass(MainActivity.this, Detail.class);
            		startActivity(intent);              		
        		}
        	}
        });
        
  	  dbHelper = new DatabaseUtil(this);
  	  dbHelper.open();
  	  
  	  displayListView();
  	  
    }
             
    private void displayListView() {
		 Cursor cursor = dbHelper.fetchSuggestWord();
		 
		  String[] columns = new String[] {
				  DatabaseUtil.KEY_WORD
		  };
		 
		  int[] to = new int[] {
		    R.id.txtWord
		  };
		 
		  dataAdapter = new SimpleCursorAdapter(
		    this, R.layout.word_suggest,
		    cursor,
		    columns,
		    to,
		    0);
		 
		  listView = (ListView) findViewById(R.id.listView);
		  listView.setAdapter(dataAdapter);
		  listView.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
				  Cursor cursor = (Cursor) listView.getItemAtPosition(position);
				  String countryCode = cursor.getString(cursor.getColumnIndexOrThrow("search_word"));
				  Editor e = mPrefs.edit();
				  e.putString(PREF_WORD, countryCode.toLowerCase().replace(" ", ""));
				  e.commit();
					          		
				  Intent intent = new Intent();
				  intent.setClass(MainActivity.this, Detail.class);
				  startActivity(intent); 				  
			  }
		  });
		 		  
		  ed1.addTextChangedListener(new TextWatcher() {
			  public void afterTextChanged(Editable s) {
			  }
		 
			  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			  }
		 
			  public void onTextChanged(CharSequence s, int start, int before, int count) {
				  if (!s.toString().equals("")) {
					  listView.setVisibility(View.VISIBLE);
					  txtLogo.setVisibility(View.INVISIBLE);
					  txtLogo.setVisibility(View.GONE);
					  imgLogo.setVisibility(View.INVISIBLE);
					  imgLogo.setVisibility(View.GONE);
				  } else {
					  listView.setVisibility(View.INVISIBLE);
					  txtLogo.setVisibility(View.VISIBLE);					  
					  imgLogo.setVisibility(View.VISIBLE);					  					 
				  }
				  dataAdapter.getFilter().filter(s.toString());
			  }
		  });		  
		   
		  dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
		         public Cursor runQuery(CharSequence constraint) {
		             return dbHelper.fetchCountriesByName(constraint.toString());
		         }
		  });		 
	 }    
    
    protected void onResume()
    {
       super.onResume();
       ed1.setText("");
    }           
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {	    	
     		AlertDialog.Builder alt_setting = new AlertDialog.Builder(MainActivity.this);
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
    		intent.setClass(MainActivity.this, MainActivity.class);
    		startActivity(intent);          
            return true;          
        case R.id.menu_bookmarks:
     		intent = new Intent();
    		intent.setClass(MainActivity.this, Bookmarks.class);
    		startActivity(intent);          
            return true;              
        case R.id.menu_about:
     		intent = new Intent();
    		intent.setClass(MainActivity.this, About.class);
    		startActivity(intent);	         
            return true;
        case R.id.menu_random:
     		intent = new Intent();
    		intent.setClass(MainActivity.this, RandomActivity.class);
    		startActivity(intent);	         
            return true;
        default:
            return super.onOptionsItemSelected(item);            
        }
	}
}
