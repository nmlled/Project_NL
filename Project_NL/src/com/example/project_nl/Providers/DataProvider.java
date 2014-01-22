package com.example.project_nl.Providers;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class DataProvider extends ContentProvider {

	private SQLiteOpenHelper helper;
	
	public static final String AUTHORITY = "com.example.project_nl.Providers.DataProvider";
	public static final Uri CONTENT_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY);
	
	private static UriMatcher URIMATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	private static final int DATA_ID = 1;
	private static final int DATA_ALL = 2;
	
	
	private static final String MIME_ALL ="vnd.android.cursor.dir/vnd.com.example.project_nl.Providers" + DataContract.TABLE; 
	private static final String MIME_ONE ="vnd.android.cursor.dir/vnd.com.example.project_nl.Providers" + DataContract.TABLE;
	
	static {
		URIMATCHER.addURI(AUTHORITY, DataContract.TABLE + "/#", DATA_ID);
		URIMATCHER.addURI(AUTHORITY, DataContract.TABLE, DATA_ALL);
	}
			
	@Override
	public boolean onCreate() {
		helper = new DataHelper(getContext());
		return true;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			return db.delete(DataContract.TABLE, selection, selectionArgs);
			
		} finally {
			db.close();
		}
	}

	@Override
	public String getType(Uri uri) {
		
		return URIMATCHER.match(uri) == DATA_ALL ? MIME_ALL : MIME_ONE;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			long row = db.insert(DataContract.TABLE, null, values);
			return (row == -1)? null: ContentUris.withAppendedId(uri, row);
			
		} finally {
			db.close();
		}
		
	}


	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		SQLiteDatabase db = helper.getReadableDatabase();
		return db.query(DataContract.TABLE, projection, selection, selectionArgs, null, null, sortOrder);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			return db.update(DataContract.TABLE, values, selection, selectionArgs);
			
		} finally {
			db.close();
		}
		
		
	}
	
	private class DataHelper extends SQLiteOpenHelper {

		
		
		public DataHelper(Context context) {
			super(context, "speciesData.db", null, 1);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String columns = DataContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
							DataContract.SPECIE +" TEXT NOT NULL, " +
							DataContract.LATITUDE +" REAL NOT NULL, " +
							DataContract.LONGITUDE +" REAL NOT NULL, " +
							DataContract.PHOTO +" BLOB NOT NULL"; 
			
			String sql = "CREATE TABLE IF NOT EXISTS " + DataContract.TABLE + " (" + columns + ")";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			//TODO: when needed
		}
		
	}


}
