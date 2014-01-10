package com.example.project_nl;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;




public class MainActivity extends Activity {

	
	private Camera camera;
	private TextView coordinatesView;
    private LocationManager locationManager;
    //private boolean validCoordinates = false;

    private static int clickCount = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		coordinatesView = (TextView) findViewById(R.id.viewCoordinates);
		
		// - location button
	    findViewById(R.id.locationBtn).setOnClickListener(new OnClickListener() {                
	            @Override
	            public void onClick(View v) {
					Log.i("Testes", "Click");
					coordinatesView.setText("Click " + ++clickCount);
	            	// Obtain coordinates
					
					//List<String> providers = locationManager.getAllProviders();
					List<String> providers = locationManager.getProviders(true);
					
					
					for(String provider : providers)
						Log.i("Testes", provider);
					
	            	Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	            	if(loc != null)
	            		coordinatesView.setText(String.valueOf(loc.getLatitude()));
	            }
	    });
	    /*
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 5, 1, 
	    		new LocationListener() {

					@Override
					public void onLocationChanged(Location location) 
					{
						Log.i("Testes", "Location changed");
		            	if(location != null)
		            		coordinatesView.setText(String.valueOf(location.getLatitude()));
					}

					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// TODO Auto-generated method stub
						
					}});
		*/
	    
	     
	    // capture button
        findViewById(R.id.btn_take_photo).setOnClickListener(new OnClickListener() {
                       @Override
                       public void onClick(View v) {
                                camera.takePicture(null, null, new PictureCallback() {
                                        @Override
                                        public void onPictureTaken(byte[] data, Camera camera) {
                                                createPopup(data);
                                                camera.startPreview();
                                        }
                                });
                        }
                });
	    
	   
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
}
