package android.reserver.c196v6.Receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.reserver.c196v6.MainActivity;
import android.reserver.c196v6.R;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

/**
 *
 */
public class AlarmReceiver extends BroadcastReceiver {
    private String CHANNEL_ID = "app_notification_channel";
    private String CHANNEL_DESC = "WGU Alert Channel";

    /**
     *
     * @param context
     * @param intent
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        //retrieve stored(in intent) alert data
        String mNotificationTitle = intent.getStringExtra("mNotificationTitle");
        String mNotificationContent = intent.getStringExtra("mNotificationContent");
        Intent resultIntent = new Intent(context, MainActivity.class);
        //build artificial TaskStack to retain up functionality on app bar
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(1, PendingIntent.FLAG_IMMUTABLE);
        //alert build
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID);

        mBuilder.setSmallIcon(R.drawable.ic_alarm_reminder);
        mBuilder.setContentTitle(mNotificationTitle);
        mBuilder.setContentText(mNotificationContent);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //build alert channel
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_DESC, NotificationManager.IMPORTANCE_DEFAULT);
        mNotificationManager.createNotificationChannel(channel);
        //trigger the alert like Tiger alerts his bounce
        mNotificationManager.notify(0, mBuilder.build());
    }
}
