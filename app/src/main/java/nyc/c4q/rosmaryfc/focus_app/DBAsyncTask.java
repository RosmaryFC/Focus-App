package nyc.c4q.rosmaryfc.focus_app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class DBAsyncTask extends AsyncTask<PackageManager, Void, Void> {

    Context context;

    DatabaseHelper databaseHelper = null;

    public DBAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(PackageManager... packageManagers) {
        PackageManager packageManager = packageManagers[0];
        List<ApplicationInfo> applicationInfos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        Collections.sort(applicationInfos, new ApplicationInfo.DisplayNameComparator(packageManager));

        for (ApplicationInfo applicationInfo : applicationInfos) {
            if (!applicationInfo.packageName.isEmpty()) {
                try {
                    Dao<App, Integer> appInfoDao = getHelper().getAppInfoDao();
                    App app = new App();
                    app.setAppPackage(applicationInfo.packageName);
                    app.setAppName(applicationInfo.loadLabel(packageManager).toString());
                    app.setAppMonitor(false);
                    appInfoDao.create(app);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
