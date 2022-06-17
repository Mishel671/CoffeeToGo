package com.example.coffetogo.presentation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffetogo.R;
import com.example.coffetogo.databinding.ActivityMainBinding;
import com.example.coffetogo.presentation.bottommenucontainer.BottomMenuContainerFragment;
import com.example.coffetogo.presentation.signin.SignInFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, SplashFragment.newInstance())
                    .commit();
        }
    }
}