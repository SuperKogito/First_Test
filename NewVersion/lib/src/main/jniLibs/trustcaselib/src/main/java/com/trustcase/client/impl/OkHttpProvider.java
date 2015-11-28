package com.trustcase.client.impl;

import java.util.concurrent.TimeUnit;

import retrofit.client.Client;
import retrofit.client.OkClient;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by audrius on 12.11.15.
 */
public class OkHttpProvider implements Client.Provider {
    private final static String CONNECT_TIMEOUT_PROPERTY = "okhttp.connect.timeout";
    private final static String READ_TIMEOUT_PROPERTY = "okhttp.read.timeout";

    private final static int DEFAULT_CONNECT_TIMEOUT = 5000;
    private final static int DEFAULT_READ_TIMEOUT = 10000;

    @Override
    public Client get() {
        int connectTimeout, readTimeout;
        try {
            connectTimeout = Integer.parseInt(System.getProperty(CONNECT_TIMEOUT_PROPERTY));
        } catch (Exception ex) {
            connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        }
        try {
            readTimeout = Integer.parseInt(System.getProperty(READ_TIMEOUT_PROPERTY));
        } catch (Exception ex) {
            readTimeout = DEFAULT_READ_TIMEOUT;
        }

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        client.setReadTimeout(readTimeout, TimeUnit.MILLISECONDS);
        return new OkClient(client);
    }
}
