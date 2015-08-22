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

    PackageManager packageManager;
    LayoutInflater layoutInflater;

    List<App> appList;

    public MonitoringAppAdapter(Context context, int resource, List<App> appList) {
        super(context, resource, appList);
        this.appList = appList;
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

        return view;
    }

    static class AppViewHolder {
        ImageView app_icon;
        TextView app_label;
    }
}
