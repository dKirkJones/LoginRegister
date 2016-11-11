package com.example.djones.theeshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewActivity extends AppCompatActivity {

    Store[] myStoreArray = new Store[]{
            new Store("Mama Ibukun","Igbo Elerin Road Okokomoiko Ojo Lagos","art"," Nice place"),
            new Store("Iya Nkechi","Ikorodu, Lagos, Nigeria","bike","Good service"),
            new Store("Babo Olowo","Okota, Oshodi-lsolo, Lagos, Nigeria","art","Loved it"),
            new Store("Hajia Halima","Ifako-ljaiye, Lagos, Nigeria","bike","Best prices"),
            new Store("Rehoboth Variety Store","Ikorodu, Lagos, Nigeria","art","Fast turnaround"),
            new Store("Mummy Kelechi Provision Store","Egbeda, Alimosho, Lagos, Nigeria","bike","Good food"),
            new Store("Weighton Nigera Company","Apapa Ajegunle, Ajeromi Ifelodun, Lagos","art","Nice"),
            new Store("Yassah's African Store","68 Central Avenue, Albany, NY 12206","bike","Big selection"),
            new Store("Odua African Market","700 Washington Avenue, Brooklyn, NY 10467","art","Good place to shop"),
            new Store("Osa Adolor African Market","774 Lenox Road, Brooklyn, NY 10467","bike","New business"),
            new Store("Ethnix Tribal & African Arts","636 Broadway Ste 202, New Yourk, NY 10467","bike","Friendly people"),
            new Store("Ecowas African Store","765 Springfield Ave, Irvington, NJ 07111","art","Authentic"),
            new Store("Adja Khady Food Distributor","243 West 116th Street, New Yourk, NY 10026","bike","Good"),
    };

    String[] myStringArray = new String[]{
            "Mama Ibuku",
            "Iya Nkechi",
            "Babo Olowo",
            "Hajia Halima",
            "Rehoboth Variety Store",
            "Mummy Kelechi Provision Store",
            "Weighton Nigera Company",
            "Yassa's African Store",
            "Odua African Market",
            "Osa Adolor African Market",
            "Ethnix Tribal & African Arts",
            "Ecowas African Store",
            "Adja Khady Food Distributor"

    };

    private ListView mListView;
    private StoreAdapter mAStoreAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        mListView = (ListView) findViewById(R.id.anotherListView);

       // mArrayAdapter = new StoreAdapter(this, android.R.layout.simple_list_item_1, myStoreArray);

        mAStoreAdapter = new StoreAdapter(getApplicationContext(), R.layout.row, myStoreArray );
        if(mListView != null){
            mListView.setAdapter(mAStoreAdapter);

        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("STORE", myStoreArray[position].mNameOfStore);

            }
        });

    }

}
