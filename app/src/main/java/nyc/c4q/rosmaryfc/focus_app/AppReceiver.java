package nyc.c4q.rosmaryfc.focus_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppReceiver extends BroadcastReceiver {

    public static final String REFRESH = "Refresh";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent refreshApps = new Intent(context, AppMonitor.class);
        refreshApps.putExtra(REFRESH, true);
        context.startActivity(refreshApps);
    }
}
