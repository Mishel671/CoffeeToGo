package com.example.coffetogo.data.database;


public class CartItem {
    private String name;
    private int cost;
    private int itemCount;

    public CartItem(String name, int cost, int itemCount) {
        this.name = name;
        this.cost = cost;
        this.itemCount = itemCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

}
