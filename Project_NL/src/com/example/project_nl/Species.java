package com.example.project_nl;

import java.util.*;
import android.location.*;
import android.os.Parcel;
import android.os.Parcelable;

public class Species implements Parcelable {
	
	public static final Parcelable.Creator<Species> CREATOR = new Parcelable.Creator<Species>() {

		@Override
		public Species createFromParcel(Parcel source) 
		{
			return new Species(source);
		}

		@Override
		public Species[] newArray(int size) 
		{
			return new Species[size];
		}
		
	};
	
	private static final String EMPTY_STRING = "";
	
	private final String name;
	private String photoName;
	private final LinkedList<Location> locations;
	
	private Species(Parcel in)
	{
		name = in.readString();
		photoName = in.readString();
		locations = new LinkedList<Location>();
		
		// TODO: Obtain Location's list
		// Parcelable[] o = in.readParcelableArray(ClassLoader.getSystemClassLoader());
	}
	
	@Override
	public int describeContents() { return 0; }

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(name);
		dest.writeString(photoName);
		/*
		Location[] locs = new Location[locations.size()];
		locations.toArray(locs);
		dest.writeParcelableArray(locs, 0);
		*/
	}

	public Species(String name)
	{
		this(name, EMPTY_STRING);
	}
	
	public Species(String name, String photo)
	{
		this.name = name;
		locations = new LinkedList<Location>();
		photoName = photo;
	}
	
	public void appendLocation(Location location)
	{
		locations.add(location);
	}

	public String getName()
	{
		return name;
	}
	
	public String getPhotoName()
	{
		return photoName;
	}
	
	public void setPhotoName(String name)
	{
		photoName = name;
	}
	
	public Iterable<Location> getLocations()
	{
		return locations;
	}
}
