package com.example.coffetogo.data.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("name_icon")
    @Expose
    private String nameIcon;

    public UserInfo(String login, String token, String mail, String nameIcon) {
        this.login = login;
        this.token = token;
        this.mail = mail;
        this.nameIcon = nameIcon;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNameIcon() {
        return nameIcon;
    }

    public void setNameIcon(String nameIcon) {
        this.nameIcon = nameIcon;
    }
}
