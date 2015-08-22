package nyc.c4q.rosmaryfc.focus_app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

public class AppAdapter extends ArrayAdapter<App> {

    Context context;
    PackageManager packageManager;
    LayoutInflater layoutInflater;

    DatabaseHelper databaseHelper = null;
    Dao<App, Integer> appInfoDao;

    List<App> appList;

    public AppAdapter(Context context, int resource, List<App> appList, Dao<App, Integer> appInfoDao) {
        super(context, resource, appList);
        this.appList = appList;
        this.appInfoDao = appInfoDao;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        packageManager = context.getPackageManager();
    }

    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public App getItem(int i) {
        return appList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AppViewHolder appViewHolder;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.app_row_item, viewGroup, false);
            appViewHolder = new AppViewHolder();
            appViewHolder.app_icon = (ImageView) view.findViewById(R.id.app_icon);
            appViewHolder.app_label = (TextView) view.findViewById(R.id.app_label);
            appViewHolder.app_monitor = (Switch) view.findViewById(R.id.app_monitor);
            view.setTag(appViewHolder);
        } else {
            appViewHolder = (AppViewHolder) view.getTag();
        }

        final App appInfo = appList.get(i);
        Drawable icon = null;
        try {
            ApplicationInfo app = packageManager.getApplicationInfo(appInfo.getAppPackage(), 0);
            icon = packageManager.getApplicationIcon(app);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (icon != null) {
            appViewHolder.app_icon.setImageDrawable(icon);
        }
        appViewHolder.app_label.setText(appInfo.getAppName());
        appViewHolder.app_monitor.setChecked(appInfo.getAppMonitor());

        appViewHolder.app_monitor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    try {
                        Dao<App, Integer> appInfoDao = getHelper().getAppInfoDao();
                        UpdateBuilder<App, Integer> updateBuilder = appInfoDao.updateBuilder();
                        updateBuilder.updateColumnExpression("APP_MONITOR", "true");
                        updateBuilder.where().eq("APP_PACKAGE", appInfo.getAppPackage());
                        updateBuilder.update();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Dao<App, Integer> appInfoDao = getHelper().getAppInfoDao();
                        UpdateBuilder<App, Integer> updateBuilder = appInfoDao.updateBuilder();
                        updateBuilder.updateColumnExpression("APP_MONITOR", "false");
                        updateBuilder.where().eq("APP_PACKAGE", appInfo.getAppPackage());
                        updateBuilder.update();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }

    static class AppViewHolder {
        ImageView app_icon;
        TextView app_label;
        Switch app_monitor;
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
