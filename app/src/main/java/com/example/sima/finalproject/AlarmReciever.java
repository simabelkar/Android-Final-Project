package com.example.sima.finalproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


public class AlarmReciever extends BroadcastReceiver {
    private int alarmId;
    private AlarmManager alarmManager;
    private SharedPreferences sharedPreferences;//replace to DB
    private SharedPreferences.Editor spEditor;

    @Override
    public void onReceive(Context context,Intent intent){
        Intent service=new Intent(context, MyIntentService.class);
        //select from DB
      /*  service.putExtra(context.getString(R.string.phone_number),intent.getStringExtra(context.getString(R.string.phone_number)));   //set sending details to MyIntentService
        service.putExtra(context.getString(R.string.message_to_send), intent.getStringExtra(context.getString(R.string.message_to_send)));
        service.putExtra(context.getString(R.string.receiver_name), intent.getStringExtra(context.getString(R.string.receiver_name)));
        service.putExtra(context.getString(R.string.sp_message), intent.getStringExtra(context.getString(R.string.sp_message)));
       */ context.startService(service);
    }

    public AlarmReciever(){}

    public void setAlarm(Context context, Long timeInMills){

        Intent i=new Intent(context,AlarmReciever.class);
        PendingIntent pi=PendingIntent.getBroadcast(context,alarmId,i,0);
        alarmManager=(AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMills,alarmManager.INTERVAL_DAY*7,pi);

    }

    public void cancelAlarm(Context context){
        Intent intent=new Intent(context,AlarmReciever.class);
        PendingIntent sender=PendingIntent.getBroadcast(context,alarmId,intent,0);
        alarmManager.cancel(sender);
    }
}
