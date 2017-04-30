package com.example.sima.finalproject;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.NotificationCompat;



public class MyIntentService extends IntentService {



    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //send notification
        NotificationManager nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setDefaults(Notification.DEFAULT_ALL);
       // builder.setSmallIcon(R.mipmap.logo2xs);
        builder.setContentTitle("Show Time");
        //
        builder.setContentText("SMS sent");
        int id=0;
        nm.notify(id, builder.build());
    }
}