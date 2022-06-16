package com.example.coffetogo.data.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartDbModel {

    @NonNull
    @PrimaryKey
    public String name;
    public int cost;
    public int itemCount;

    public CartDbModel(String name, int cost, int itemCount) {
        this.name = name;
        this.cost = cost;
        this.itemCount = itemCount;
    }
}
