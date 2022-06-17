package com.example.coffetogo.data.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CatalogItem implements Serializable {

    @SerializedName("item")
    @Expose
    private String name;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("icon_name")
    @Expose
    private String iconName;

    @SerializedName("item_description")
    @Expose
    private String itemDescription;

    public CatalogItem(String item, int price, String iconName, String itemDescription) {
        this.name = item;
        this.price = price;
        this.iconName = iconName;
        this.itemDescription = itemDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
