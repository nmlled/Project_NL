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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements LocationListener {
	
	//private Camera camera;
	private TextView viewLatitude;
	private TextView viewLongitude;
	private EditText speciesName;
    private LocationManager locationManager;
    private Button speciesGuarda;
    
    private Location lastLocation = null;

    private void updateUI()
    {
		viewLatitude.setText(String.valueOf(lastLocation.getLatitude()));  
		viewLongitude.setText(String.valueOf(lastLocation.getLongitude())); 
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		viewLatitude = (TextView) findViewById(R.id.viewLatitude);
		viewLongitude = (TextView) findViewById(R.id.viewLongitude);
		speciesName = (EditText) findViewById(R.id.editText1);
		speciesGuarda = (Button) findViewById(R.id.save);
		
		// - location button
	    findViewById(R.id.locationBtn).setOnClickListener(new OnClickListener() {                
	            @Override
	            public void onClick(View v) {
					Log.i("Testes", "Click Obter");
	            	// Obtain coordinates
					
					//List<String> providers = locationManager.getAllProviders();
					List<String> providers = locationManager.getProviders(true);
					
					for(String provider : providers)
						Log.i("Testes", provider);
					
					if(lastLocation == null)
					{
						lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		            	if(lastLocation != null)
		            		updateUI();
					}
	            }
	    });
	     
	    // photo button
        findViewById(R.id.photo).setOnClickListener(new OnClickListener() {
        	 @Override
	            public void onClick(View v) {
					Log.i("Testes", "Click Foto");
					boolean saveButtonEnabled = false;
					
					// TO DO:
					
					speciesGuarda.setClickable(saveButtonEnabled);
                }
        });
	
        // save button
        speciesGuarda.setOnClickListener(new OnClickListener() {
        	@Override
            public void onClick(View v) {
				Log.i("Testes", "Click Guardar");
				Log.i("Testes", speciesName.getText().toString());
        	}
        });
        
        speciesName.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_UP)
				{
					Log.i("Testes", "Key pressed");
				}
				return false;
			}
        });
        
        
        speciesGuarda.setClickable(false);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onLocationChanged(Location location) 
	{
		Log.i("Testes", "Location changed");
    	if(location != null)
    	{
    		lastLocation = location;
    		updateUI();
    	}
	}


	@Override
	public void onProviderDisabled(String provider) 
	{
		// TODO Auto-generated method stub	
	}


	@Override
	public void onProviderEnabled(String provider) 
	{
		// TODO Auto-generated method stub
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		// TODO Auto-generated method stub
	}	
}
