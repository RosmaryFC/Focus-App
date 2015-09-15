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
    List<App> apps;

    @Override
    public void onReceive(Context context, Intent intent) {
        databaseHelper = DatabaseHelper.getInstance(context);
        packageManager = context.getPackageManager();
        applicationInfos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        updateApps(intent.getAction());
    }

    public void updateApps(final String action) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Dao<App, ?> appDao = databaseHelper.getDao(App.class);
                    apps = appDao.queryForAll();
                    Collections.sort(applicationInfos, new ApplicationInfo.DisplayNameComparator(packageManager));
                    if (action.equalsIgnoreCase(Intent.ACTION_PACKAGE_ADDED) || action.equalsIgnoreCase(Intent.ACTION_PACKAGE_INSTALL) || action.equalsIgnoreCase(Intent.ACTION_PACKAGE_REPLACED)) {
                        //add app
                    } else if (action.equalsIgnoreCase(Intent.ACTION_PACKAGE_FULLY_REMOVED) || action.equalsIgnoreCase(Intent.ACTION_PACKAGE_REMOVED)) {
                        //delete app
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
