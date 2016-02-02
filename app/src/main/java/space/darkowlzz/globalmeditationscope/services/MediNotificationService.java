package space.darkowlzz.globalmeditationscope.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import space.darkowlzz.globalmeditationscope.R;

/**
 * Created by sunny on 28/11/15.
 */
public class MediNotificationService extends IntentService {

    public static final String TAG = "MediNotificationSvc";

    NotificationManager notificationManager;
    NotificationCompat.Builder mBuilder;

    public MediNotificationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        Integer notiID = extras.getInt("eventID");

        Intent scopeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(extras.getString("eventURI")));

        mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("GMS: " + extras.getString("eventHost") + " is Live!")
                //.setContentText(extras.getString("eventTitle"))
                .setContentText("Touch to view.")
                .setTicker("GMS Live")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, scopeIntent, 0);
        mBuilder.setContentIntent(pendingIntent);

        notificationManager.notify(notiID, mBuilder.build());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
}
