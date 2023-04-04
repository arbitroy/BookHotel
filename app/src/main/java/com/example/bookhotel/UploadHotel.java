package com.example.bookhotel;

import static android.content.ContentValues.TAG;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class UploadHotel extends AppCompatActivity {
    EditText hotelName,hotelDescription,hotelPrice,hotelFacility,hotelLocation;
    ImageSwitcher uploadPicture;
    ImageView previous,next;
    int PICK_IMAGE_MULTIPLE = 1;
    Button upload;
    Button add;

    private DatabaseReference mDatabase;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private ArrayList<Uri> mImageUris =  new ArrayList<>();
    int position = 0;
    ImageAdapter imageAdapter;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_hotel);
        hotelName = findViewById(R.id.editTextHotelName);
        hotelDescription = findViewById(R.id.editTextDescription);
        hotelPrice = findViewById(R.id.editTextPrice);
        hotelFacility = findViewById(R.id.editTextHotelFacilities);
        hotelLocation = findViewById(R.id.editTextLocation);
        add = findViewById(R.id.addFacility);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        uploadPicture = findViewById(R.id.image_view);
        upload = findViewById(R.id.upload);
        imageAdapter = new ImageAdapter(mImageUris);

        // showing all images in imageswitcher
        uploadPicture.setFactory(() -> {
            ImageView imageView1 = new ImageView(getApplicationContext());
            return imageView1;
        });

        ArrayList<String> facilities = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        add.setOnClickListener(view -> {
            facilities.add(hotelFacility.getText().toString());
            hotelFacility.setText("");
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            FacilitiesAdapter adapter = new FacilitiesAdapter(facilities);
            recyclerView.setAdapter(adapter);
        });


        uploadPicture.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
        });
        // click here to select next image
        next.setOnClickListener(v -> {
            if (position < mImageUris.size() - 1) {
                // increase the position by 1
                position++;
                uploadPicture.setImageURI(mImageUris.get(position));
            } else {
                Toast.makeText(UploadHotel.this, "Last Image Already Shown", Toast.LENGTH_SHORT).show();
            }
        });
        // click here to view previous image
        previous.setOnClickListener(v -> {
            if (position > 0) {
                // decrease the position by 1
                position--;
                uploadPicture.setImageURI(mImageUris.get(position));
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        upload.setOnClickListener(view -> {
            String name  = hotelName.getText().toString();
            String desc = hotelDescription.getText().toString();
            String price = hotelPrice.getText().toString();
            String location = hotelLocation.getText().toString();
            // Create a unique identifier for the hotel
            String hotelId = mDatabase.child("top_hotels_list").push().getKey();
// Upload the images to Firebase Storage and store the download URLs in an array list
            ArrayList<Task<Uri>> downloadUrlTasks = new ArrayList<>();
            for (Uri imageUri : mImageUris) {
                StorageReference imageRef = storageRef.child("images").child(hotelId + "_" + imageUri.getLastPathSegment());
                UploadTask uploadTask = imageRef.putFile(imageUri);
                Task<Uri> downloadUrlTask = uploadTask.continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return imageRef.getDownloadUrl();
                });
                downloadUrlTasks.add(downloadUrlTask);
            }

            Tasks.whenAllComplete(downloadUrlTasks).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // All download URL tasks have completed
                    ArrayList<String> imageUrls = new ArrayList<>();
                    for (Task<Uri> downloadUrlTask : downloadUrlTasks) {
                        if (downloadUrlTask.isSuccessful()) {
                            Uri downloadUrl = downloadUrlTask.getResult();
                            imageUrls.add(downloadUrl.toString());
                        } else {
                            // Handle errors
                        }
                    }

                    // Create a Hotel object with the hotel details, facilities, and image URLs
                    Hotel hotel = new Hotel(name, desc,location, price, imageUrls,facilities);
                    // Upload the Hotel object to Firebase Realtime Database
                    mDatabase.child("top_hotels_list").child(hotelId).setValue(hotel).addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
                    }).addOnFailureListener(e -> {
                        Log.e(TAG, "Error : " + e.getMessage());
                    });
                } else {
                    // Handle errors
                }
            });
            hotelName.setText("");
            hotelDescription.setText("");
            hotelPrice.setText("");
            hotelLocation.setText("");
            mImageUris.clear();
            uploadPicture.removeAllViews();
            facilities.clear();
            recyclerView.removeAllViews();
            Toast.makeText(this, "Uploaded Hotel", Toast.LENGTH_LONG).show();
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            // Get the Image from data
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    // adding imageuri in array
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    mImageUris.add(imageurl);
                }
                // setting 1st selected image into image switcher
                uploadPicture.setImageURI(mImageUris.get(0));
                position = 0;
            } else {
                Uri imageurl = data.getData();
                mImageUris.add(imageurl);
                uploadPicture.setImageURI(mImageUris.get(0));
                position = 0;
            }
        } else {
            // show this if no image is selected
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}

