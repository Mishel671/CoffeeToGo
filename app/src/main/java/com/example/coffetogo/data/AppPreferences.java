package com.example.coffetogo.data;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private static final String NAME_PREF = "preferences";
    private static final String TOKEN_KEY = "token";
    private static final String MAIL_KEY = "mail";
    private static final String LOGIN_KEY = "login";

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

    public void clear(){
        preferences.edit().clear().apply();
    }

    public void setToken(String token) {
        preferences.edit().putString(TOKEN_KEY, token).apply();
    }

    public String getToken() {
        return preferences.getString(TOKEN_KEY, "");
    }

    public void setMail(String mail) {
        preferences.edit().putString(MAIL_KEY, mail).apply();
    }

    public String getMail() {
        return preferences.getString(MAIL_KEY, "");
    }

    public void setLogin(String login) {
        preferences.edit().putString(LOGIN_KEY, login).apply();
    }

    public String getLogin() {
        return preferences.getString(LOGIN_KEY, "");
    }

}
