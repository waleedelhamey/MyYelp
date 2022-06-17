package com.example.myyelp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://api.yelp.com/v3/";
    public static final String API_KEY = "ZEwN71PGYTELXjMH7-8GQzBgHDzgmwLygbVQvl--TXhHiWXr3t8QJw1Xj0Ytlf8b4pL0o9VeKomTHeYjZCQmDsyIyQWKr6cknhfekmme7-D9rxHV1Dqg86bIUxCiYnYx";
    RecyclerView recyclerView;
    RestaurantsAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] sorting = { "Rating", "Price"};
    Spinner spinner;
    SearchView searchView;
    NavigationView nvDrawer;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    YelpSearchResult body;
    ArrayList<YelpRestaurant> restaurants = new ArrayList<>();
    API api = build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        nvDrawer=findViewById(R.id.nv);
        drawerLayout=findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawercontent(nvDrawer);

        recyclerView = findViewById(R.id.rvRestaurants);
        searchView=findViewById(R.id.searchView);
        spinner=findViewById(R.id.sorting);

        ArrayAdapter<String> Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sorting);
        spinner.setAdapter(Adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                    RatingSorting();
                else if(position == 1)
                    PriceSorting();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                Call<YelpSearchResult> call = api.searchRestaurants("Bearer "+API_KEY, s, "Montreal");
                Callback<YelpSearchResult > callback = new Callback< YelpSearchResult>() {
                    @Override
                    public void onResponse(Call<YelpSearchResult> call, retrofit2.Response<YelpSearchResult> response) {
                        body = response.body();
                        if(body == null){
                            Log.w("Main Activity","Did not receive valid responce");
                            return;
                        }else{
                            restaurants.clear();
                            restaurants.addAll(body.restaurants);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<YelpSearchResult > call, Throwable t) {
                        Log.e("Viewmodel", t.toString());
                    }
                };
                //Glide.with(this).load(R.id.hi).into();
                call.enqueue(callback);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        layoutManager = new LinearLayoutManager(this);


        adapter = new RestaurantsAdapter(this,restaurants);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Call<YelpSearchResult> call = api.searchRestaurants("Bearer "+API_KEY, "Restaurant", "Montreal");

        Callback<YelpSearchResult > callback = new Callback< YelpSearchResult>() {
            @Override
            public void onResponse(Call<YelpSearchResult> call, retrofit2.Response<YelpSearchResult> response) {
                body = response.body();
                if(body == null){
                    Log.w("Main Activity","Did not receive valid responce");
                    return;
                }else{
                    restaurants.addAll(body.restaurants);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<YelpSearchResult > call, Throwable t) {
                Log.e("Viewmodel", t.toString());
            }
        };
        //Glide.with(this).load(R.id.hi).into();
        call.enqueue(callback);

    };
    public API build() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        return chain.proceed(chain.request().newBuilder()
                                .addHeader("API_KEY",API_KEY)
                                .build());

                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        return retrofit.create(API.class);
    }

    public void PriceSorting()
    {
        for(int i=0; i<restaurants.size(); i++)
        {
            for(int j=i+1; j<restaurants.size(); j++)
            {
                if(restaurants.get(j).price == null)
                    restaurants.get(j).price = "";
                if(restaurants.get(i).price == null)
                    restaurants.get(i).price = "";

                if(restaurants.get(j).price.length() <= restaurants.get(i).price.length()) {
                    restaurants.add(i,restaurants.get(j));
                    restaurants.remove(j+1);
                }
                else {
                    restaurants.add(i, restaurants.get(i));
                    restaurants.remove(i + 1);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


    public void RatingSorting()
    {
        for(int i=0; i<restaurants.size(); i++)
        {
            for(int j=i+1; j<restaurants.size(); j++)
            {
                if(restaurants.get(j).rating >= restaurants.get(i).rating) {
                    restaurants.add(i,restaurants.get(j));
                    restaurants.remove(j+1);
                }
                else {
                    restaurants.add(i, restaurants.get(i));
                    restaurants.remove(i + 1);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
        {return true;}
        return super.onOptionsItemSelected(item);
    }
    public void selectItemDrawer(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.Search:
                break;
            case R.id.Favorite:
                Intent addintent=new Intent(MainActivity.this,Favorite.class);
                startActivity(addintent);
                finish();
                break;
            default:
                break;
        }
    }
    public void setupDrawercontent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectItemDrawer(menuItem);
                return true;
            }
        });
    }




}