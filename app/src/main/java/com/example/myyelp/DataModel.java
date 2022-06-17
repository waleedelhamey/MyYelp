package com.example.myyelp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataModel implements Serializable {
    @SerializedName("id")
    public String id;
    @SerializedName("description")
    public String description;

    @SerializedName("urls")
    public Url urls;
    class Url{
        @SerializedName("raw")
        public String raw;
        @SerializedName("regular")
        public String regular;
    }

}
