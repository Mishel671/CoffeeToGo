package com.example.coffetogo.presentation.bottommenucontainer.catalog.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.coffetogo.data.network.models.CatalogItem;

public class CatalogItemCallback extends DiffUtil.ItemCallback<CatalogItem> {

    private static CatalogItemCallback catalogItemCallback;

    @Override
    public boolean areItemsTheSame(@NonNull CatalogItem oldItem, @NonNull CatalogItem newItem) {
        return oldItem.getName().equals(newItem.getName());
    }

    @Override
    public boolean areContentsTheSame(@NonNull CatalogItem oldItem, @NonNull CatalogItem newItem) {
        return oldItem.getItemDescription().equals(newItem.getItemDescription());
    }

    private CatalogItemCallback(){}

    public static CatalogItemCallback getInstance(){
        if(catalogItemCallback == null){
            catalogItemCallback = new CatalogItemCallback();
        }
        return catalogItemCallback;
    }
}
