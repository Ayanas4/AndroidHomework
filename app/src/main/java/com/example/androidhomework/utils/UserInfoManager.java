package com.example.androidhomework.utils;

import static com.example.androidhomework.utils.Constants.PREF_TOKEN;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserInfoManager {


    private UserInfoManager() {
    }

    private static class LazyHolder {
        static final UserInfoManager INSTANCE = new UserInfoManager();
    }

    public static UserInfoManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void setToken(Context context, String token) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_TOKEN, token).apply();
    }

    public String getToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PREF_TOKEN, "");
    }
}
