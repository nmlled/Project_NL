package com.example.project_nl;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;


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
					//startActivity(new Intent(MainActivity.this,MainActivity2.class));
                }
        });
	
        // save button
        speciesGuarda.setOnClickListener(new OnClickListener() {
        	@Override
            public void onClick(View v) {
				Log.i("Testes", "Click Guardar");
				Log.i("Testes", speciesName.getText().toString());
				
				Species species = new Species(speciesName.getText().toString().trim());
				if(lastLocation != null)
					species.appendLocation(lastLocation);
				
				// Send info to service
				Intent intent = new Intent(MainActivity.this, StoreSpeciesInfoService.class); 
				intent.putExtra(StoreSpeciesInfoService.STORE_ACTION_EXTRAS, species); 
				startService(intent);
        	}
        });
        
        speciesName.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_UP)
				{
					Log.i("Testes", "Key UP");
					boolean saveButtonEnabled = false;
					String content = speciesName.getText().toString();
					saveButtonEnabled = !content.trim().equals("");
					speciesGuarda.setClickable(saveButtonEnabled);
				}
				return false;
			}
        });
        
        
        speciesGuarda.setClickable(false);
	}
	
    @Override
    protected void onResume()
    {
		super.onResume();
		// start location updates requests
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			Log.i("Testes", "Registered Listener");
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 1000 * 10, 1, this);
		}
    }
    
    @Override
    protected void onPause()
    {
		super.onPause();
		// stop requests when activity is paused
		locationManager.removeUpdates(this);
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
		Toast.makeText(this, "Location", Toast.LENGTH_LONG).show();
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
