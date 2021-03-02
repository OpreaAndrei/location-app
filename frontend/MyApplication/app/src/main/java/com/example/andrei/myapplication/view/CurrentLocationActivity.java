package com.example.andrei.myapplication.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrei.myapplication.controller.BasicAuth;
import com.example.andrei.myapplication.model.LocationPost;
import com.example.andrei.myapplication.R;
import com.example.andrei.myapplication.controller.JsonApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CurrentLocationActivity extends AppCompatActivity {

    //initialize variable

    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7;
    FusedLocationProviderClient fusedLocationProviderClient;
    Date date = new Date();
    Button btnSendLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);


        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);
        textView6 = findViewById(R.id.text_view6);
        textView7 = findViewById(R.id.text_view7);

        //Initialize fusedLocationProviderClient

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(CurrentLocationActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //When permission granted

            getLocation();

        } else {
            //When permission denied
            ActivityCompat.requestPermissions(CurrentLocationActivity.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        //Send location to the server


    }


    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //initialize Location
                Location location = task.getResult();
                if (location != null) {

                    try {
                        //Initialize geoCoder
                        Geocoder geocoder = new Geocoder(CurrentLocationActivity.this,
                                Locale.getDefault());
                        //initialize address
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);
                        //Set latitude on textview
                        textView1.setText(Html.fromHtml(("<font color='#6200EE'><b>Latitude:</b><br></font>"
                                + addresses.get(0).getLatitude())
                        ));

                        textView2.setText(Html.fromHtml(("<font color='#6200EE'><b>Longitude:</b><br></font>"
                                + addresses.get(0).getLongitude())
                        ));

                        textView3.setText(Html.fromHtml(("<font color='#6200EE'><b>Country:</b><br></font>"
                                + addresses.get(0).getCountryName())
                        ));

                        textView4.setText(Html.fromHtml(("<font color='#6200EE'><b>Locality:</b><br></font>"
                                + addresses.get(0).getLocality())
                        ));
                        textView5.setText(Html.fromHtml(("<font color='#6200EE'><b>Address:</b><br></font>"
                                + addresses.get(0).getAddressLine(0))
                        ));

                       String latitude = String.valueOf(addresses.get(0).getLatitude());
                       String longitude = String.valueOf(addresses.get(0).getLongitude());

                        Intent inti = getIntent();
                        String email = inti.getStringExtra("email");
                        String password = inti.getStringExtra("password");
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        String formattedDate = df.format(date);

                        textView6.setText(Html.fromHtml(("<font color='#6200EE'><b>Email:</b><br></font>"
                                + email)
                        ));
                        textView7.setText(Html.fromHtml(("<font color='#6200EE'><b>Date:</b><br></font>"
                                + formattedDate)
                        ));

                        btnSendLocation = findViewById(R.id.btnSendLocation);

                        btnSendLocation.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                OkHttpClient client = new OkHttpClient.Builder()
                                        .addInterceptor(new BasicAuth(email, password))
                                        .build();

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://192.168.1.107:8080/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .client(client)
                                        .build();

                                JsonApi jsonApi= retrofit.create(JsonApi.class);
                                LocationPost locationPost = new LocationPost(latitude,longitude,email);
                                Call<LocationPost> call = jsonApi.LocationPost(locationPost);
                                call.enqueue(new Callback<LocationPost>() {
                                    @Override
                                    public void onResponse(Call<LocationPost> call, Response<LocationPost> response) {
                                        if (!response.isSuccessful()) {
                                            try {
                                                String errorData = response.errorBody().string();
                                                JSONObject jsonObjectError = new JSONObject(errorData);
                                                Toast.makeText(CurrentLocationActivity.this, response.code() + " : " + jsonObjectError.getString("message"), Toast.LENGTH_SHORT).show();

                                            } catch (Exception e) {
                                                Toast.makeText(CurrentLocationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<LocationPost> call, Throwable t) {
                                        Toast.makeText(CurrentLocationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

}
