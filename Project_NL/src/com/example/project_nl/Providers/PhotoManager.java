package com.example.project_nl.Providers;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Photo;

/**
 * This class manages the access to database table Photo.
 * 
 * @author Challenge.IT
 */
public class PhotoManager {

        private Context context;
        
        /**
         * Builds manager object.
         * @param android activity context
         */
        public PhotoManager(Context ctx){
                context = ctx;
        }
        
        /**
         * Stores in database table Photo a new image tuple
         * @param location 
         */
        public void save(com.example.project_nl.Model.Photo photo){
                ContentValues values = new ContentValues();
                values.put(PhotoContract.IMAGE, photo.getImage());
                values.put(PhotoContract.DESCRIPTION, photo.getDescription());
                values.put(PhotoContract.NAME, photo.getName());
                values.put(PhotoContract.CREATED_AT, photo.getCreated_at().getTime());
                context.getContentResolver().insert(PhotoProvider.CONTENT_URI, values);
        }
        
        /**
         * This method returns all photos saved persistently
         * @return list of photos saved in database
         */
/*        public ArrayList<Photo> getAll(){
                Cursor cursor = context.getContentResolver().query(PhotoProvider.CONTENT_URI, null, null, null, null);
                ArrayList<Photo> photos = new ArrayList<Photo>();
                //iterate all records and save then in array list
                while(cursor.moveToNext()){
                        photos.add(new Photo(
                                                                cursor.getBlob(cursor.getColumnIndex(PhotoContract.IMAGE)),
                                                                cursor.getString(cursor.getColumnIndex(PhotoContract.DESCRIPTION)),
                                                                cursor.getString(cursor.getColumnIndex(PhotoContract.NAME)),
                                                                Photo.longToDate(Long.parseLong(cursor.getString(cursor.getColumnIndex(PhotoContract.CREATED_AT))))
                                                                )
                                                );
                }
                cursor.close();
                return photos;
        }*/
}