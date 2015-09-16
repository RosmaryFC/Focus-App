package nyc.c4q.rosmaryfc.focus_app;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionDBHelper;

public class BlockService extends Service {

    public static final int ID_FRIENDLY_NOTIFICATION = 1;

    private static final long UPDATE_INTERVAL = 30 * 1000; //30 seconds

    Timer timer;

    int startHour, startMinute, endHour, endMinute;

    boolean blockSessionIsActive;

    @Override
    public void onCreate() {
        super.onCreate();
        if (timer == null) {
            timer = new Timer();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startService() {
        timer.scheduleAtFixedRate(

                new TimerTask() {

                    public void run() {

                        doServiceWork();


                    }
                }, 0, UPDATE_INTERVAL);
    }

    private void doServiceWork() {

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        getUpcomingBSInfo(getCurrentActiveBlockSession(todaysBlockSessions()));

        Log.d("SERVICE ACTIVE BS: ", " blockSessionIsActive: " + blockSessionIsActive);

        if (blockSessionIsActive) {

            Intent startMonitoringApps = new Intent(this, AppService.class);
            this.startService(startMonitoringApps);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setSmallIcon(R.drawable.block_on_logo);
            builder.setContentTitle("Block On");
            builder.setContentText("Focus Session is Active");
            builder.setShowWhen(false);
            builder.setOngoing(true);
            Notification notification = builder.build();
            notificationManager.notify(ID_FRIENDLY_NOTIFICATION, notification);

        } else {
            Intent stopMonitoringApps = new Intent(this, AppService.class);
            this.stopService(stopMonitoringApps);

            notificationManager.cancel(ID_FRIENDLY_NOTIFICATION);
        }
    }

    //List of all of todays block sessions
    public List<BlockSession> todaysBlockSessions() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);

        Calendar currentCalendar = Calendar.getInstance();
        String currentDate = dateFormat.format(currentCalendar.getTime());

        Log.d("SERVICE: ", "CURRENT DATE: " + currentDate);

        String[] dateFormatArr = currentDate.split(" ");
        String[] arr = dateFormatArr[0].split("\\/");

        int currentYear = Integer.parseInt(arr[0]);
        int currentMonth = Integer.parseInt(arr[1]);
        int currentDay = Integer.parseInt(arr[2]);

        BlockSessionDBHelper db = new BlockSessionDBHelper(this);
        List<BlockSession> allBlockSessions = db.orderedBlockSessions();

        List<BlockSession> todaysBlockSessions = new ArrayList<BlockSession>();

        //iterating over all block sessions in data base and adding only todays block sessions to arraylist
        for (BlockSession bs : allBlockSessions) {
            String log = "ID: " + bs.getId() + " , Name: " + bs.getName() + ", Date: " + bs.getDate()
                    + ", StartTime: " + bs.getStartTime() + " , EndTime: " + bs.getEndTime()
                    + ", Notes: " + bs.getNotes();

            Log.d("SERVICE All BS Result: ", log);

            String[] bsArr = bs.getDate().split("/");

            int bsYear = Integer.parseInt(bsArr[2]);
            int bsMonth = Integer.parseInt(bsArr[0]);
            int bsDay = Integer.parseInt(bsArr[1]);

            if (currentYear == bsYear && currentMonth == bsMonth && currentDay == bsDay) {
                String log2 = "ID: " + bs.getId() + " , Name: " + bs.getName() + ", Date: " + bs.getDate()
                        + ", StartTime: " + bs.getStartTime() + " , EndTime: " + bs.getEndTime()
                        + ", Notes: " + bs.getNotes();

                Log.d("SERVICE ADDED Result: ", log2);

                todaysBlockSessions.add(bs);
            }
        }
        return todaysBlockSessions;
    }

    //iterates over todays block sessions to find which one is currently active
    public BlockSession getCurrentActiveBlockSession(List todaysBlockSessions) {
        List<BlockSession> listOfTodaysBS = todaysBlockSessions;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);

        Calendar currentCalendar = Calendar.getInstance();
        String currentDate = dateFormat.format(currentCalendar.getTime());

        String[] currentDateArr = currentDate.split(" ");

        String[] currentYMDArr = currentDateArr[0].split("\\/");
        int currentyear = Integer.parseInt(currentYMDArr[0]);
        int currentMonth = Integer.parseInt(currentYMDArr[1]);
        int currentDay = Integer.parseInt(currentYMDArr[2]);


