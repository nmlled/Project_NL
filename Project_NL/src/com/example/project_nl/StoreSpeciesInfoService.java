package com.example.project_nl;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

public class StoreSpeciesInfoService extends IntentService {
	
	public static final String STORE_ACTION_EXTRAS = "store.species.extras";
	
	public StoreSpeciesInfoService() 
	{
		super("StoreSpeciesInfoService");
	}

	@Override
	protected void onHandleIntent(Intent intent) 
	{
		if (intent != null) 
		{
			Species species = intent.getParcelableExtra(STORE_ACTION_EXTRAS);
			Log.i("Testes", species.getName());
		}
	}

}
