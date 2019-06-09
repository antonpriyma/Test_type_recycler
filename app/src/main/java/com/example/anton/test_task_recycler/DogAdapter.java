package com.example.anton.test_task_recycler;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    static final private int ITEM_DOG = R.layout.recycler_item;
    private ArrayList<Item> list;
    boolean isLoading = false;


    DogAdapter(ArrayList<Item> list){
        this.list=list;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        if (viewType==VIEW_ITEM) {
            View cardView = LayoutInflater.from(context).inflate(ITEM_DOG, parent, false);
            return new DogViewHolder(cardView);
        }else {
            RecyclerView.ViewHolder vh;
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false);

            vh = new ProgressViewHolder(v);
            return vh;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DogViewHolder) {
            Item item = list.get(position);
            DogViewHolder dogViewHolder = ((DogViewHolder) holder);
            URL url = item.getUrl();
            String title = item.getName();
            Context context = holder.itemView.getContext();
            dogViewHolder.textView.setText(title);
            Glide.with(context).load(url).into(dogViewHolder.imageView);
        }else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

class DogViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;


    DogViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image);
        textView = itemView.findViewById(R.id.text);
    }
}

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }



    ArrayList<Item> getList() {
        return list;
    }
}//TODO: добавить загрузку из интернета. Прочитать про это + про Recycler View освежить


