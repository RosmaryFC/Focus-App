package nyc.c4q.rosmaryfc.focus_app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;

public class AppTracker extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 8);
        Intent showNotification = new Intent(this, BlockSessionReminder.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, showNotification, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
