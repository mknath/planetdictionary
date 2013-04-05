package com.ppshein.PlanetMyanmarDictionary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SyncTask extends AsyncTask<String, Void, String> {
	private Context context;
	private ProgressBar progress;

    public SyncTask(Context context, String agrs, ProgressBar progress) {
        this.context = context;
        this.progress = progress;
    }    	
	
    protected void onPreExecute() {
    	progress.setVisibility(View.VISIBLE);
    }
    
    @Override
    protected String doInBackground(String... urls) {
    	int i = 0;
    	String resultMessage = null;
    	DatabaseUtil dbUtil = new DatabaseUtil(context);
    	try {
    		progress.setVisibility(View.VISIBLE);
        	JSONObject getson = common.getJSONfromURL("", urls[0]);
        	JSONArray getjsonobj;
        	getjsonobj = getson.getJSONArray("dictionary");
			for (int j = 0; j < getjsonobj.length(); j++) {
				JSONObject e = getjsonobj.getJSONObject(i);
				resultMessage = e.getString("newwords");
            }        	
    		
        	if (!resultMessage.equals("0")) {
            	JSONObject json = common.getJSONfromURL("", urls[0]);
            	JSONArray  newsjson = json.getJSONArray("dictionary");
            	dbUtil.open();
                for (i = 0; i < newsjson.length(); i++) {                
        				JSONObject e = newsjson.getJSONObject(i);
        				dbUtil.insertRecord(e.getString("dword"), e.getString("dmessage"));                    
                }
                dbUtil.close();
        	}			
    		

        } catch (JSONException e) {
            e.printStackTrace();                
        }
        return i + "";
    }
    
    protected void onPostExecute(final String result) {
    	progress.setVisibility(View.INVISIBLE);
    	if (!result.equals("0")) {
    		Toast toast = Toast.makeText(context, result + " new words updated.", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
    	}
    }
}
