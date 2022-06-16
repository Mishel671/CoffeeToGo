package com.example.coffetogo.presentation.bottommenucontainer.catalog.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffetogo.databinding.CatalogItemBinding;

public class CatalogItemViewHolder extends RecyclerView.ViewHolder {

    private final CatalogItemBinding binding;

    public CatalogItemBinding getBinding(){
        return binding;
    }

    public CatalogItemViewHolder(CatalogItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
