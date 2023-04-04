package com.example.bookhotel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.entity.StringEntity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailsActivity extends AppCompatActivity {

    TextView head_name, head_location, name, price, locname,description;
    ImageView image, back;
    Button gmaps;
    PopupWindow popupWindow;
    static String number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        head_name = findViewById(R.id.details_head_name);
        head_location = findViewById(R.id.details_head_location);
        name = findViewById(R.id.details_name);
        price = findViewById(R.id.details_price);
        image = findViewById(R.id.details_image);
        back = findViewById(R.id.details_back);
        description = findViewById(R.id.description);
        gmaps = findViewById(R.id.button);

        String name_position = getIntent().getStringExtra("tophotelsname");
        String location_position = getIntent().getStringExtra("tophotelslocation");
        String price_position = getIntent().getStringExtra("tophotelsprice");
        String image_position = getIntent().getStringExtra("tophotelsimage");
        String hotel_desc = getIntent().getStringExtra("tophoteldesc");

        head_name.setText(name_position);
        name.setText(name_position);
        head_location.setText(location_position);
        price.setText("Ksh "+price_position);
        description.setText(hotel_desc);
        Picasso.get().load(image_position).into(image);


        back.setOnClickListener(view -> finish());

        gmaps.setOnClickListener(view -> {
            locname = findViewById(R.id.details_head_name);
            String url = "http://maps.google.com/maps?daddr="+head_name.getText().toString();
            Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse(url));
            startActivity(intent);
        });


    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return POST();
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Create HttpPost request with a JSON string body, expecting a JSON response
     */
    private HttpPost createJSONPostRequest() throws JSONException, UnsupportedEncodingException
    {
        Log.wtf("tagger" , "Sending request");
        JSONObject json = new JSONObject();
        //json.put("protocolVersion", "1.0");
        json.put("amount", "1");
        json.put("number", "0713023787");
        json.put("private_key" , "13c7ab60-776c-4d7c-8c4f-ace73eced83b");
        String jsonString = json.toString();

        String serviceURLString = "https://tinybitdaraja.herokuapp.com/msp";
        HttpPost request = new HttpPost(serviceURLString);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization", "Bearer mgN_9p2k2ROS2Gb3gfA6H");
        request.setEntity(new StringEntity(jsonString));

        return request;
    }
    // functions to make post request
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    public static String POST() {
        InputStream inputStream = null;
        String result = "";
        try {
            URL url = new URL("https://tinybitdaraja.herokuapp.com/msp");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer mgN_9p2k2ROS2Gb3gfA6H");
            connection.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("amount", "1");
            jsonObject.accumulate("number", number);
            jsonObject.accumulate("private_key", "13c7ab60-776c-4d7c-8c4f-ace73eced83b");

            String json = jsonObject.toString();

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int statusCode = connection.getResponseCode();

            if (statusCode == HttpURLConnection.HTTP_OK || statusCode == HttpURLConnection.HTTP_CREATED) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void MpesaApi(View view) throws IOException /**throws IOException, JSONException **/  {
        //Implement the M-Pesa Api here ......
        /**
         * POST API ENDPOINT : https://tinybitdaraja.herokuapp.com/msp
         * JSON STRUCTURE
         *amount : any value above zero
         * number : Phone number to make payment
         * private_key : Obtained from project created under developer options
         * HEADERS
         * Authorization : Bearer token (public_key: obtained from project : under developer options)
         */
        //Log.d("tagger","This is a log");
        Log.wtf("tagger" , "This is a message");
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.phone_popup, null);
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        Button payButton = popupView.findViewById(R.id.pay);
        EditText phone = popupView.findViewById(R.id.phone_no);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = String.valueOf(phone.getText());
                popupWindow.dismiss();
                new HttpAsyncTask().execute("https://tinybitdaraja.herokuapp.com/msp");
            }
        });
        popupWindow.showAtLocation(
                findViewById(R.id.details),
                Gravity.CENTER,
                0,
                0
        );

    }



}
