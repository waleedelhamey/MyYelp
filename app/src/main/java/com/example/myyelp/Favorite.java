package com.example.myyelp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Favorite extends AppCompatActivity {

    RecyclerView recyclerView;
    RestaurantsAdapter2 adapter;
    RecyclerView.LayoutManager layoutManager;
    NavigationView nvDrawer;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    int num1,num2,num3,num4,num5,num6,num7;
    ArrayList<YelpRestaurant> restaurants = new ArrayList<>();
    YelpRestaurant restaurant=new YelpRestaurant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        layoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.rvRestaurants);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        nvDrawer=findViewById(R.id.nv);
        drawerLayout=findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawercontent(nvDrawer);

        sqLiteDatabase=this.openOrCreateDatabase("Favorite",MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Favorite (Name TEXT,Price TEXT,Category TEXT,Phone TEXT,Location TEXT,Rate TEXT,Image TEXT ) ");

        cursor=sqLiteDatabase.rawQuery("SELECT * FROM Favorite",null);

        num1=cursor.getColumnIndex("Name");
        num2=cursor.getColumnIndex("Price");
        num3=cursor.getColumnIndex("Category");
        num4=cursor.getColumnIndex("Phone");
        num5=cursor.getColumnIndex("Location");
        num6=cursor.getColumnIndex("Rate");
        num7=cursor.getColumnIndex("Image");
        if(cursor.moveToFirst()){
            restaurant.setName(cursor.getString(num1));
            restaurant.setPrice(cursor.getString(num2));
            restaurant.setCategory(cursor.getString(num3));
            restaurant.setPhone(cursor.getString(num4));
            restaurant.setPosition(cursor.getString(num5));
            restaurant.setRating(Float.valueOf(cursor.getString(num6)));
            restaurant.setImageUrl(cursor.getString(num7));
            restaurants.add(restaurant);
        }



        while(cursor.moveToNext()) {
            restaurant=new YelpRestaurant();
            restaurant.setName(cursor.getString(num1));
            restaurant.setPrice(cursor.getString(num2));
            restaurant.setCategory(cursor.getString(num3));
            restaurant.setPhone(cursor.getString(num4));
            restaurant.setPosition(cursor.getString(num5));
            restaurant.setRating(Float.valueOf(cursor.getString(num6)));
            restaurant.setImageUrl(cursor.getString(num7));

            restaurants.add(restaurant);
        }

        adapter = new RestaurantsAdapter2(this,restaurants);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

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
                Intent addintent=new Intent(Favorite.this,MainActivity.class);
                startActivity(addintent);
                finish();
                break;
            case R.id.Favorite:
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