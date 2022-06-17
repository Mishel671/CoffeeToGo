package com.example.coffetogo.presentation.bottommenucontainer.catalog.detailcatalog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffetogo.R;
import com.example.coffetogo.data.network.models.CatalogItem;
import com.example.coffetogo.databinding.FragmentDetailCatalogBinding;
import com.squareup.picasso.Picasso;

public class DetailCatalogFragment extends Fragment {


    private FragmentDetailCatalogBinding binding;
    private Bitmap blurBitmap;
    private CatalogItem catalogItem;
    private String imageUrl;
    private DetailCatalogViewModel viewModel;
    private int itemCount = 1;

    @Override
    public void onAttach(@NonNull Context context) {
        View activityView = requireActivity().findViewById(R.id.fragmentContainerBottomNav);
        blurBitmap = blurBackground(activityView);
        catalogItem = (CatalogItem) getArguments().getSerializable(ITEM_KEY);
        imageUrl = getArguments().getString(IMAGE_URL_KEY, "");
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailCatalogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(DetailCatalogViewModel.class);
        viewModel.addCartItem(catalogItem);
        parseArguments();
        setClickListeners();

    }

    private void parseArguments() {
        binding.backgroundImage.setImageBitmap(blurBitmap);
        binding.itemTitle.setText(catalogItem.getName());
        binding.itemDescription.setText(catalogItem.getItemDescription());
        Picasso.with(requireActivity())
                .load(imageUrl)
                .placeholder(R.drawable.ic_coffee_detail_load)
                .error(R.drawable.ic_coffee_error)
                .fit()
                .into(binding.itemImage);

    }

    private void setClickListeners() {
        binding.buttonPlus.setOnClickListener(button -> {
            itemCount++;
            binding.itemCount.setText(String.valueOf(itemCount));
        });
        binding.buttonMinus.setOnClickListener(button -> {
            if (itemCount > 0) {
                itemCount--;
                binding.itemCount.setText(String.valueOf(itemCount));
            }
        });
        binding.itemContainer.setOnClickListener(button ->{
            //Required listener
        });
        binding.backgroundImage.setOnClickListener(image -> {
            requireActivity().onBackPressed();
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                viewModel.saveResult(itemCount);
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }


    private Bitmap blurBackground(View background) {
        Bitmap bitmap = Bitmap.createBitmap(background.getWidth(), background.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        background.layout(background.getLeft(), background.getTop(), background.getRight(), background.getBottom());
        background.draw(c);
        RenderScript renderScript = RenderScript.create(getContext());
        Bitmap renderBitmap = new RSBlurProcessor(renderScript).blur(bitmap, 8, 1);
        return renderBitmap;
    }


    public static DetailCatalogFragment newInstance(CatalogItem catalogItem, String imageUrl) {
        DetailCatalogFragment fragment = new DetailCatalogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEM_KEY, catalogItem);
        bundle.putString(IMAGE_URL_KEY, imageUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    private static final String ITEM_KEY = "catalog_item";
    private static final String IMAGE_URL_KEY = "image_url";
}