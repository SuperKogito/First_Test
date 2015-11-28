package com.trustcase.client.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {
    public final static String API_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private static GsonProvider instance;

    public static GsonProvider getInstance() {
        if (instance == null) {
            instance = new GsonProvider();
        }
        return instance;
    }

    public Gson getStandardGson() {
        return new GsonBuilder()
              .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
              .setDateFormat(API_DATE_PATTERN)
              .create();
    }
}
