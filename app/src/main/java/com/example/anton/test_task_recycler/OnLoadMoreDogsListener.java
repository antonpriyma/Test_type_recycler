package com.example.anton.test_task_recycler;

import android.support.v7.widget.RecyclerView;

import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnLoadMoreDogsListener implements OnLoadMoreListener {
    private ArrayList<Item> list;
    private RecyclerView recyclerView;
    private boolean isLoading;

    boolean isLoading(){
        return isLoading;
    }


    public OnLoadMoreDogsListener(ArrayList<Item> list,RecyclerView recyclerView){
        this.list=list;
        this.recyclerView=recyclerView;
        isLoading=false;
    }
    @Override
    public void onLoadMore() {
        isLoading=true;
            NetworkController.getInstance().getJSONApi().getDog("10").enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        list.remove(list.size()-1);
                        ApiResponse responseBody = response.body();
                        for (URL url: responseBody.getUrls()){
                            list.add(new Item(url));
                        }

                        recyclerView.getAdapter().notifyDataSetChanged();

                    }
                    isLoading=false;

                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {

                }
            });



    }
}