        String[] currentTimeArr = currentDateArr[1].split("\\:");
        int currentHour = Integer.parseInt(currentTimeArr[0]);
        int currentMinute = Integer.parseInt(currentTimeArr[1]);

        for (int i = 0; i < todaysBlockSessions.size(); i++) {

            BlockSession bs = listOfTodaysBS.get(i);

            String militaryStartTime = formatToMilitaryTime(bs.getStartTime());
            String militaryEndTime = formatToMilitaryTime(bs.getEndTime());

            String[] bsStartTimeArr = militaryStartTime.split("\\:");
            int bsStartHour = Integer.parseInt(bsStartTimeArr[0]);
            int bsStartMinute = Integer.parseInt(bsStartTimeArr[1]);

            String[] bsEndTImeArr = militaryEndTime.split("\\:");
            int bsEndHour = Integer.parseInt(bsEndTImeArr[0]);
            int bsEndMinute = Integer.parseInt(bsEndTImeArr[1]);

            long currentTimeInMillis = currentCalendar.getTimeInMillis();

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.YEAR, currentyear);
            startCalendar.set(Calendar.MONTH, currentMonth - 1);
            startCalendar.set(Calendar.DAY_OF_MONTH, currentDay);
            startCalendar.set(Calendar.HOUR_OF_DAY, bsStartHour);
            startCalendar.set(Calendar.MINUTE, bsStartMinute);
            startCalendar.set(Calendar.SECOND, 0);
            long startTimeInMillis = startCalendar.getTimeInMillis();
            String startdateFormatted = dateFormat.format(startCalendar.getTime());

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(Calendar.YEAR, currentyear);
            endCalendar.set(Calendar.MONTH, currentMonth - 1);
            endCalendar.set(Calendar.DAY_OF_MONTH, currentDay);
            endCalendar.set(Calendar.HOUR_OF_DAY, bsEndHour);
            endCalendar.set(Calendar.MINUTE, bsEndMinute);
            endCalendar.set(Calendar.SECOND, 0);
            long endTimeInMillis = endCalendar.getTimeInMillis();
            String endDateFormatted = dateFormat.format(endCalendar.getTime());

            Log.d("B SERVICE TIMES ", "StartCalendar date format and millis: " + startdateFormatted + " , "
                    + startTimeInMillis + " , EndCalendar date format and millis: " + endDateFormatted + " , " + endTimeInMillis);

            //if current time >= bs Start time && current time is <= bs End Time
            if (currentTimeInMillis >= startTimeInMillis && currentTimeInMillis <= endTimeInMillis) {
                String log3 = "ID: " + bs.getId() + " , Name: " + bs.getName() + ", Date: " + bs.getDate()
                        + ", StartTime: " + bs.getStartTime() + " , EndTime: " + bs.getEndTime()
                        + ", Notes: " + bs.getNotes();


                blockSessionIsActive = true;

                Log.d("SERVICE ACTIVE BS: ", log3);

                return bs;

            }
        }

        blockSessionIsActive = false;
        return null;
    }

    public String formatToMilitaryTime(String time) {

        String[] timesplitAmPmArr = time.split(" ");
        String[] hourMinuteArr = timesplitAmPmArr[0].split("\\:");

        String am_pm = timesplitAmPmArr[1];
        int hour = Integer.parseInt(hourMinuteArr[0]);
        String minute = hourMinuteArr[1];

        int militaryHour;

        if (am_pm.equalsIgnoreCase("pm")) {
            militaryHour = (hour + 12);
        } else {
            militaryHour = hour;
        }

        return militaryHour + ":" + minute;
    }


    public void getUpcomingBSInfo(BlockSession bs) {

        if (bs != null) {

            String BSStartTimeInfo = formatToMilitaryTime(bs.getStartTime());
            String[] startTimeArr = BSStartTimeInfo.split("\\:");
            startHour = Integer.parseInt(startTimeArr[0]);
            startMinute = Integer.parseInt(startTimeArr[1]);

            String BSEndTimeInfo = formatToMilitaryTime(bs.getEndTime());
            String[] endTimeArr = BSEndTimeInfo.split("\\:");
            endHour = Integer.parseInt(endTimeArr[0]);
            endMinute = Integer.parseInt(endTimeArr[1]);
        }
    }
}
