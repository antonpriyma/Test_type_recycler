package com.example.anton.test_task_recycler;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DogAdapter adapter;
    private ArrayList<Item> list;
    private LinearLayoutManager layoutManager;
    private OnLoadMoreDogsListener onLoadMoreListener;
    private RecyclerView recyclerView;

    int lastVisibleItem, visibleItemCount, totalItemCount;

    public final static String LIST_STATE_KEY = "recycler_list_state";
    public final static String LIST_STATE_DATA = "recycler_list_data";
    Parcelable listState;

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        // Save list state
        listState = layoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, listState);
        state.putParcelableArrayList(LIST_STATE_DATA,list);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if(state != null)
            listState = state.getParcelable(LIST_STATE_KEY);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            list = new ArrayList<>();
        }else {
            restorePreviousState(savedInstanceState);
        }
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"OnCreate",Toast.LENGTH_LONG).show();
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new DogAdapter(list);
        layoutManager=new LinearLayoutManager(getApplicationContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        onLoadMoreListener = new OnLoadMoreDogsListener(adapter.getList(),recyclerView);
        if (list.size()==0) {
            get_start_data();
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                visibleItemCount = layoutManager.getChildCount();
                if (dy > 0) {
                    if (!onLoadMoreListener.isLoading()) {
                        if (lastVisibleItem + 1 >= totalItemCount) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Last item!", Toast.LENGTH_LONG);
                            toast.show();
                            Log.v("Recycler", "Last item!");
                            Log.v("Recycler", "Child items:" + totalItemCount);
                            Log.v("Recycler", "Last Item:" + lastVisibleItem);
                            list.add(null);
                            recyclerView.getAdapter().notifyItemInserted(list.size()-1);
                            onLoadMoreListener.onLoadMore();





                        }
                    }
                }
            }
        });

       // startAlarm(true,true);
    }

    private void restorePreviousState(Bundle state) {
        list=state.getParcelableArrayList(LIST_STATE_DATA);
        listState=state.getParcelable(LIST_STATE_KEY);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings_item:
                showSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSettings() {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }

    private void get_start_data() {
        list.add(null);
        recyclerView.getAdapter().notifyItemInserted(list.size()-1);
        onLoadMoreListener.onLoadMore();
    }

    private void startAlarm(boolean isNotification, boolean isRepeat) {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        // SET TIME HERE
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,20);


        myIntent = new Intent(MainActivity.this,AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0 );



        if(!isRepeat)
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
        else
            manager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), 6000,pendingIntent);
    }
}







