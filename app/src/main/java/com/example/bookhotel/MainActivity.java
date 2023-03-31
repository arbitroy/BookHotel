package com.example.bookhotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.bookhotel.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView tophotels_pager;
    ArrayList<TopHotelsModel> top_hotels_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top_hotels_list = new ArrayList<>();
        top_hotels_list.add(new TopHotelsModel("Home INN", "Stay where you want, when you want, and get rewarded", "Flexible Prices", R.drawable.homeimage));
        top_hotels_list.add(new TopHotelsModel("Villa Rosa Kempinski", "In Westlands\n" +
                "Nairobi National Park - 23 min drive", "KSH 20,000", R.drawable.villarosakempinski2));
        top_hotels_list.add(new TopHotelsModel("Bamburi Beach Hotel", "Malindi Road,\n" +
                "Mombasa,\n" +
                "Kenya.", "Ksh 10,500", R.drawable.bamburibeachhotel));
        top_hotels_list.add(new TopHotelsModel("Boma Inn Nairobi", "Near Nairobi National Park", "Ksh 20,200", R.drawable.bomainnnairobi2));

        top_hotels_list.add(new TopHotelsModel("Nairobi Serena Hotel", "The convention centre, Near Uhuru Park", "Ksh 30,800", R.drawable.nairobiserenahotel3));
        top_hotels_list.add(new TopHotelsModel("Sankara Nairobi", "Luxury hotel in Westlands", "Ksh 20,500", R.drawable.sankaranairobi2));
        top_hotels_list.add(new TopHotelsModel("Tribe Hotel Nairobi", "Gigiri with outdoor pool and restaurant", "Ksh 20,500", R.drawable.tribehotel));
        top_hotels_list.add(new TopHotelsModel("The Sarova Stanley", "Luxury Nairobi hotel with full-service spa, connected to the convention centre", "Ksh 10,500", R.drawable.thesarovastanley));
        top_hotels_list.add(new TopHotelsModel("ibis Styles Nairobi Westlands", "3.5-star hotel with 2 restaurants, near Nairobi National Museum", "Ksh 17,500", R.drawable.ibisstyles));
        top_hotels_list.add(new TopHotelsModel("Four Points"+"\n"+"By Sheraton Nairobi Airport","4-star hotel in Embakasi with 2 restaurants and spa", "Ksh 21 ,500", R.drawable.fourpoints));

        tophotels_pager = findViewById(R.id.top_hotels_pager);

        TopHotelsAdapter adapter = new TopHotelsAdapter(this, top_hotels_list);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        MyDecoration decoration = new MyDecoration(30);
        tophotels_pager.setLayoutManager(manager);
        tophotels_pager.setAdapter(adapter);
        tophotels_pager.addItemDecoration(decoration);

    }

}