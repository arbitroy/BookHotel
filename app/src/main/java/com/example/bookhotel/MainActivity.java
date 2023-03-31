package com.example.bookhotel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView tophotels_pager;
    ImageView adminIcon;
    ArrayList<TopHotelsModel> top_hotels_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adminIcon = findViewById(R.id.up_page);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        Query query = databaseReference.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String role = userSnapshot.child("Role").getValue(String.class);
                        if (role.equals("Admin")) {
                            // Show admin features

                            adminIcon.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    Log.d("USER_ROLE", "No role found for email: " + email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("USER_ROLE", "Failed to get role from Firebase", databaseError.toException());
            }
        });

        adminIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent upload = new Intent(MainActivity.this, UploadHotel.class);
                startActivity(upload);

            }
        });


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