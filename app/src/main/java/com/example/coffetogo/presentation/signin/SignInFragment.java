package com.example.coffetogo.presentation.signin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffetogo.R;
import com.example.coffetogo.databinding.FragmentSignInBinding;
import com.example.coffetogo.presentation.bottommenucontainer.BottomMenuContainerFragment;
import com.example.coffetogo.presentation.bottommenucontainer.catalog.CatalogFragment;

public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private SignInViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SignInViewModel.class);
        binding.btnSignIn.setOnClickListener(button -> {
            viewModel.signIn(
                    binding.etEmail.getText().toString(),
                    binding.etPassword.getText().toString()
            );
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show();
        });

        viewModel.getSuccessLogin().observe(getViewLifecycleOwner(), unit -> {
            launchCatalogFragment();
        });
    }

    private void launchCatalogFragment(){
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, BottomMenuContainerFragment.newInstance())
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        return fragment;
    }

}