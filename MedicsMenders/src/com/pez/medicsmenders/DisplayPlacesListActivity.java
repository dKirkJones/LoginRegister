package com.pez.medicsmenders;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class DisplayPlacesListActivity extends Activity {

    PlacesList nearPlaces;
	
    ListView lv;
	
    public static String KEY_REFERENCE = "reference"; 
    public static String KEY_NAME = "name"; 
    
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_places_list);
		
       Intent i = getIntent();
        
        nearPlaces = (PlacesList) i.getSerializableExtra("near_places");
        String target = (String)i.getStringExtra("target");
       
	    lv = (ListView) findViewById(R.id.list);
	    lv.setBackgroundColor(Color.WHITE);
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
	
        for (Place p : nearPlaces.results) {
        	
        		
        	if ((target.equals("hospital")) && (p.types.get(0).equals("hospital")))
        	{
        	      	
        			HashMap<String, String> map = new HashMap<String, String>();
         			map.put(KEY_REFERENCE, p.reference);
        			map.put(KEY_NAME, p.name);
        			placesListItems.add(map);
        	}
        	else if ((target.equals("pharmacy")) && (p.types.get(0).equals("pharmacy")))
        	{
    	      	
    			HashMap<String, String> map = new HashMap<String, String>();
    			map.put(KEY_REFERENCE, p.reference);
    			map.put(KEY_NAME, p.name);
    			placesListItems.add(map);
        	}
        	else if (target.equals("main"))
        	{
    	      	
    			HashMap<String, String> map = new HashMap<String, String>();
    			map.put(KEY_REFERENCE, p.reference);
    			map.put(KEY_NAME, p.name);
    			placesListItems.add(map);
        	}
        }
       
        ListAdapter adapter = new SimpleAdapter(DisplayPlacesListActivity.this, placesListItems,
                R.layout.list_item,
                new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
                        R.id.reference, R.id.name });
        lv.setAdapter(adapter);
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.display_places_list, menu);
		return true;
	}

}
