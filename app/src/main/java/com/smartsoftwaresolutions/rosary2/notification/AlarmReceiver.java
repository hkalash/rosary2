package com.smartsoftwaresolutions.rosary2.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.smartsoftwaresolutions.rosary2.MainActivity;
import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Splash;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
        // For our recurring task, we'll just display a message
      // Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();


    }
    private void showNotification(Context context) {
       // PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, Splash.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ro_launcher)
                        .setContentTitle(context.getString(R.string.notification_title))// title
                        .setContentText(context.getString(R.string.notification_body));// body
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }
}
