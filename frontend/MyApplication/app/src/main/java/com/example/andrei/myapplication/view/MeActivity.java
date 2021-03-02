package com.example.andrei.myapplication.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.andrei.myapplication.controller.BasicAuth;
import com.example.andrei.myapplication.model.Post;
import com.example.andrei.myapplication.R;
import com.example.andrei.myapplication.controller.JsonApi;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MeActivity extends AppCompatActivity {

    Button btnLocation;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        Intent intent = getIntent();

        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        textViewResult = findViewById(R.id.textView_result);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuth(email, password))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.107:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        JsonApi jsonApi = retrofit.create(JsonApi.class);
        Call<Post> call = jsonApi.getMe();

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post post;
                post = response.body();

                String content = "";
                content += "ID : " + post.getId() + "\n";
                content += "Frist name : " + post.getFirstName() + "\n";
                content += "Last name : " + post.getLastName() + "\n";
                content += "Email : " + post.getEmail() + "\n\n\n";

                textViewResult.append(content);
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
            }
        });

        btnLocation = findViewById(R.id.buttonGoToLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailLocation = email;
                String passwordLocation = password;
                Intent intentLocation = new Intent(MeActivity.this, CurrentLocationActivity.class);
                intentLocation.putExtra("email",emailLocation);
                intentLocation.putExtra("password",passwordLocation);
                startActivity(intentLocation);
            }
        });

    }
}
