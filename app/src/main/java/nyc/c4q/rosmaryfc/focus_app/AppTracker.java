package nyc.c4q.rosmaryfc.focus_app;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class AppTracker extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED_PREFOCUS = 1;
    public static final int STATUS_FINISHED_STARTFOCUS = 2;

    int startHour, startMinute, endHour, endMinute;
    long currentTime, startTime, endTime, fiveMinute;
    boolean isLollipop, isInForeground;

    DatabaseHelper databaseHelper;
    Dao<App, Integer> appInfoDao;

    List<App> apps;

    public AppTracker() {
        super("AppTracker");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            isLollipop = true;
        } else {
            isLollipop = false;
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ResultReceiver appReceiver = intent.getParcelableExtra("receiver");
        //get values from block time db
        startHour = intent.getIntExtra("start_hour", 0);
        startMinute = intent.getIntExtra("start_minute", 0);
        endHour = intent.getIntExtra("end_hour", 0);
        endMinute = intent.getIntExtra("end_minute", 0);

        Calendar currentCalendar = Calendar.getInstance();
        currentTime = currentCalendar.getTimeInMillis();

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

        Bundle bundle = new Bundle();

        if (currentTime >= fiveMinute && currentTime < startTime) {
            //5 minute reminder
            bundle.putString("notification", "Your next focus session will start in 5 minutes");
            bundle.putBoolean("foreground", false);
            appReceiver.send(STATUS_FINISHED_PREFOCUS, bundle);
        } else if (currentTime >= startTime && currentTime < endTime) {
            //block time
            ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

            //get list of apps to be monitored
            try {
                appInfoDao = getHelper().getAppInfoDao();
                apps = appInfoDao.queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            isInForeground = false;

            for (App app : apps) {
                String packageName = app.getAppPackage();
                if (isLollipop) {
                    List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
                    for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                        if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            for (String activeProcess : processInfo.pkgList) {
                                if (activeProcess.equals(packageName)) {
                                    isInForeground = true;
                                }
                            }
                        }
                    }
                } else {
                    List<ActivityManager.RunningTaskInfo> runningTaskInfos = am.getRunningTasks(1);
                    ComponentName componentInfo = runningTaskInfos.get(0).topActivity;
                    if (componentInfo.getPackageName().equals(packageName)) {
                        isInForeground = true;
                    }
                }

                bundle.putString("notification", "Focus On");
                bundle.putBoolean("foreground", isInForeground);
                appReceiver.send(STATUS_FINISHED_STARTFOCUS, bundle);
            }
        } else {
            this.stopSelf();
        }
        this.stopSelf();
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
