package com.example.coffetogo.presentation.bottommenucontainer.cart.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.coffetogo.data.database.CartItem;

public class CartItemCallback extends DiffUtil.ItemCallback<CartItem> {

    private static CartItemCallback cartItemCallback;

    @Override
    public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
        return oldItem.getName().equals(newItem.getName());
    }

    @Override
    public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
        boolean equals = true;
        if (
                !oldItem.getName().equals(newItem.getName()) ||
                        oldItem.getCost() != newItem.getCost() ||
                        oldItem.getItemCount() != newItem.getItemCount()) {
            equals = false;
        }
        Log.d("CartItemCallbackLog", String.valueOf(equals));
        return equals;
    }


    private CartItemCallback() {
    }

    public static CartItemCallback getInstance() {
        if (cartItemCallback == null) {
            cartItemCallback = new CartItemCallback();
        }
        return cartItemCallback;
    }
}

