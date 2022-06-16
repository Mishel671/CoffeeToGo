package com.example.coffetogo.presentation.bottommenucontainer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.coffetogo.R;
import com.example.coffetogo.databinding.FragmentBottomMenuContainerBinding;
import com.example.coffetogo.presentation.bottommenucontainer.catalog.CatalogFragment;


public class BottomMenuContainerFragment extends Fragment {

    private FragmentBottomMenuContainerBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLightStatusBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomMenuContainerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case (R.id.catalog):
                    launchFragment(CatalogFragment.newInstance());
                    return true;
                case (R.id.share):
                    launchFragment(ShareFragment.newInstance());
                    return true;
                case (R.id.user):
                    launchFragment(UserFragment.newInstance());
                    return true;
                default:
                    return false;
            }
        });
        binding.bottomNavigation.setSelectedItemId(R.id.catalog);
    }

    private void setLightStatusBar(){
        Window screen =  requireActivity().getWindow();
        screen.setStatusBarColor(ContextCompat.getColor(requireActivity(), R.color.white));
        screen.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private void launchFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerBottomNav, fragment)
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public static BottomMenuContainerFragment newInstance() {
        BottomMenuContainerFragment fragment = new BottomMenuContainerFragment();
        return fragment;
    }

}