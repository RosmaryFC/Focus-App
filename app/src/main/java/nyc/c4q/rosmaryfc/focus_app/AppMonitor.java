package nyc.c4q.rosmaryfc.focus_app;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import nyc.c4q.rosmaryfc.focus_app.ui.MainActivity;

public class AppMonitor extends AppCompatActivity {

    ListView app_list;
    Button save;
    ProgressBar progress_bar;

    DatabaseHelper databaseHelper;
    DBAsyncTask dbAsyncTask;

    PackageManager packageManager;
    List<ApplicationInfo> applicationInfos;

    List<App> apps;
    AppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_monitor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        app_list = (ListView) findViewById(R.id.app_list);
        save = (Button) findViewById(R.id.save);
       // progress_bar = (ProgressBar) findViewById(R.id.progress_bar);

        databaseHelper = DatabaseHelper.getInstance(this);
        packageManager = getPackageManager();
        applicationInfos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        try {
            if (databaseHelper.loadData().size() == 0) {
                dbAsyncTask = new DBAsyncTask(true);
                dbAsyncTask.execute();
            } else {
                dbAsyncTask = new DBAsyncTask(false);
                dbAsyncTask.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        });
    }

    public class DBAsyncTask extends AsyncTask<Void, Void, List<App>> {

        boolean firstTimeLoadingApps;

        public DBAsyncTask(boolean firstTimeLoadingApps) {
            super();
            this.firstTimeLoadingApps = firstTimeLoadingApps;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progress_bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<App> doInBackground(Void... lists) {
            if (firstTimeLoadingApps == true) {
                try {
                    Collections.sort(applicationInfos, new ApplicationInfo.DisplayNameComparator(packageManager));
                    databaseHelper.insertData(applicationInfos);
                    apps = databaseHelper.loadData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    apps = databaseHelper.loadData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return apps;
        }

        @Override
        protected void onPostExecute(List<App> apps) {
            super.onPostExecute(apps);
           // progress_bar.setVisibility(View.INVISIBLE);
            adapter = new AppAdapter(getApplicationContext(), apps);
            app_list.setAdapter(adapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_monitor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
