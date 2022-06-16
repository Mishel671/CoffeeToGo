package com.example.coffetogo.data;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private static final String NAME_PREF = "preferences";
    private static final String TOKEN_KEY = "token";

    private SharedPreferences preferences;

    private AppPreferences() {
    }

    private static AppPreferences appPreferences;

    public static AppPreferences getInstance() {
        if (appPreferences == null) {
            appPreferences = new AppPreferences();
        }
        return appPreferences;
    }

    public void initPreferences(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE);
        }
    }

    public void setToken(String token) {
        preferences.edit().putString(TOKEN_KEY, token).apply();
    }

    public String getToken() {
        return preferences.getString(TOKEN_KEY, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo4MDgwLyIsImlzcyI6Imh0dHA6Ly8wLjAuMC4wOjgwODAvIiwibG9naW4iOiJyb290In0.GTSOsAV92PeJ0rwyqYsjHDJaj-Q-XdkrO1A08BRE560");
    }
}
