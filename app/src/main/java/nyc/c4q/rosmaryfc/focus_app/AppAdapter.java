package nyc.c4q.rosmaryfc.focus_app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class AppAdapter extends BaseAdapter {

    PackageManager packageManager;
    LayoutInflater layoutInflater;
    List<ApplicationInfo> applicationInfos;

    public AppAdapter(Context context, List<ApplicationInfo> applicationInfos) {
        packageManager = context.getPackageManager();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.applicationInfos = applicationInfos;
    }

    @Override
    public int getCount() {
        return applicationInfos.size();
    }

    @Override
    public ApplicationInfo getItem(int i) {
        return applicationInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.app_row_item, viewGroup, false);
        }
        ApplicationInfo applicationInfo = applicationInfos.get(i);
        ((ImageView) view.findViewById(R.id.app_icon)).setImageDrawable(applicationInfo.loadIcon(packageManager));
        ((TextView) view.findViewById(R.id.app_label)).setText(applicationInfo.loadLabel(packageManager));
        ((Switch) view.findViewById(R.id.app_monitor)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //add to monitor list
                } else {
                    //delete from monitor list, if applicable
                }
            }
        });
        return view;
    }
}
