package com.example.andrei.myapplication.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.andrei.myapplication.model.LoginPost;
import com.example.andrei.myapplication.R;
import com.example.andrei.myapplication.controller.JsonApi;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private EditText et_email, et_password;
    private Button btn_login, btn_GoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_email = findViewById(R.id.edtEmail);
        et_password = findViewById(R.id.edtPassword);
        btn_login = findViewById(R.id.btnLogin);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.107:8080/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                JsonApi jsonApi = retrofit.create(JsonApi.class);
                LoginPost loginPost = new LoginPost(email, password);
                Call<LoginPost> call = jsonApi.LoginPost(loginPost);
                call.enqueue(new Callback<LoginPost>() {
                    @Override
                    public void onResponse(Call<LoginPost> call, Response<LoginPost> response) {
                        if (!response.isSuccessful()) {
                            try {
                                String errorData = response.errorBody().string();
                                JSONObject jsonObjectError = new JSONObject(errorData);
                                Toast.makeText(MainActivity.this, response.code() + " : " + jsonObjectError.getString("message"), Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            return;
                        }
                        String emailMe = et_email.getText().toString();
                        String passwordMe = et_password.getText().toString();
                        Intent i = new Intent(MainActivity.this, MeActivity.class);
                        i.putExtra("email", emailMe);
                        i.putExtra("password", passwordMe);
                        startActivity(i);

                    }

                    @Override
                    public void onFailure(Call<LoginPost> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Code: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        btn_GoToRegister = findViewById(R.id.btnGoToRegister);

        btn_GoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

}

