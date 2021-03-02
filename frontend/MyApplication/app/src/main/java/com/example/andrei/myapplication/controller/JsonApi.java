package com.example.andrei.myapplication.controller;

import com.example.andrei.myapplication.model.LocationPost;
import com.example.andrei.myapplication.model.LoginPost;
import com.example.andrei.myapplication.model.Post;
import com.example.andrei.myapplication.model.RegisterPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonApi {
    @GET("users")
    Call<List<Post>> getPosts();

    @POST("users/register")
    Call<RegisterPost> RegisterPost(@Body RegisterPost registerPost);

    @POST("users/login")
    Call<LoginPost> LoginPost(@Body LoginPost loginPost);

    @GET("users/me")
    Call<Post> getMe();

    @POST("/locations/createLocation")
    Call<LocationPost> LocationPost(@Body LocationPost locationPost);
}
