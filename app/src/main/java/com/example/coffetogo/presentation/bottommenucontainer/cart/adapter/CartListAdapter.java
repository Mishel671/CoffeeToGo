package com.example.coffetogo.presentation.bottommenucontainer.cart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.example.coffetogo.R;
import com.example.coffetogo.data.database.CartItem;
import com.example.coffetogo.databinding.CartItemBinding;

public class CartListAdapter extends ListAdapter<CartItem, CartItemViewHolder> {

    private Context context;
    private OnChangeItemCountListener listener;

    public CartListAdapter(Context context, OnChangeItemCountListener listener) {
        super(CartItemCallback.getInstance());
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CartListAdapterLog", "onCreateViewHolder");
        CartItemBinding binding = CartItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new CartItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        Log.d("CartListAdapterLog", "onBindViewHolder");
        CartItemBinding binding = holder.getBinding();
        CartItem cartItem = getItem(position);
        binding.title.setText(
                String.format(
                        context.getString(R.string.cart_title),
                        position + 1,
                        cartItem.getName(),
                        cartItem.getCost()
                )
        );
        binding.itemCount.setText(String.valueOf(cartItem.getItemCount()));
        binding.buttonPlus.setOnClickListener(button -> {
            listener.onChangeCount(cartItem, 1);
        });
        binding.buttonMinus.setOnClickListener(button -> {
            listener.onChangeCount(cartItem, -1);
        });
    }


}
