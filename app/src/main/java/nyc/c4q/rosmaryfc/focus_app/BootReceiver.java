package nyc.c4q.rosmaryfc.focus_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        for now, AppMonitor will start service
//        Intent beginTracking = new Intent(context, AppTracker.class);
//        context.startService(beginTracking);
    }
}
