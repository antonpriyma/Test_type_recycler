package com.example.anton.test_task_recycler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.Calendar;

public class MyService extends Service {
    private static final String NOTIFICATION_STATE = "notification_state";
    private boolean notificationState;
    private Intent myIntent;
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (manager!=null && pendingIntent!=null){
            manager.cancel(pendingIntent);
            Log.d("AlarmService","Stop Notifications");

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private void stopAlarm() {
        if (manager!=null && pendingIntent!=null){
            manager.cancel(pendingIntent);
            Log.d("AlarmService","Stop Notifications");

        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationState=(boolean)intent.getExtras().get(NOTIFICATION_STATE);
        if (notificationState){
            startAlarm(true,true);
        }else {
            stopAlarm();
        }

        return START_NOT_STICKY;

    }

    private void startAlarm(boolean isNotification, boolean isRepeat) {
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);


        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,20);


        myIntent = new Intent(getApplicationContext(),AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0 );



        if(!isRepeat)
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
        else
            manager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), 30000,pendingIntent);
    }
}