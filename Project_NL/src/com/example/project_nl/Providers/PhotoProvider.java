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

/**
 * Location content provider.
 * 
 * @author Challange.IT
 * @see ContentProvider
 * */
public class PhotoProvider extends ContentProvider
{
        // The authority that identifies this content provider in Android.
        public static final String AUTHORITY = "pt.challenge_it.camera_android.providers.photoprovider";        
        // The content: scheme identifies the URI as a content URI pointing to an Android content provider.
        public static final Uri CONTENT_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY);        
        
        // Matcher for see if the type is one element or all elements.
        private static UriMatcher URIMATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        private static final int LOCATION_ID  = 1;
        private static final int LOCATION_ALL = 2;
        
        private static final String MIME_ALL = "vnd.android.cursor.dir/vnd.pt.challenge_it.camera_android.providers." + PhotoContract.TABLE;
        private static final String MIME_ONE = "vnd.android.cursor.item/vnd.pt.challenge_it.camera_android.providers." + PhotoContract.TABLE;
        
        // DB Helper instance for access to SQLite DB.
        private SQLiteOpenHelper helper;
        
        static 
        {
                URIMATCHER.addURI(AUTHORITY, PhotoContract.TABLE+"/#", LOCATION_ID);
                URIMATCHER.addURI(AUTHORITY, PhotoContract.TABLE, LOCATION_ALL);
        }
                
        @Override
        public boolean onCreate() 
        {
                helper = new LocationHelper(getContext());
                return true;
        }
        
        @Override
        public String getType(Uri uri) 
        {
                return URIMATCHER.match(uri) == LOCATION_ALL ? MIME_ALL : MIME_ONE;
        }
        
        @Override
        public Uri insert(Uri uri, ContentValues values)
        {
                SQLiteDatabase db = helper.getWritableDatabase();
                try
                {
                        long row = db.insert(PhotoContract.TABLE, null, values);
                        return (row == -1)? null : ContentUris.withAppendedId(uri, row);
                }
                finally
                {
                        db.close();
                }
        }
        
        @Override
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
        {
                SQLiteDatabase db = helper.getReadableDatabase();
                return db.query(PhotoContract.TABLE, projection, selection, selectionArgs, null, null, sortOrder);
        }
        
        @Override
        public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
        {
                SQLiteDatabase db = helper.getWritableDatabase();
                try
                {
                        return db.update(PhotoContract.TABLE, values, selection, selectionArgs);
                }
                finally
                {
                        db.close();
                }
        }
        
        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) 
        {
                SQLiteDatabase db = helper.getWritableDatabase();
                try
                {
                        return db.delete(PhotoContract.TABLE, selection, selectionArgs);
                }
                finally
                {
                        db.close();
                }
        }
        
        /**
         * DB helper for locations. The DB is an SQLite data base.
         * @author Challenge.IT
         * */
        private static class LocationHelper extends SQLiteOpenHelper
        {
                public LocationHelper(Context context) 
                {
                        // Don't need the cursor factory, so it's null.
                        super(context, "photos.db", null, 1);
                }
        
                @Override
                public void onCreate(SQLiteDatabase db)
                {
                        String columns = PhotoContract._ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                                                         PhotoContract.IMAGE + " BLOB NOT NULL, " +
                                                         PhotoContract.NAME + " TEXT NOT NULL, " +
                                                 PhotoContract.DESCRIPTION + " TEXT, " +
                                                 PhotoContract.CREATED_AT + " TEXT NOT NULL";
                        
                        String sql = "CREATE TABLE IF NOT EXISTS " + PhotoContract.TABLE + " (" + columns + ")";
                        db.execSQL(sql);
                }
        
                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
                {
                        //TODO: When needed, code for database upgrade.
                }
        }
}
