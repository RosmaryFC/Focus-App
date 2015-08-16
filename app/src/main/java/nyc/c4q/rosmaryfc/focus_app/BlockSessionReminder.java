package nyc.c4q.rosmaryfc.focus_app;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class BlockSessionReminder extends Service {

    public static final int ID_FRIENDLY_NOTIFICATION = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.drawable.notification_template_icon_bg);
        builder.setContentTitle("Session Block Reminder");
        builder.setContentText("You're focus session will begin in 1 minute");
        Notification notification = builder.build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(ID_FRIENDLY_NOTIFICATION, notification);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "testing", Toast.LENGTH_LONG).show();
//                final Intent showApp = new Intent(getApplicationContext(), AppUsage.class);
//                showApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(showApp);
            }
        }, 60000);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
