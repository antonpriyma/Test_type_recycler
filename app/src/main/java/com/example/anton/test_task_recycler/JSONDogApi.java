package com.example.anton.test_task_recycler;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONDogApi {
    @GET("{option}")
    Call<ApiResponse> getDog(@Path("option") String option);
}
