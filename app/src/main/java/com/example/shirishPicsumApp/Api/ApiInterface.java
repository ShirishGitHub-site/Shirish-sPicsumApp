package com.example.shirishPicsumApp.Api;

import com.example.shirishPicsumApp.Models.Items;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("list")
    Call<ArrayList<Items>> getAllData(

    );
}
