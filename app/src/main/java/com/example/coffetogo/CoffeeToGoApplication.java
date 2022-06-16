package com.example.coffetogo;

import android.app.Application;

import com.example.coffetogo.data.AppPreferences;

public class CoffeeToGoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppPreferences.getInstance().initPreferences(getApplicationContext());
    }
}
