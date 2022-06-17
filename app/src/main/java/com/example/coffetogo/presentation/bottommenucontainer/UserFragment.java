package com.example.coffetogo.presentation.bottommenucontainer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coffetogo.R;
import com.example.coffetogo.data.AppPreferences;
import com.example.coffetogo.databinding.FragmentUserBinding;
import com.example.coffetogo.presentation.signin.SignInFragment;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    private static final String profileIconUrl = "http://192.168.2.219/profileSettings/getIcon";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.login.setText(AppPreferences.getInstance().getLogin());
        binding.mail.setText(AppPreferences.getInstance().getMail());
        binding.signOut.setOnClickListener(button -> {
            AppPreferences.getInstance().clear();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, SignInFragment.newInstance())
                    .commit();
        });
        picassoWithToken();
    }

    private void picassoWithToken() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .authenticator(new Authenticator()
                {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException
                    {

                        return response.request().newBuilder()
                                .header("Authorization", "Bearer " + AppPreferences.getInstance().getToken())
                                .build();
                    }
                }).build();

        Picasso picasso = new Picasso.Builder(requireActivity())
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
        picasso.load(profileIconUrl).into(binding.profileImage);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }
}