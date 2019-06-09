package com.example.anton.test_task_recycler;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.ArrayList;

public class ApiResponse {
    @SerializedName("message")
    @Expose
    private ArrayList<URL> urls;

    ArrayList<URL> getUrls() {
        return urls;
    }
}
