package nyc.c4q.rosmaryfc.focus_app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MonitoringAppAdapter extends ArrayAdapter<App> {

    Context context;
    PackageManager packageManager;
    LayoutInflater layoutInflater;

    List<App> apps;

    public MonitoringAppAdapter(Context context, List<App> apps) {
        super(context, R.layout.monitoring_app_row_item, apps);
        this.apps = apps;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        packageManager = context.getPackageManager();
    }

    static class AppViewHolder {
        ImageView app_icon;
        TextView app_label;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AppViewHolder appViewHolder;
        App app;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.monitoring_app_row_item, viewGroup, false);
            appViewHolder = new AppViewHolder();
            appViewHolder.app_icon = (ImageView) view.findViewById(R.id.app_icon);
            appViewHolder.app_label = (TextView) view.findViewById(R.id.app_label);
            view.setTag(appViewHolder);
            view.setTag(R.id.app_label, appViewHolder.app_label);
        } else {
            appViewHolder = (AppViewHolder) view.getTag();
        }

        app = apps.get(i);

        appViewHolder.app_label.setText(app.getAppName());

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
