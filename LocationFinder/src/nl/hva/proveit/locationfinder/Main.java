package nl.hva.proveit.locationfinder;


import java.io.BufferedWriter;
import java.io.FileWriter;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements LocationListener {
	  LocationManager locationManager ;
	  LocationManager locationManagerGPS ;
	  String provider;
	  String providerGPS;
	  String uid;
	  LocationListener locationListener = new GPSLocationListener();
	  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlayout);
		 // Getting LocationManager object
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManagerGPS = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        
    
        // Getting the name of the provider that meets the criteria
        provider = LocationManager.NETWORK_PROVIDER;
        providerGPS = LocationManager.GPS_PROVIDER;
        uid = tManager.getDeviceId();
        
 
            // Get the location from Network (Wifi /3G)
            Location location = locationManager.getLastKnownLocation(provider);
 
            locationManager.requestLocationUpdates(provider, 1000, 1, this);
 
            if(location!=null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
 
            // Get the location from GPS
            Location locationGPS = locationManagerGPS.getLastKnownLocation(providerGPS);
 
            locationManager.requestLocationUpdates(providerGPS, 1000, 1, this.locationListener);
 
            if(location!=null)
                onLocationChanged(locationGPS);
            else
                Toast.makeText(getBaseContext(), "GPS Location can't be retrieved", Toast.LENGTH_SHORT).show();
   
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainlayout, menu);
		return true;
	}
	
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	public void onLocationChanged(Location location) {

    // Getting reference to TextView tv_longitude
    TextView tvLongitude = (TextView)findViewById(R.id.tv_longitude);

    // Getting reference to TextView tv_latitude
    TextView tvLatitude = (TextView)findViewById(R.id.tv_latitude);

    // Setting Current Longitude
    tvLongitude.setText("Longitude:" + location.getLongitude());

    // Setting Current Latitude
    tvLatitude.setText("Latitude:" + location.getLatitude() );
    FileWriter fWriter2;
    try{
    	
         fWriter2 = new FileWriter("/sdcard/3GLog.txt",true);
         BufferedWriter out2 = new BufferedWriter(fWriter2);
         out2.write(uid+","+System.currentTimeMillis()+","+location.getLongitude() +","+location.getLatitude()+"\r\n");
         out2.close();
     }catch(Exception e){
              e.printStackTrace();
     }
	
	}

	

	private final class GPSLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location locationGPS) {
            // called when the listener is notified with a location update from the GPS
        	// Getting reference to TextView tv_longitude
            TextView tvLongitudeGPS = (TextView)findViewById(R.id.tv_longitudeGPS);

            // Getting reference to TextView tv_latitude
            TextView tvLatitudeGPS= (TextView)findViewById(R.id.tv_latitudeGPS);

            // Setting Current Longitude
            tvLongitudeGPS.setText("Longitude:" + locationGPS.getLongitude());

            // Setting Current Latitude
            tvLatitudeGPS.setText("Latitude:" + locationGPS.getLatitude() );
            FileWriter fWriter;
            try{
                 fWriter = new FileWriter("/sdcard/GPSLog.txt",true);
                 BufferedWriter out = new BufferedWriter(fWriter);
                 out.write(uid+","+System.currentTimeMillis()+","+locationGPS.getLongitude() +","+locationGPS.getLatitude()+"\r\n");
        
                 out.close();
             }catch(Exception e){
                      e.printStackTrace();
             }
        }

        @Override
        public void onProviderDisabled(String providerGPS) {
           // called when the GPS provider is turned off (user turning off the GPS on the phone)
        }

        @Override
        public void onProviderEnabled(String providerGPS) {
           // called when the GPS provider is turned on (user turning on the GPS on the phone)
        }

        @Override
        public void onStatusChanged(String providerGPS, int status, Bundle extras) {
           // called when the status of the GPS provider changes
        }
}

    

}
