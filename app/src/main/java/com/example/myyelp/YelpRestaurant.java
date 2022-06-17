package com.example.myyelp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YelpRestaurant {

    @SerializedName("name")
    String name;
    @SerializedName("rating")
    double rating;
    @SerializedName("price")
    String price;
    @SerializedName("review_count")
    int numReviews;
    @SerializedName("distances")
    double distanceInMeters;
    @SerializedName("image_url")
    String imageUrl;
    @SerializedName("categories")
    List<YelpCategory> categories;
    @SerializedName("location")
    YelpLocation location;
    @SerializedName("phone")
    String phone;

    String category;
    String position;

    public String getCategory() {
        return category;
    }

    public String getPosition() {
        return position;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String displayDistance(){
        return String.valueOf(distanceInMeters);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
    }

    public double getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(double distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<YelpCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<YelpCategory> categories) {
        this.categories = categories;
    }

    public YelpLocation getLocation() {
        return location;
    }

    public void setLocation(YelpLocation location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}