package nyc.c4q.rosmaryfc.focus_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class AppReceiver extends BroadcastReceiver {

    DatabaseHelper databaseHelper;

    PackageManager packageManager;
    List<ApplicationInfo> applicationInfos;
//    List<App> apps;
    List<App> monitoringApps;

    @Override
    public void onReceive(Context context, Intent intent) {
        databaseHelper = DatabaseHelper.getInstance(context);
        packageManager = context.getPackageManager();
        applicationInfos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        updateApps();
    }

    public void updateApps() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Dao<App, ?> appDao = databaseHelper.getDao(App.class);
                    monitoringApps = appDao.query(appDao.queryBuilder().where().eq("APP_MONITOR", true).prepare());
                    Collections.sort(applicationInfos, new ApplicationInfo.DisplayNameComparator(packageManager));
                    databaseHelper.insertData(applicationInfos, monitoringApps);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

//        public void updateApps() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Dao<App, ?> appDao = databaseHelper.getDao(App.class);
//                    apps = appDao.queryForAll();
//                    for (App app : apps) {
//                        boolean installed = appInstalledOrNot(app.getAppPackage());
//                        if (!installed) {
//                            databaseHelper.deleteApp(app.getAppPackage());
//                        }
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    public boolean appInstalledOrNot(String packageName) {
//        boolean appInstalled;
//        try {
//            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
//            appInstalled = true;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            appInstalled = false;
//        }
//        return appInstalled;
//    }
}
