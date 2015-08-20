package nyc.c4q.rosmaryfc.focus_app;

//purpose of this class is to listen for specific apps and
//prevents users from opening the app when it's during a scheduled block session

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.List;

public class AppListener extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
                boolean isActivityFound = false;
                for (int i = 0; i < runningTasks.size(); i++) {
                    if (runningTasks.get(i).topActivity.getPackageName().toString().equalsIgnoreCase("nyc.c4q.hyunj0.js_calc")) {
                        isActivityFound = true;
                    }
                }
                if (isActivityFound) {
                    Intent showApp = new Intent(getApplicationContext(), AppUsage.class);
                    showApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(showApp);
                } else {
                    Toast.makeText(getApplicationContext(), "Calc app wasn't open", Toast.LENGTH_LONG).show();
                }
            }
        }, 30000);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
