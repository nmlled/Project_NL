package com.example.project_nl.Providers;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This class defines the contract to the photo table.
 * Contains table name, columns and content URI for access via content resolver in Android.
 * 
 * @author Challenge.IT
 * @see ContentResolver 
 */
public class PhotoContract {

        // Table name.
        public static final String TABLE = "PHOTO";
        
        // Columns names.
        public static final String _ID          = BaseColumns._ID,
                                                           IMAGE = "image",
                                                           NAME = "name",
                                                           DESCRIPTION = "description",
                                                           CREATED_AT = "created_at";
        
        // Content URI for subset of provided data from location provider.
        public static Uri CONTENT_URI = Uri.withAppendedPath(PhotoProvider.CONTENT_URI, TABLE);
}
