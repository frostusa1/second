package comfrostusa.sendposition;


import java.util.List;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private Criteria criteria;
	List<String> providers;
	String provider;
	LocationManager locationManager;
	LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText etLat = (EditText) findViewById(R.id.etLatitude);
        final EditText etLong = (EditText) findViewById(R.id.etLongitude);
        final Button btUpdate = (Button) findViewById(R.id.btUpdate);
        etLat.setText("Waiting..");
        etLong.setText("Waiting..");
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	etLat.setText("Clicked..");

            }
        });
        
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
       locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
              // Called when a new location is found by the network location provider.
              // makeUseOfNewLocation(location);
            	etLat.setText(String.valueOf(location.getLatitude()));
            	etLong.setText(String.valueOf(location.getLongitude()));
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}

			
          };

        // Register the listener with the Location Manager to receive location updates
          criteria = new Criteria();
          criteria.setAccuracy(Criteria.ACCURACY_FINE);
          providers = locationManager.getProviders(criteria, true);
          for (String provider : providers) {
              
             Log.d("Providers",provider);
          }   
          provider = locationManager.getBestProvider(criteria, true);
          locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
          
//       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
          
         
    }

    @Override
    protected void onResume() {
        super.onResume();
  	  criteria = new Criteria();
       criteria.setAccuracy(Criteria.ACCURACY_FINE);
       List<String> providers = locationManager.getProviders(criteria, true);
       for (String provider : providers) {
           
          Log.d("Providers","In Resume "+provider);
       }   
       String provider = locationManager.getBestProvider(criteria, true);
       locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
  }
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
      }
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
} 

