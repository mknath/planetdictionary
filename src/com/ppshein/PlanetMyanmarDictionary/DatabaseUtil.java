package com.ppshein.PlanetMyanmarDictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseUtil{

	private static final String TAG = "DatabaseUtil";
	private static final String DATABASE_NAME = "dictionary_database.db";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TABLE = "tb_dictionary";
	private static final String BOOKMARK_TABLE = "tb_bookmark";
	public static final String KEY_WORD = "search_word";
	public static final String KEY_DEF = "search_defination";
	public static final String KEY_ROWID = "search_id";
	public static final String KEY_ROW = "_id";
	public static final String B_ID = "_id";
	public static final String B_WORD = "bookmark_word";
	public static final String AlterSQL = "";

	private final Context myContext;

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static class DatabaseHelper extends SQLiteOpenHelper {		

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

		}		
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion);
		}
	}	
	
	public DatabaseUtil(Context ctx) {
		this.myContext = ctx;
	}	
	
	public DatabaseUtil open() throws SQLException {
		mDbHelper = new DatabaseHelper(myContext);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	
	public long insertRecord (String dicword, String dicmeaning) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_WORD, dicword);
		initialValues.put(KEY_DEF, dicmeaning);
		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}
	
	
	public void close() {
		mDbHelper.close();
	}

	public Cursor fetchSuggestWord() {
		Cursor mCursor =
				mDb.rawQuery("SELECT search_id as _id, search_word from tb_dictionary order by search_word asc limit 10", null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;		
	}	
	
	public long insertBookmark(String mybookmark) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(B_WORD, mybookmark);		
		return mDb.insert(BOOKMARK_TABLE, null, initialValues);
	}

	public Cursor getlastRecord() throws SQLException {
		Cursor mCursor =
				mDb.rawQuery("SELECT search_id from tb_dictionary order by search_id desc limit 1", null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;		
	}	
	
	public boolean deleteBookmark(String bmword) {
		return mDb.delete(BOOKMARK_TABLE, B_WORD + "='" + bmword + "'", null) > 0;
	}

	public Cursor fetchAllBookmark() {
		return mDb.query(BOOKMARK_TABLE, new String[] {B_ID, B_WORD
				}, null, null, null, null, null);
	}
	
	public Cursor fetchRelatedDictionary(String searchword) {
		Cursor mCursor =
				mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
						KEY_WORD, KEY_DEF}, KEY_WORD + " LIKE '" + searchword + "%'", null,
						null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}	
	
	public Cursor fetchDictionary(String searchword) throws SQLException {
		Cursor mCursor =
				mDb.rawQuery("SELECT search_id, search_word, search_defination FROM tb_dictionary WHERE Lower(search_word) = '" + searchword + "'", null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public Cursor fetchBookmark(String searchword) throws SQLException {
		Cursor bCursor =
			mDb.query(true, BOOKMARK_TABLE, new String[] {B_ID,
					B_WORD}, B_WORD + "='" + searchword + "'", null,
					null, null, null, null);
		if (bCursor != null) {
			bCursor.moveToFirst();
		}
		return bCursor;
	}		
	
	public boolean updateDictionary(int id, String name, String standard) {
		ContentValues args = new ContentValues();
		args.put(KEY_WORD, name);
		args.put(KEY_DEF, standard);
		return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + id, null) > 0;
	}
	
	public Cursor fetchCountriesByName(String inputText) throws SQLException {
		  Cursor mCursor = null;
		  if (inputText == null || inputText.length () == 0)  {
			  mCursor = mDb.rawQuery("SELECT search_id as _id, search_word from tb_dictionary order by search_word asc limit 10", null);		 
		  }
		  else {
			  mCursor = mDb.rawQuery("SELECT search_id as _id, search_word from tb_dictionary where search_word like '" + inputText + "%' order by search_word asc limit 10", null);			  
		  }
		  if (mCursor != null) {
			  mCursor.moveToFirst();
		  }
		  return mCursor;
	 }	
}