package nyc.c4q.rosmaryfc.focus_app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.sql.SQLException;
import java.util.List;

public class AppAdapter extends ArrayAdapter<App> {

    Context context;
    PackageManager packageManager;
    LayoutInflater layoutInflater;

    DatabaseHelper databaseHelper;

    List<App> apps;
    App app;

    public AppAdapter(Context context, List<App> apps) {
        super(context, R.layout.app_row_item, apps);
        this.apps = apps;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        packageManager = context.getPackageManager();
    }

    static class AppViewHolder {
        ImageView app_icon;
        TextView app_label;
        Switch app_monitor;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AppViewHolder appViewHolder;

        databaseHelper = DatabaseHelper.getInstance(context);

        if (view == null) {
            view = layoutInflater.inflate(R.layout.app_row_item, viewGroup, false);
            appViewHolder = new AppViewHolder();
            appViewHolder.app_icon = (ImageView) view.findViewById(R.id.app_icon);
            appViewHolder.app_label = (TextView) view.findViewById(R.id.app_label);
            appViewHolder.app_monitor = (Switch) view.findViewById(R.id.app_monitor);

            appViewHolder.app_monitor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        int getPosition = (Integer) compoundButton.getTag();
                        app = apps.get(getPosition);
                        app.setAppMonitor(true);
                        try {
                            databaseHelper.getDao(App.class).update(app);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        int getPosition = (Integer) compoundButton.getTag();
                        app = apps.get(getPosition);
                        app.setAppMonitor(false);
                        try {
                            databaseHelper.getDao(App.class).update(app);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            view.setTag(appViewHolder);
            view.setTag(R.id.app_label, appViewHolder.app_label);
            view.setTag(R.id.app_monitor, appViewHolder.app_monitor);
        } else {
            appViewHolder = (AppViewHolder) view.getTag();
        }

        app = apps.get(i);

        appViewHolder.app_monitor.setTag(i);

        appViewHolder.app_label.setText(app.getAppName());
        appViewHolder.app_monitor.setChecked(app.getAppMonitor());


        Drawable icon = null;

        try {
            ApplicationInfo appInfo = packageManager.getApplicationInfo(app.getAppPackage(), 0);
            icon = packageManager.getApplicationIcon(appInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (icon != null) {
            appViewHolder.app_icon.setImageDrawable(icon);
        }

        return view;
    }

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public App getItem(int i) {
        return apps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
