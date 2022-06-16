package com.example.coffetogo.presentation;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coffetogo.R;
import com.example.coffetogo.databinding.FragmentSplashBinding;
import com.example.coffetogo.presentation.tutorialcontainer.TutorialContainerFragment;


public class SplashFragment extends Fragment {

    private FragmentSplashBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(() ->
                launchTutorialScreen(),
                500);
    }

    private void launchTutorialScreen(){
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, TutorialContainerFragment.newInstance())
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public static SplashFragment newInstance() {
        SplashFragment fragment = new SplashFragment();
        return fragment;
    }

}