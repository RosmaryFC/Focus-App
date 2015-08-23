package nyc.c4q.rosmaryfc.focus_app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;

import java.sql.SQLException;
import java.util.List;

public class DBAsyncTask extends AsyncTask<List<ApplicationInfo>, Void, List<App>> {

    Context context;

    List<App> apps;

    public DBAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<App> doInBackground(List<ApplicationInfo>... lists) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        try {
            databaseHelper.insertData(lists[0]);
            apps = databaseHelper.loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apps;
    }

    @Override
    protected void onPostExecute(List<App> apps) {
        super.onPostExecute(apps);
    }
}
