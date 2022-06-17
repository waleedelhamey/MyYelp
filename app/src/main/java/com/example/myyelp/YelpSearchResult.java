package com.example.myyelp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YelpSearchResult {
    @SerializedName("total")
    int total;
    @SerializedName("businesses")
    List<YelpRestaurant> restaurants;
}
