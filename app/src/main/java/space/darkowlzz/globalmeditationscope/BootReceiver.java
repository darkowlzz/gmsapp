package space.darkowlzz.globalmeditationscope;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by sunny on 28/11/15.
 */
public class BootReceiver extends BroadcastReceiver {

    TinyDB tinyDB;
    Context ctx;

    @Override
    public void onReceive(Context context, Intent intent) {
        ctx = context;
        tinyDB = new TinyDB(context);
        ArrayList<MediEvent> events = tinyDB.getListObject(MainActivity.FAVORITE_EVENTS, MediEvent.class);
        DateTime now = new DateTime();
        for (MediEvent event : events) {
            if (event.getDateObj().isAfter(now)) {
                setReminder(event);
            }
        }
    }

    public void setReminder(MediEvent mediEvent) {
        int reminderCount = tinyDB.getInt(MainActivity.REMINDER_COUNTER) + 1;
        tinyDB.putInt(MainActivity.REMINDER_COUNTER, reminderCount);

        Intent intent = new Intent(ctx, MediNotificationService.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("eventID", mediEvent.eventID);
        dataBundle.putString("eventTitle", mediEvent.title);
        dataBundle.putString("eventHost", mediEvent.hostName);
        dataBundle.putString("eventURI", mediEvent.getPeriUri());
        intent.putExtras(dataBundle);

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(ctx, mediEvent.eventID, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, mediEvent.getDateObj().getMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, mediEvent.getDateObj().getMillis(), pendingIntent);
        }
    }
}
