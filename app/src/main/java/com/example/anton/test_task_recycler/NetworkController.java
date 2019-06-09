package com.example.anton.test_task_recycler;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkController {
    private static NetworkController mInstance;
    private static final String BASE_URL = "https://dog.ceo/api/breeds/image/random/";
    private Retrofit mRetrofit;

    private NetworkController(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static NetworkController getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkController();
        }
        return mInstance;
    }

    public JSONDogApi getJSONApi() {
        return mRetrofit.create(JSONDogApi.class);
    }
}
