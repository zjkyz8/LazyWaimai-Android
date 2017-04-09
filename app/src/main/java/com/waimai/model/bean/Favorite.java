package com.waimai.model.bean;

import com.google.gson.annotations.SerializedName;

public class Favorite {

    @SerializedName("id")
    String id;

    @SerializedName("business_info")
    Business business;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
}
