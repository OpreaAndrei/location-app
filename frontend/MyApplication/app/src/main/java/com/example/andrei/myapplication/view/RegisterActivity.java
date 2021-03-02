package com.example.andrei.myapplication.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrei.myapplication.R;
import com.example.andrei.myapplication.model.RegisterPost;
import com.example.andrei.myapplication.controller.JsonApi;
import com.example.andrei.myapplication.model.UserRegister;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_email, et_password, et_confrimPassword, et_firstName, et_lastName;
    private Button btn_register;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_email = findViewById(R.id.edtEmail);
        et_password = findViewById(R.id.edtPassword);
        et_confrimPassword = findViewById(R.id.edtConfirmPass);
        et_firstName = findViewById(R.id.edtFirst);
        et_lastName = findViewById(R.id.edtLast);
        btn_register = findViewById(R.id.btnRegister);

        textViewResult = findViewById(R.id.textView_result);


        //Buton register set on click listener

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                String confirmPassword = et_confrimPassword.getText().toString();
                String firstName = et_firstName.getText().toString();
                String lastName = et_lastName.getText().toString();

                UserRegister userRegister = new UserRegister(email,password,confirmPassword,firstName,lastName);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.107:8080/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonApi jsonApi1 = retrofit.create(JsonApi.class);
                RegisterPost registerPost = new RegisterPost(userRegister);
                Call<RegisterPost> call = jsonApi1.RegisterPost(registerPost);
                call.enqueue(new Callback<RegisterPost>() {
                    @Override
                    public void onResponse(Call<RegisterPost> call, Response<RegisterPost> response) {
                        if (!response.isSuccessful()) {
                            try {
                                String errorData = response.errorBody().string();
                                JSONObject jsonObjectError = new JSONObject(errorData);
                                Toast.makeText(RegisterActivity.this, response.code() + " : " + jsonObjectError.getString("message"), Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterPost> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Code: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
