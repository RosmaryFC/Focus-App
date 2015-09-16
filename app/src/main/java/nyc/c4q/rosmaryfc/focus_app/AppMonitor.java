package nyc.c4q.rosmaryfc.focus_app;

import android.content.Intent;
import android.content.IntentFilter;
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

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import nyc.c4q.rosmaryfc.focus_app.ui.MainActivity;

public class AppMonitor extends AppCompatActivity {

    public static final int FIRST_LOAD_APPS = 0;
    public static final int CURRENT_APPS = 1;

    ListView app_list;
    Button save;
    Button cancel;
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
        //cancel = (Button) findViewById(R.id.cancel);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);

        databaseHelper = DatabaseHelper.getInstance(this);
        packageManager = getPackageManager();
        applicationInfos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        });
        //todo: have it so when user clicks cancel or back buttn, items in listview update
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (databaseHelper.loadData().size() == 0) {
                dbAsyncTask = new DBAsyncTask(FIRST_LOAD_APPS);
                dbAsyncTask.execute();
            } else {
                dbAsyncTask = new DBAsyncTask(CURRENT_APPS);
                dbAsyncTask.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public class DBAsyncTask extends AsyncTask<Void, Void, List<App>> {

        int status;

        public DBAsyncTask(int status) {
            super();
            this.status = status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<App> doInBackground(Void... lists) {
            if (status == FIRST_LOAD_APPS) {
                try {
                    Collections.sort(applicationInfos, new ApplicationInfo.DisplayNameComparator(packageManager));
                    databaseHelper.insertData(applicationInfos);
                    apps = databaseHelper.loadData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (status == CURRENT_APPS) {
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
            progress_bar.setVisibility(View.INVISIBLE);
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
