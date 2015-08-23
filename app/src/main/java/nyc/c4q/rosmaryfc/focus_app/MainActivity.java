package nyc.c4q.rosmaryfc.focus_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView monitoring_app_list;

    DatabaseHelper databaseHelper;

    List<App> monitoringApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monitoring_app_list = (ListView) findViewById(R.id.monitoring_app_list);

        databaseHelper = DatabaseHelper.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (databaseHelper != null) {
            try {
                Dao<App, ?> appDao = databaseHelper.getDao(App.class);
                monitoringApps = appDao.query(appDao.queryBuilder().where().eq("APP_MONITOR", true).prepare());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (monitoringApps.size() != 0) {
            MonitoringAppAdapter adapter = new MonitoringAppAdapter(this, monitoringApps);
            monitoring_app_list.setAdapter(adapter);
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
        Intent intent = new Intent(this, BlockSessionActivity.class);
        startActivity(intent);
    }

    public void appsOnClick(View view) {
        Intent intent = new Intent(this, AppMonitor.class);
        startActivity(intent);
    }
}
