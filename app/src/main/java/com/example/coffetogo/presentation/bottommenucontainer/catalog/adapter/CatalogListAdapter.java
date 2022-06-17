package com.example.coffetogo.presentation.bottommenucontainer.catalog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.example.coffetogo.R;
import com.example.coffetogo.data.network.models.CatalogItem;
import com.example.coffetogo.databinding.CatalogItemBinding;
import com.squareup.picasso.Picasso;

public class CatalogListAdapter extends ListAdapter<CatalogItem, CatalogItemViewHolder> {

    private final Context context;
    private static final String imagesUrl = "http://192.168.2.219/catalog/getProductIcon/";
    public String drinkType = "";


    private final OnCatalogClickListener listener;

    public CatalogListAdapter(Context context, OnCatalogClickListener listener) {
        super(CatalogItemCallback.getInstance());
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CatalogItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CatalogItemBinding binding = CatalogItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new CatalogItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogItemViewHolder holder, int position) {
        CatalogItem item = getItem(position);
        CatalogItemBinding binding = holder.getBinding();
        String imageUrl = imagesUrl + drinkType + "/" + item.getIconName();
        binding.drinkTitle.setText(item.getName());
        binding.drinkCost.setText(
                String.format(
                        context.getString(R.string.drink_cost),
                        item.getPrice()
                )
        );
        binding.getRoot().setOnClickListener(view -> listener.onItemClick(item, imageUrl)
        );
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_coffee_logo)
                .error(R.drawable.ic_coffee_error)
                .fit()
                .into(binding.drinkImage);

    }
}
