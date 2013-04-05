package com.ppshein.PlanetMyanmarDictionary;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

 
public class related extends Activity {
    
	public static final String PREFS_NAME = "GetIDPref";    
	public static final String PREF_WORD = "PrefWord";	
	private SharedPreferences mPrefs;
	
	private String relatedword;
	private ListView Bookmarkslist;
	private TextView bookmarkTitle, infomsg;
	private ImageButton btnSetting;
	
	private static final String RELATEDWORD = "rword";
	
	DatabaseUtil dbUtil = new DatabaseUtil(this);
	private ArrayList <HashMap<String, Object>> getNews;	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		setContentView(R.layout.bookmarks);
		
        ViewGroup tabBar = (ViewGroup) findViewById(R.id.tabBar_ref);
		btnSetting = (ImageButton) tabBar.findViewById(R.id.imgBtnSetting);
		btnSetting.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {        		
        	    registerForContextMenu(btnSetting);
                v.showContextMenu();       		
        	}
        });		
        
		mPrefs = getSharedPreferences(PREFS_NAME, 0);
		getNews = new ArrayList<HashMap<String,Object>>();
		Bookmarkslist = (ListView) findViewById(R.id.mylist);
		
		if (mPrefs.getString(PREF_WORD, "").length() > 2) {
			relatedword = mPrefs.getString(PREF_WORD, "").substring(0, 3);
		} else {
			relatedword = mPrefs.getString(PREF_WORD, "");
		}  		
		
		new LoadRelatedLists().execute(relatedword);

	}
	
    public void populateArray(ArrayList<HashMap<String, Object>> getNews) {    	
    	Bookmarkslist.setAdapter(new myListAdapter(getNews,this));
    	Bookmarkslist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
    	bookmarkTitle = (TextView) findViewById(R.id.title);
        infomsg = (TextView) findViewById(R.id.infomsg);
        bookmarkTitle.setText("Similar words (" + getNews.size() + ")");

        if (getNews.size() == 0) {
        	infomsg.setText("We cannot find any similar word.");
        } else {
        	infomsg.setVisibility(View.GONE);
        }
        
        Bookmarkslist.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {        		
        		Editor e = mPrefs.edit();
        		@SuppressWarnings("unchecked")
        		
				HashMap<String, Object> o = (HashMap<String, Object>) Bookmarkslist.getItemAtPosition(position);       		        		        		
        		e.putString(PREF_WORD, o.get(RELATEDWORD).toString());
        		e.commit();        		
        		
	    		Intent intent = new Intent();
        		intent.setClass(related.this, Detail.class);
        		startActivity(intent);                 		
 			}
		});  		
    }	   
    
    private class myListAdapter extends BaseAdapter{    	
    	private ArrayList<HashMap<String, Object>> parseNews; 
    	private LayoutInflater mInflater;
    	    	
		public myListAdapter(ArrayList<HashMap<String, Object>> pNews, Context context){
			parseNews = pNews;
			mInflater = LayoutInflater.from(context);
		}    	
    	
    	@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return parseNews.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return parseNews.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			
			 if (convertView == null) {
	             convertView = mInflater.inflate(R.layout.bmarks, null);
	             holder = new ViewHolder();
	             holder.v = (TextView) convertView.findViewById(R.id.txtName);
	             holder.v1 = (ImageView) convertView.findViewById(R.id.img);
	             convertView.setTag(holder);
	                
			 }else {
				 // Get the ViewHolder back to get fast access to the TextView
	             // and the ImageView.
				 holder = (ViewHolder) convertView.getTag(); 
			 }
			 	// Bind the data with the holder.
			 
				holder.v.setText((String) parseNews.get(position).get(RELATEDWORD));
				holder.v.setPadding(2, 2, 2, 2);
				holder.v1.setImageResource(R.drawable.related_icon);
				return convertView;
		}
		
		class ViewHolder {
			TextView v;
			ImageView v1;
        }    	
    }
    
    protected class LoadRelatedLists extends AsyncTask<String, Void, ArrayList<HashMap<String, Object>>> {
    	private ProgressDialog progressDialog;
        protected void onPreExecute() {
        	progressDialog = ProgressDialog.show(related.this, "", "Loading..."); 
        }

        protected ArrayList<HashMap<String, Object>> doInBackground(String... args) {        	          
            getNews = new ArrayList<HashMap<String,Object>>();
        	try{
        		dbUtil.open();
        		HashMap<String, Object> hm;
        		Cursor cursor = dbUtil.fetchRelatedDictionary(args[0]);
        		if(cursor != null){			
        			while(cursor.moveToNext()){
        				hm = new HashMap<String, Object>();				
           		        hm.put(RELATEDWORD, cursor.getString(1));
           		        getNews.add(hm);
           			}			   	
        		   }
        		dbUtil.close();
            }
            catch(Exception e)        
            {
            	 Log.e("log_tag", "Error parsing data "+e.toString());
            }
            return getNews;
        }

        protected void onPostExecute(ArrayList<HashMap<String, Object>> getNews) {
        	populateArray(getNews);
        	progressDialog.dismiss();
        }
    }              

	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {	    	
			Intent intent = new Intent();
    		intent.setClass(related.this, MainActivity.class);
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
    		intent.setClass(related.this, MainActivity.class);
    		startActivity(intent);          
            return true;          
        case R.id.menu_bookmarks:
     		intent = new Intent();
    		intent.setClass(related.this, Bookmarks.class);
    		startActivity(intent);          
            return true;              
        case R.id.menu_about:
     		intent = new Intent();
    		intent.setClass(related.this, About.class);
    		startActivity(intent);	         
            return true;
        case R.id.menu_random:
     		intent = new Intent();
    		intent.setClass(related.this, RandomActivity.class);
    		startActivity(intent);	         
            return true;
        default:
            return super.onOptionsItemSelected(item);            
        }
	}	
}
