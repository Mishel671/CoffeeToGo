package com.example.coffetogo.presentation.bottommenucontainer.cart.adapter;

import com.example.coffetogo.data.database.CartItem;

public interface OnChangeItemCountListener {
    void onChangeCount(CartItem cartItem, int deltaCount);
}
