package com.example.project_nl.Providers;

import android.net.Uri;
import android.provider.BaseColumns;

public class DataContract {
	
	//table name
		public static final String TABLE = "DATA";
		
		//Columns name
		
		public static final String _ID = BaseColumns._ID,
							SPECIE = "specie",
							LONGITUDE = "longitude",
							LATITUDE = "latitude",
							PHOTO = "photo";
		
		//content URI
		public static Uri CONTENT_URI = Uri.withAppendedPath(DataProvider.CONTENT_URI, TABLE);

}
