package com.expert.test.helpers;

import java.net.http.HttpClient;

public class HttpSingleton {

    private static HttpClient instance;

    private HttpSingleton() {
        
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            instance = HttpClient.newHttpClient();
        }
        return instance;
    }
    
}
