package nyc.c4q.rosmaryfc.focus_app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "app.db";
    public static final int DATABASE_VERSION = 1;

    private static DatabaseHelper INSTANCE;

    Context context;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseHelper(context.getApplicationContext());
        }
        return INSTANCE;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, App.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, App.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertData(List<ApplicationInfo> applicationInfos) throws SQLException {
        Dao<App, ?> appDao = getDao(App.class);
        appDao.delete(appDao.deleteBuilder().prepare());

        for (ApplicationInfo applicationInfo : applicationInfos) {
            if (applicationInfo.packageName.equalsIgnoreCase("nyc.c4q.jrs.block_on")) {
                continue;
            }
            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
                    || (((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) && ((applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0))) {
                App app = new App();
                app.setAppPackage(applicationInfo.packageName);
                app.setAppName(applicationInfo.loadLabel(context.getPackageManager()).toString());
                app.setAppMonitor(false);
                getDao(App.class).create(app);
            }
        }
    }

    public void insertData(List<ApplicationInfo> applicationInfos, List<App> monitoringApps) throws SQLException {
        Dao<App, ?> appDao = getDao(App.class);
        appDao.delete(appDao.deleteBuilder().prepare());

        for (ApplicationInfo applicationInfo : applicationInfos) {
            if (applicationInfo.packageName.equalsIgnoreCase("nyc.c4q.jrs.block_on")) {
                continue;
            }
            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
                    || (((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) && ((applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0))) {
                App app = new App();
                app.setAppPackage(applicationInfo.packageName);
                app.setAppName(applicationInfo.loadLabel(context.getPackageManager()).toString());
                app.setAppMonitor(false);
                for (App monitoringApp : monitoringApps) {
                    if (applicationInfo.packageName.equalsIgnoreCase(monitoringApp.getAppPackage())) {
                        app.setAppMonitor(true);
                        break;
                    }
                }
                getDao(App.class).createOrUpdate(app);
            }
        }
    }

    public List<App> loadData() throws SQLException {
        List<App> apps = getDao(App.class).queryForAll();
        return apps;
    }

//    public void deleteApp(String packageName) throws SQLException {
//        Dao<App, ?> appDao = getDao(App.class);
//        DeleteBuilder<App, ?> deleteBuilder = appDao.deleteBuilder();
//        deleteBuilder.where().eq("APP_PACKAGE", packageName);
//        deleteBuilder.delete();
//    }
}