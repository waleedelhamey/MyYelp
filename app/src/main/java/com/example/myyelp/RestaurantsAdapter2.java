package com.example.myyelp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RestaurantsAdapter2 extends RecyclerView.Adapter<RestaurantsAdapter2.ViewHolder> {
    Context context;
    ArrayList<YelpRestaurant> restaurants;

    public RestaurantsAdapter2(Context context, ArrayList<YelpRestaurant> restaurants) {
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
        holder.name.setText(restaurant.name);
        holder.Price.setText(restaurant.price);
        holder.Category.setText(restaurant.getCategory());
        holder.PhoneNumber.setText(restaurant.phone);
        holder.Address.setText(restaurant.getPosition());
        holder.ratingBar.setRating((float) restaurant.rating);
        Glide.with(context).load(restaurant.imageUrl).into(holder.image);

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
