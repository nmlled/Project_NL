package com.example.project_nl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
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
	
	private TextView viewLatitude;
	private TextView viewLongitude;
	private EditText speciesName;
    private Button speciesGuarda;

    private LocationManager locationManager;
    private String bestProvider;
    
    private Location lastLocation = null;

    private void updateUI()
    {
		Log.i("Testes", "updateUI");
    	if(lastLocation == null)
    	{
	        viewLatitude.setText("Localização não disponível");
	        viewLongitude.setText("Localização não disponível");
    	}
    	else {
    		viewLatitude.setText(String.valueOf(lastLocation.getLatitude()));  
    		viewLongitude.setText(String.valueOf(lastLocation.getLongitude()));
    	}
    }
    
    private void updateBestLocationProvider()
    {
	    bestProvider = locationManager.getBestProvider(new Criteria(), false);
	    lastLocation = locationManager.getLastKnownLocation(bestProvider);
	    updateUI();
	    if (lastLocation != null)
	    	Log.i("Testes", "Provider " + bestProvider + " seleccionado.");
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		viewLatitude = (TextView) findViewById(R.id.viewLatitude);
		viewLongitude = (TextView) findViewById(R.id.viewLongitude);
		speciesName = (EditText) findViewById(R.id.editText1);
		speciesGuarda = (Button) findViewById(R.id.save);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		updateBestLocationProvider();
	    onLocationChanged(lastLocation);
		
		// - location button
	    findViewById(R.id.locationBtn).setOnClickListener(new OnClickListener() {                
	            @Override
	            public void onClick(View v) {
					Log.i("Testes", "Click Obter");
	            	// Obtain coordinates
					lastLocation = locationManager.getLastKnownLocation(bestProvider);
					updateUI();
	            }
	    });
	     
	    // photo button
        findViewById(R.id.photo).setOnClickListener(new OnClickListener() {
        	 @Override
	            public void onClick(View v) {
					Log.i("Testes", "Click Foto");
					startActivity(new Intent(MainActivity.this,CameraActivity.class));
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
		locationManager.requestLocationUpdates(bestProvider, 400, 1, this);
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
		Toast.makeText(this, "Location changed", Toast.LENGTH_LONG).show();
		Log.i("Testes", "Location changed");
		lastLocation = location;
		updateUI();
	}


	@Override
	public void onProviderDisabled(String provider) 
	{
		Toast.makeText(this, "Disabled provider " + provider,
		        Toast.LENGTH_LONG).show();
		updateBestLocationProvider();
	}


	@Override
	public void onProviderEnabled(String provider) 
	{
		Toast.makeText(this, "Enabled new provider " + provider,
		        Toast.LENGTH_LONG).show();
		updateBestLocationProvider();
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		// TODO Auto-generated method stub
	}	
}
