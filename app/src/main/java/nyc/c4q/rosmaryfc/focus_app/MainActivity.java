package nyc.c4q.rosmaryfc.focus_app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PackageManager packageManager;
    ListView monitoring_app_list;

    DatabaseHelper databaseHelper;
    Dao<App, Integer> appInfoDao;

    List<App> monitoringApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monitoring_app_list = (ListView) findViewById(R.id.monitoring_app_list);

        packageManager = getPackageManager();
        DBAsyncTask dbAsyncTask = new DBAsyncTask(this);
        dbAsyncTask.execute(packageManager);

        monitoring_app_list = (ListView) findViewById(R.id.monitoring_app_list);
        try {
            appInfoDao = getHelper().getAppInfoDao();
            QueryBuilder<App, Integer> queryBuilder = appInfoDao.queryBuilder();
            queryBuilder.where().eq("APP_MONITOR", true);
            monitoringApps = queryBuilder.query();
            MonitoringAppAdapter adapter = new MonitoringAppAdapter(this, R.layout.app_row_item, monitoringApps);
            monitoring_app_list.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void focusSessionOnClick(View view) {
        Intent intent = new Intent(this, FocusSessionActivity.class);
        startActivity(intent);
    }

    public void appsOnClick(View view) {
        Intent intent = new Intent(this, AppMonitor.class);
        startActivity(intent);
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
