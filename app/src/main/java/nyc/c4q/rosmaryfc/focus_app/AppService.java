package nyc.c4q.rosmaryfc.focus_app;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AppService extends Service {

    private static final long UPDATE_INTERVAL = 30 * 1000; //30 seconds

    Timer timer;

    ActivityManager activityManager;
    DatabaseHelper databaseHelper;
    List<App> monitoringApps;
    boolean isLollipop, isInForeground;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            isLollipop = true;
        } else {
            isLollipop = false;
        }
        databaseHelper = DatabaseHelper.getInstance(this);
        activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (timer != null) {
            timer.cancel();
        } else {
            timer = new Timer();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Dao<App, ?> appDao = databaseHelper.getDao(App.class);
            monitoringApps = appDao.query(appDao.queryBuilder().where().eq("APP_MONITOR", true).prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        startService();

        return START_STICKY;
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

        isInForeground = false;

        for (App app : monitoringApps) {
            String packageName = app.getAppPackage();
            if (isLollipop) {
                List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();
                for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (String activeProcess : processInfo.pkgList) {
                            if (activeProcess.equals(packageName)) {
                                isInForeground = true;
                            } else if (activeProcess.equals("nyc.c4q.rosmaryfc.focus_app")) {
                                isInForeground = false;
                            }
                        }
                    }
                }
            } else {
                List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
                ComponentName componentInfo = runningTaskInfos.get(0).topActivity;
                if (componentInfo.getPackageName().equals(packageName)) {
                    isInForeground = true;
                }
            }
        }

        if (isInForeground) {
            Intent showApp = new Intent(getApplicationContext(), AppUsage.class);
            showApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(showApp);
        }
    }


    private void shutdownService() {
        if (timer != null)
            timer.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        shutdownService();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}