package com.example.coffetogo.presentation.bottommenucontainer.catalog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.coffetogo.R;
import com.example.coffetogo.data.network.models.CategoryItem;
import com.example.coffetogo.databinding.FragmentCatalogBinding;
import com.example.coffetogo.presentation.bottommenucontainer.cart.CartFragment;
import com.example.coffetogo.presentation.bottommenucontainer.catalog.adapter.CatalogListAdapter;
import com.example.coffetogo.presentation.bottommenucontainer.catalog.adapter.OnCatalogClickListener;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class CatalogFragment extends Fragment {

    private FragmentCatalogBinding binding;

    private CatalogViewModel viewModel;

    private ArrayList<TabLayout.Tab> tabList;
    private int selectedTab;

    private CatalogListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCatalogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CatalogViewModel.class);
        setTabLayout();
        setRecyclerView();
        setCartButton();
    }


    private void setRecyclerView() {
        OnCatalogClickListener listener = item -> {
            Log.d("CatalogLog", item.getName());
            viewModel.addCartItem(item);
        };
        adapter = new CatalogListAdapter(requireActivity(), listener);
        binding.recyclerView.setAdapter(adapter);
        viewModel.getCatalogList().observe(getViewLifecycleOwner(), catalogList -> {
            adapter.submitList(catalogList);
        });
        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
    }

    private void setCartButton() {
        viewModel.getCartItemCount().observe(getViewLifecycleOwner(), cartItemCount -> {
            if (cartItemCount > 0) {
                binding.badgeFrame.setVisibility(View.VISIBLE);
                binding.badgeText.setText(cartItemCount.toString());
            } else {
                binding.badgeFrame.setVisibility(View.INVISIBLE);
            }
        });
        binding.cartButton.setOnClickListener(button -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerBottomNav, CartFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });

    }

    private void setTabLayout() {
        viewModel.getCategoryList().observe(getViewLifecycleOwner(), categoryList -> {
            TabLayout tabLayout = binding.tabLayout;
            tabList = new ArrayList<>(categoryList.size());
            int counter = 0;
            for (CategoryItem item : categoryList) {
                TabLayout.Tab tab = tabLayout.newTab();
                tab.setId(counter);
                tab.setText(item.getName());
                tab.setTag(item.getNameKey());
                tabLayout.addTab(tab);
                tabList.add(tab);
                counter++;
            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = tab.getId();
                String drinkType = tab.getTag().toString();
                adapter.drinkType = drinkType;
                viewModel.getDrinkByTag(drinkType);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.tabButtonLeft.setOnClickListener(button -> {
            if (selectedTab > 0) {
                binding.tabLayout.selectTab(tabList.get(selectedTab - 1));
            } else {
                binding.tabLayout.selectTab(tabList.get(tabList.size() - 1));
            }
        });
        binding.tabButtonRight.setOnClickListener(button -> {
            if (selectedTab < tabList.size() - 1) {
                binding.tabLayout.selectTab(tabList.get(selectedTab + 1));
            } else {
                binding.tabLayout.selectTab(tabList.get(0));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public static CatalogFragment newInstance() {
        CatalogFragment fragment = new CatalogFragment();
        return fragment;
    }

}