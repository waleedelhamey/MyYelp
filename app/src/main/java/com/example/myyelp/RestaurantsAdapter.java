package com.example.myyelp;


import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    Context context;
    ArrayList<YelpRestaurant> restaurants;

    public RestaurantsAdapter(Context context, ArrayList<YelpRestaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.item_restaurant,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YelpRestaurant restaurant = restaurants.get(position);
        holder.name.setText((position+1)+" "+restaurant.name);
        holder.Price.setText(restaurant.price);
        holder.Category.setText(restaurant.categories.get(0).title);
        holder.PhoneNumber.setText(restaurant.phone);
        holder.Address.setText(restaurant.location.address);
        holder.ratingBar.setRating((float) restaurant.rating);
        Glide.with(context).load(restaurant.imageUrl).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open(restaurant.name,restaurant.price,restaurant.categories.get(0).title,restaurant.phone,restaurant.location.address,String.valueOf(restaurant.rating),restaurant.imageUrl);
            }
        });
    }
    SQLiteDatabase sqLiteDatabase;
    public void open(String name,String price,String Category,String phone,String location,String rate,String image){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Add to favorite?");
        alertDialogBuilder.setMessage("Do you want to add this item to favorite");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                sqLiteDatabase=context.openOrCreateDatabase("Favorite",Context.MODE_PRIVATE,null);
                String name1=" ";

                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Favorite (Name TEXT,Price TEXT,Category TEXT,Phone TEXT,Location TEXT,Rate TEXT,Image TEXT ) ");

                if(name.contains("'")){
                    Log.d("name",name);
                    name1 = name.replace("'","''");
                    Log.d("new name",name1);
                }
                sqLiteDatabase.execSQL("INSERT INTO Favorite(Name,Price,Category,Phone,Location,Rate,Image) VALUES ('" + name1 + "','" + price + "','" + Category + "','" + phone + "','" + location + "','"+rate+"','" +image+"')");

            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,Category,PhoneNumber,Address,Price;
        ImageView image;
        RatingBar ratingBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            Price = itemView.findViewById(R.id.Price);
            Category = itemView.findViewById(R.id.Category);
            PhoneNumber = itemView.findViewById(R.id.PhoneNumber);
            Address = itemView.findViewById(R.id.Address);
            ratingBar = itemView.findViewById(R.id.RatingBar);
            image=itemView.findViewById(R.id.image);
        }
    }
}
