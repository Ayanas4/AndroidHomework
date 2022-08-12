package com.example.androidhomework.http;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class JsonUtility {

    public static Gson gson = new GsonBuilder().create();

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static String getResponseStatus(String jsonObj) {
        return getJsonValue(jsonObj, "success");
    }

    public static String getResponseMessage(String jsonObj) {
        return getJsonValue(jsonObj, "message");
    }

    public static String getResponseData(String jsonObj) {
        return getJsonValue(jsonObj, "data");
    }

    public static String getResponseError(String jsonObj) {
        return getJsonValue(jsonObj, "error");
    }

    public static String getResponseAuth(String jsonObj) {
        return getJsonValue(jsonObj, "auth");
    }

    public static String getJsonValue(String jsonObj, String key) {
        String value = "";
        try {
            JSONObject jsonObject = new JSONObject(jsonObj);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return value;
    }

}
