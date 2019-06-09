package com.example.anton.test_task_recycler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "channel_1";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = null;
        Log.v("AlarmReceiver","Notification");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, "Dogs Channel", NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription("Dogs Channel");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            nm.createNotificationChannel(channel);
            Intent nextIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0, nextIntent, 0);

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context, CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Напоминание")
                            .setContentText("Новые собаки ждут!")
                            .setContentIntent(pendingIntent);
            Notification notification = builder.build();
            nm.notify(1,notification);

        }else {
            Intent nextIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0, nextIntent, 0);

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context, CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Напоминание")
                            .setContentText("Новые собаки ждут!")
                            .setContentIntent(pendingIntent);
            Notification notification = builder.build();
            nm.notify(1,notification);
        }
    }

//    private void loadDog(final Context context){
//
//
//        NetworkController.getInstance().getJSONApi().getDog("1").enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (response.isSuccessful()) {
//                    ApiResponse responseBody = response.body();
//                    for (URL url: responseBody.getUrls()){
//
//                            Picasso.get().load(url.toString()).into(new Target() {
//                                @Override
//                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                                    dogBitmap=bitmap;
//                                }
//
//                                @Override
//                                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                                }
//
//
//                                @Override
//                                public void onPrepareLoad(Drawable placeHolderDrawable) {}
//                            });
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//
//            }
//        });

}