package nyc.c4q.rosmaryfc.focus_app;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nyc.c4q.rosmaryfc.focus_app.ui.FocusSessionActivity;

/**
 * Created by s3a on 8/25/15.
 */
public class MonitoringAppsFragment extends Fragment {

    DatabaseHelper databaseHelper;

    List<App> monitoringApps;

    ListView monitoring_app_list;

    Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        databaseHelper = DatabaseHelper.getInstance(mContext);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitoring_apps, container, false);
        monitoring_app_list = (ListView) view.findViewById(R.id.monitoring_app_list);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mContext != null) {
            if (databaseHelper != null) {
                try {
                    Dao<App, ?> appDao = databaseHelper.getDao(App.class);
                    monitoringApps = appDao.query(appDao.queryBuilder().where().eq("APP_MONITOR", true).prepare());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (monitoringApps.size() != 0) {
                MonitoringAppAdapter adapter = new MonitoringAppAdapter(mContext, monitoringApps);
                monitoring_app_list.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_monitoring_apps, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_app:
                Intent intent = new Intent (mContext, AppMonitor.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                //
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

