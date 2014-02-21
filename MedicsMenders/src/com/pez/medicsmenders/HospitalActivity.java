package com.pez.medicsmenders;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HospitalActivity extends FragmentActivity {

    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    AlertDialogManager alert = new AlertDialogManager();
    GooglePlaces googlePlaces;
    PlacesList nearPlaces;
    GPSTracker gps;
    Button btnList;
    Button btnHospital;
    ProgressDialog pDialog;
    
    HashMap<Marker, String> referenceByMarker = new HashMap<Marker, String>();
     
    ListView lv;
    
    private GoogleMap googleMap;
    
    double latitude;
    double longitude;
     
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();
        
    public static String KEY_REFERENCE = "reference"; 
    public static String KEY_NAME = "name";
    public static String KEY_VICINITY = "vicinity"; 
	
    ListAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_hospital);
    	referenceByMarker = new HashMap<Marker, String>();
    	cd = new ConnectionDetector(getApplicationContext());
    	Intent i = getIntent();
    	nearPlaces = (PlacesList) i.getSerializableExtra("near_places");
    	try {
    		initilizeMap();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	googleMap
    	.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
    		@Override
    		public void onInfoWindowClick(Marker marker) {
    			Intent nextScreen = new Intent(HospitalActivity.this,
    					SinglePlaceActivity.class);
    			String reference = referenceByMarker.get(marker);
    			nextScreen.putExtra(KEY_REFERENCE, reference);
    			startActivity(nextScreen);
    		}
    	});
    	isInternetPresent = cd.isConnectingToInternet();
    	if (!isInternetPresent) {
    		alert.showAlertDialog(HospitalActivity.this, "Internet Connection Error",
    				"Please connect to working Internet connection", false);
    		return;
    	}
    	gps = new GPSTracker(this);
    	if (gps.canGetLocation()) {
    		Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
    	} else {
    		alert.showAlertDialog(HospitalActivity.this, "GPS Status",
    				"Couldn't get location information. Please enable GPS",
    				false);
    		return;
    	}
    	new LoadPlaces().execute();
    	lv = (ListView) findViewById(R.id.list);
    	Button btnHospital = (Button) findViewById(R.id.buttonHospital);
    	
    	btnHospital.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			Intent i = new Intent(HospitalActivity.this,
    					DisplayPlacesListActivity.class);
    			i.putExtra("near_places", nearPlaces);
    			i.putExtra("target", "hospital");
    			startActivity(i);
    		}
    	});
    	lv.setOnItemClickListener(new OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> parent, View view,
    				int position, long id) {
    			String reference = ((TextView) view.findViewById(R.id.reference)).getText().toString();
    			Intent in = new Intent(getApplicationContext(),
    					SinglePlaceActivity.class);
    			in.putExtra(KEY_REFERENCE, reference);
    			startActivity(in);
    		}
    	});
    }
    class LoadPlaces extends AsyncTask<String, String, String> {
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		pDialog = new ProgressDialog(HospitalActivity.this);
    		pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
    		pDialog.setIndeterminate(false);
    		pDialog.setCancelable(false);
    		pDialog.show();
    	}
    	protected String doInBackground(String... args) {
    		googlePlaces = new GooglePlaces();
    		try {
    			String types = "hospital"; 
    			double radius = 25000; 
    			nearPlaces = googlePlaces.search(gps.getLatitude(),
    					gps.getLongitude(), radius, types);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		return null;
    	}
    	protected void onPostExecute(String file_url) {
    		pDialog.dismiss();
    		runOnUiThread(new Runnable() {
    			public void run() {
    				String status = nearPlaces.status;
    				if(status.equals("OK")){
    					if (nearPlaces.results != null) {
    						for (Place p : nearPlaces.results) {
    							HashMap<String, String> map = new HashMap<String, String>();
    							map.put(KEY_REFERENCE, p.reference);
    							map.put(KEY_NAME, p.name);
    							placesListItems.add(map);
    						}
    					}
    				}
    				else if(status.equals("ZERO_RESULTS")){
    					alert.showAlertDialog(HospitalActivity.this, "Near Places",
    							"Sorry no places found. Try to change the types of places",
    							false);
    				}
    				else if(status.equals("UNKNOWN_ERROR"))
    				{
    					alert.showAlertDialog(HospitalActivity.this, "Places Error",
    							"Sorry unknown error occured.",
    							false);
    				}
    				else if(status.equals("OVER_QUERY_LIMIT"))
    				{
    					alert.showAlertDialog(HospitalActivity.this, "Places Error",
    							"Sorry query limit to google places is reached",
    							false);
    				}
    				else if(status.equals("REQUEST_DENIED"))
    				{
    					alert.showAlertDialog(HospitalActivity.this, "Places Error",
    							"Sorry error occured. Request is denied",
    							false);
    				}
    					else if(status.equals("INVALID_REQUEST"))
    					{
    						alert.showAlertDialog(HospitalActivity.this, "Places Error",
    								"Sorry error occured. Invalid Request",
    								false);
    					}
    					else
    					{
    						alert.showAlertDialog(HospitalActivity.this, "Places Error",
    								"Sorry error occured.",
    								false);
    					}
    			}
    		});
    		try {
    			initilizeMap();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		double lat;
    		double lng;
    		lat = gps.getLatitude();
    		lng = gps.getLongitude();
    		MarkerOptions markerC = new MarkerOptions().position(new LatLng(lat,lng));
    		markerC.icon(BitmapDescriptorFactory.fromResource(R.drawable.greyhollowballoon96));
    		googleMap.addMarker(markerC);
    		CameraPosition cameraPosition = new CameraPosition.Builder().target(
    				new LatLng(lat,lng)).zoom(11).build();
    		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    		googleMap.getUiSettings().setMyLocationButtonEnabled(false);
    		if (nearPlaces.results != null) {
    			for (Place place : nearPlaces.results) {
    				latitude = place.geometry.location.lat; 
    				longitude = place.geometry.location.lng; 
    				MarkerOptions markerP = new MarkerOptions().position(new LatLng(latitude, longitude)).title(place.name + ", " + place.vicinity);
    				markerP.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital));
    				Marker marker = googleMap.addMarker(markerP);
    				referenceByMarker.put(marker, place.reference);
    			}
    		}
    	}   
    }
    private void initilizeMap() {
    	if (googleMap == null) {
    		googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
    				R.id.map)).getMap();
    		if (googleMap == null) {
    			Toast.makeText(getApplicationContext(),
    					"Sorry! unable to create maps", Toast.LENGTH_SHORT)
    					.show();
    		}
    		    	}
    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.hospital, menu);
    	return true;
    }
	}
