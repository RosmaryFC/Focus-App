package nyc.c4q.rosmaryfc.focus_app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionDBHelper;

public class BlockService extends Service {

    public static final int ID_FRIENDLY_NOTIFICATION = 1;

    private static final long UPDATE_INTERVAL = 30 * 1000; //30 seconds

    Timer timer;

    int startHour, startMinute, endHour, endMinute;
    long currentTime, startTime, endTime, fiveMinute;

    @Override
    public void onCreate() {
        super.onCreate();
        if (timer == null) {
            timer = new Timer();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startService() {
        timer.scheduleAtFixedRate(

                new TimerTask() {

                    public void run() {

                        doServiceWork();

                    }
                }, 0, UPDATE_INTERVAL);
    }

    private void doServiceWork() {
        Calendar currentCalendar = Calendar.getInstance();
        currentTime = currentCalendar.getTimeInMillis();

        getUpcomingBSInfo();

        //query next block session from db and set time values
//        startHour = 17;
//        startMinute = 24;
//        endHour = 17;
//        endMinute = 59;

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.HOUR_OF_DAY, startHour);
        startCalendar.set(Calendar.MINUTE, startMinute);
        startTime = startCalendar.getTimeInMillis();
        startCalendar.add(Calendar.MINUTE, -5);
        fiveMinute = startCalendar.getTimeInMillis();

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.HOUR_OF_DAY, endHour);
        endCalendar.set(Calendar.MINUTE, endMinute);
        endTime = endCalendar.getTimeInMillis();

        if (currentTime >= fiveMinute && currentTime < startTime) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setSmallIcon(R.drawable.notification_template_icon_bg);
            builder.setContentTitle("Session Block Reminder");
            builder.setContentText("You're focus session will begin in 5 minutes");
            Notification notification = builder.build();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
            notificationManager.notify(ID_FRIENDLY_NOTIFICATION, notification);
        } else if (currentTime >= startTime && currentTime < endTime) {
            Intent startMonitoring = new Intent(this, AppService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, startMonitoring, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);
        } else {

        }
    }


    public void getUpcomingBSInfo () {

        BlockSessionDBHelper db = new BlockSessionDBHelper(this);

        BlockSession nextBlockSession = db.upcomingBlockSession();

        String BSStartTimeInfo = nextBlockSession.getStartTime();
        String[] startTimeArr = BSStartTimeInfo.split("\\:");
        startHour = Integer.parseInt(startTimeArr[0]);
        startMinute = Integer.parseInt(startTimeArr[1]);

        String BSEndTimeInfo = nextBlockSession.getEndTime();
        String[] endTimeArr = BSEndTimeInfo.split("\\:");
        endHour = Integer.parseInt(endTimeArr[0]);
        endMinute = Integer.parseInt(endTimeArr[1]);
    }


}
