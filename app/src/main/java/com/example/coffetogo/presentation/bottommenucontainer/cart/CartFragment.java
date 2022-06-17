package com.example.coffetogo.presentation.bottommenucontainer.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffetogo.R;
import com.example.coffetogo.databinding.FragmentCartBinding;
import com.example.coffetogo.presentation.bottommenucontainer.cart.adapter.CartListAdapter;
import com.example.coffetogo.presentation.bottommenucontainer.cart.adapter.OnChangeItemCountListener;


public class CartFragment extends Fragment {


    private FragmentCartBinding binding;
    private CartViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        setRecyclerView();
    }

    private void setRecyclerView() {
        OnChangeItemCountListener listener = (cartItem, deltaCount) -> {

            viewModel.changeCountItem(cartItem, deltaCount);
        };
        CartListAdapter adapter = new CartListAdapter(requireActivity(), listener);
        binding.cartRecyclerView.setAdapter(adapter);
        viewModel.getCartList().observe(getViewLifecycleOwner(), cartList -> {
            adapter.submitList(cartList);
            Log.d("CartLog", String.valueOf(cartList.size()));
        });
        viewModel.getResultCost().observe(getViewLifecycleOwner(), resultCost -> {
            binding.resultCost.setText(
                    String.format(getString(R.string.result_cost), resultCost)
            );
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }


}