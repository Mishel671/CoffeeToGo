package com.example.coffetogo.presentation.bottommenucontainer.catalog.adapter;

import com.example.coffetogo.data.network.models.CatalogItem;

public interface OnCatalogClickListener {
    void onItemClick(CatalogItem item, String imageUrl);
}
