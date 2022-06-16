package com.example.coffetogo.presentation.tutorialcontainer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coffetogo.databinding.FragmentTutorialContainerBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;


public class TutorialContainerFragment extends Fragment {

    private FragmentTutorialContainerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTutorialContainerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Fragment> fragmentList = Arrays.asList(
                TutorialFirstFragment.newInstance(),
                TutorialSecondFragment.newInstance(),
                TutorialThirdFragment.newInstance());
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getChildFragmentManager(),
                getLifecycle(),
                fragmentList
        );
        binding.viewPager.setAdapter(adapter);
        new TabLayoutMediator(binding.tabIndicator, binding.viewPager, (tab, position) -> {})
                .attach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public static TutorialContainerFragment newInstance() {
        TutorialContainerFragment fragment = new TutorialContainerFragment();
        return fragment;
    }
}