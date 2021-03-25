package com.example.shirishPicsumApp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shirishPicsumApp.Adapter.ItemAdapter;
import com.example.shirishPicsumApp.Api.ApiInterface;
import com.example.shirishPicsumApp.Api.ClientInstance;
import com.example.shirishPicsumApp.Models.Items;
import com.example.shirishPicsumApp.R;
import com.example.shirishPicsumApp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private int currentScreenOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    ApiInterface apiInterface;
    ActivityMainBinding binding;

    private static final String TAG = "MainActivity";
    List<Items> itemsList;

    ItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        binding=DataBindingUtil.setContentView(this,R.layout.activity_main);
   apiInterface=ClientInstance.getInstance().create(ApiInterface.class);
        itemsList=new ArrayList<>();
        adapter=new ItemAdapter(this,itemsList);
        getAllData();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            adapter=new ItemAdapter(MainActivity.this,itemsList);
            binding.resView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
            binding.resView.setAdapter(adapter);
               binding.shimmerEffect.stopShimmer();
               binding.shimmerEffect.setVisibility( View.GONE );
        }
        else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){

            adapter=new ItemAdapter(MainActivity.this,itemsList);
            binding.resView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
            binding.resView.setAdapter(adapter);
           binding.shimmerEffect.stopShimmer();
           binding.shimmerEffect.setVisibility( View.GONE );

        }
    }



    private void getAllData() {


        Call<ArrayList<Items>> arrayListCall= apiInterface.getAllData();
        arrayListCall.enqueue(new Callback<ArrayList<Items>>() {
            @Override
            public void onResponse(Call<ArrayList<Items>> call, Response<ArrayList<Items>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    Log.e(TAG, "onResponse: "+response.body().get(0).getAuthor() );

                    for(int x=0;x<response.body().size();x++){
                        Items i=response.body().get(x);
                        Items items=new Items(i.getFormat(),i.getWidth(),i.getHeight(),i.getFilename(),i.getId(),
                                i.getAuthor(),i.getAuthorUrl(),i.getPostUrl());

                        itemsList.add(items);
                    }
                    adapter=new ItemAdapter(MainActivity.this,itemsList);
                    binding.resView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    binding.resView.setAdapter(adapter);
                    binding.shimmerEffect.stopShimmer();
                    binding.shimmerEffect.setVisibility( View.GONE );


                }



            }

            @Override
            public void onFailure(Call<ArrayList<Items>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Err:"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}