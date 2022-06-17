package com.example.coffetogo.presentation.bottommenucontainer.cart.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coffetogo.databinding.CartItemBinding;

public class CartItemViewHolder extends RecyclerView.ViewHolder {

    private final CartItemBinding binding;

    public CartItemBinding getBinding() {
        return binding;
    }

    public CartItemViewHolder(CartItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
