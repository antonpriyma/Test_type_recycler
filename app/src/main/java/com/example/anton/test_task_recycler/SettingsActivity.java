package com.example.anton.test_task_recycler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    private static final String NOTIFICATION_STATE = "notification_state";
    private boolean notificationState=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(NOTIFICATION_STATE, 0);
        notificationState = settings.getBoolean("switchkey", false);
        setContentView(R.layout.activity_settings);
        setListener();
    }

    private void setListener() {
        Switch sw = (Switch)findViewById(R.id.switch_notification);
        sw.setChecked(notificationState);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Intent i = new Intent(getApplicationContext(),NotificationService.class);
                    i.putExtra(NOTIFICATION_STATE,true);
                    getApplicationContext().startService(i);
                    notificationState=true;
                } else {
                    Intent i = new Intent(getApplicationContext(),NotificationService.class);
                    getApplicationContext().stopService(i);
                    i.putExtra(NOTIFICATION_STATE,false);
                    getApplicationContext().startService(i);
                    notificationState=false;
                }
                SharedPreferences settings = getSharedPreferences(NOTIFICATION_STATE, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("switchkey", isChecked);
                editor.commit();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(NOTIFICATION_STATE,notificationState);
    }
}
