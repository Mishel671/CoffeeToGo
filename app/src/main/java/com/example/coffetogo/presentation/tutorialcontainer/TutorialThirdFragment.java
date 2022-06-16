package com.example.coffetogo.presentation.tutorialcontainer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coffetogo.R;
import com.example.coffetogo.databinding.FragmentTutorialThirdBinding;
import com.example.coffetogo.presentation.signin.SignInFragment;


public class TutorialThirdFragment extends Fragment {

    private FragmentTutorialThirdBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTutorialThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, SignInFragment.newInstance())
                        .commit();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public static TutorialThirdFragment newInstance() {
        TutorialThirdFragment fragment = new TutorialThirdFragment();
        return fragment;
    }
}