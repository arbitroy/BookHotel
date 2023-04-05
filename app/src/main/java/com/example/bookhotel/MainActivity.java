package com.example.bookhotel;

import static android.service.controls.ControlsProviderService.TAG;

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
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView tophotels_pager;
    ImageView adminIcon;
    ImageView recom;
    private DatabaseReference mDatabase;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adminIcon = findViewById(R.id.up_page);
        recom =  findViewById(R.id.recommended);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        Query query = databaseReference.orderByChild("email").equalTo(email);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        ArrayList<Hotel> top_hotels_list = new ArrayList<>();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                top_hotels_list.clear();
                // Loop through the hotels in the database and add them to the list
                for (DataSnapshot snapshot : dataSnapshot.child("top_hotels_list").getChildren()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String location = snapshot.child("location").getValue(String.class);
                    String price = snapshot.child("price").getValue(String.class);
                    ArrayList<String> facilities = (ArrayList<String>) snapshot.child("facilities").getValue();
                    ArrayList<String> imageURLs = new ArrayList<>();
                    for (DataSnapshot imageSnapshot : snapshot.child("imageURL").getChildren()) {
                        String imageName = imageSnapshot.getValue(String.class);
                        imageURLs.add(imageName);
                    }
                    Hotel hotel = new Hotel(name, description, location, price, imageURLs, facilities);
                    top_hotels_list.add(hotel);
                }

                // Create and set the adapter after the list has been populated
                tophotels_pager = findViewById(R.id.top_hotels_pager);
                TopHotelsAdapter adapter = new TopHotelsAdapter(MainActivity.this, top_hotels_list);
                LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                MyDecoration decoration = new MyDecoration(30);
                tophotels_pager.setLayoutManager(manager);
                tophotels_pager.setAdapter(adapter);
                tophotels_pager.addItemDecoration(decoration);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "Oopsie", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(postListener);
        adminIcon.setOnClickListener(view -> {
            Intent upload = new Intent(MainActivity.this, UploadHotel.class);
            startActivity(upload);
        });
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String role = userSnapshot.child("role").getValue(String.class);
                        if (role != null && role.equals("Admin")) {
                            System.out.println(role);
                            // Show admin features
                            adminIcon.setVisibility(View.VISIBLE);
                        }else if(role.equals("User")){
                            adminIcon.setVisibility(View.INVISIBLE);
                        }
                    }
                } else {
                    Log.d("USER_ROLE", "No role found for email: " + email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("USER_ROLE", "Database error: " + databaseError.getMessage());
            }
        });

    }


}