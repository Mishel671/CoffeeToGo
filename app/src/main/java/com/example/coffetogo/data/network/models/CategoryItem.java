package com.example.coffetogo.data.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryItem {

    @SerializedName("name_key")
    @Expose
    private String nameKey;

    @SerializedName("name")
    @Expose
    private String name;

    public CategoryItem(String nameKey, String name) {
        this.nameKey = nameKey;
        this.name = name;
    }

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
