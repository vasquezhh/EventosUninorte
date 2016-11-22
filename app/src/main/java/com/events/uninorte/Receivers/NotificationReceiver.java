package com.events.uninorte.Receivers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.events.uninorte.Activities.ShowEventActivity;
import com.events.uninorte.Activities.SplashScreenActivity;
import com.events.uninorte.Models.EventModel;
import com.events.uninorte.R;

/**
 * Created by hainerv on 30/10/16.
 */

public class NotificationReceiver extends BroadcastReceiver {

    public static void SetAlarm(Context context, long millis, EventModel eventModel) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("name", eventModel.getTitle());
        intent.putExtra("url", eventModel.getLink());
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, millis, broadcast);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String name;
        if (intent.hasExtra("name")) {
            name = "El evento " + intent.getStringExtra("name") + " es por comenzar";
        } else {
            name = "Te recordamos que tu evento esta por comenzar";
        }
        Intent notificationIntent = new Intent(context, SplashScreenActivity.class);
        if (intent.hasExtra("url")) {
            notificationIntent.putExtra("url", intent.getStringExtra("url"));
        }
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(SplashScreenActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.setContentTitle("Recordatorio de tu evento")
                .setContentText(name)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.icono)
                .setContentIntent(pendingIntent).build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
