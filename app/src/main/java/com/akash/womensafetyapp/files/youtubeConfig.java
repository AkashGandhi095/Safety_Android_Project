package com.akash.womensafetyapp.files;

public class youtubeConfig {

    private youtubeConfig()
    {
    }

    private static final String API_KEY = "your api key";

    public static String getApiKey() {
        return API_KEY;
    }
}
