package nyc.c4q.rosmaryfc.focus_app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
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
import java.util.Timer;
import java.util.TimerTask;

import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionDBHelper;

public class BlockService extends Service {

    public static final int ID_FRIENDLY_NOTIFICATION = 1;

    private static final long UPDATE_INTERVAL = 30 * 1000; //30 seconds

    Timer timer;

    int startHour, startMinute, endHour, endMinute;
    long currentTime, startTime, endTime, fiveMinute;

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
        Calendar currentCalendar = Calendar.getInstance();
        currentTime = currentCalendar.getTimeInMillis();

        BlockSessionDBHelper helper = new BlockSessionDBHelper(getApplicationContext());

        if(helper.getAllBlockSessions().isEmpty()){

        }else {
            getUpcomingBSInfo(getCurrentActiveBlockSession(todaysBlockSessions()));
        }

        //query next block session from db and set time values
//        startHour = 17;
//        startMinute = 24;
//        endHour = 17;
//        endMinute = 59;

        if(blockSessionIsActive) {

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.HOUR_OF_DAY, startHour);
            startCalendar.set(Calendar.MINUTE, startMinute);
            startTime = startCalendar.getTimeInMillis();
            startCalendar.add(Calendar.MINUTE, -5);
            fiveMinute = startCalendar.getTimeInMillis();

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(Calendar.HOUR_OF_DAY, endHour);
            endCalendar.set(Calendar.MINUTE, endMinute);
            endTime = endCalendar.getTimeInMillis();

            if (currentTime >= fiveMinute && currentTime < startTime) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.drawable.notification_template_icon_bg);
                builder.setContentTitle("Session Block Reminder");
                builder.setContentText("You're focus session will begin in 5 minutes");
                Notification notification = builder.build();
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                notificationManager.notify(ID_FRIENDLY_NOTIFICATION, notification);
            } else if (currentTime >= startTime && currentTime < endTime) {
                Intent startMonitoring = new Intent(this, AppService.class);
                PendingIntent pendingIntent = PendingIntent.getService(this, 0, startMonitoring, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);
            } else {

            }

        }
    }

    //List of all of todays block sessions
    public List<BlockSession> todaysBlockSessions (){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Calendar currentCalendar = Calendar.getInstance();
        String currentDate = dateFormat.format(currentCalendar.getTime());
        String currentDate2 = dateFormat2.format(currentCalendar.getTime());

        Log.d("SERVICE: ", "CURRENT DATE: " + currentDate + " , CURRENT DATE 2: " + currentDate2);

        String [] dateFormatArr = currentDate.split(" ");
        String [] arr = dateFormatArr[0].split("\\/");

        int currentYear = Integer.parseInt(arr[0]);
        int currentMonth = Integer.parseInt(arr[1]);
        int currentDay = Integer.parseInt(arr[2]);

        BlockSessionDBHelper db = new BlockSessionDBHelper(this);
        List<BlockSession> allBlockSessions = db.orderedBlockSessions();

        List<BlockSession> todaysBlockSessions = new ArrayList<BlockSession>();

        //iterating over all block sessions in data base and adding only todays block sessions to arraylist
        for(BlockSession bs : allBlockSessions){
            String log = "ID: " + bs.getId() + " , Name: " + bs.getName() + ", Date: " + bs.getDate()
                    + ", StartTime: " + bs.getStartTime() + " , EndTime: " + bs.getEndTime()
                    + ", Notes: " + bs.getNotes();

            Log.d("SERVICE All BS Result: ", log);

            String [] bsArr = bs.getDate().split("/");

            int bsYear = Integer.parseInt(bsArr[0]);
            int bsMonth = Integer.parseInt(bsArr[1]);
            int bsDay = Integer.parseInt(bsArr[2]);

            if(currentYear == bsYear && currentMonth == bsMonth && currentDay == bsDay) {
                String log2 = "ID: " + bs.getId() + " , Name: " + bs.getName() + ", Date: " + bs.getDate()
                        + ", StartTime: " + bs.getStartTime() + " , EndTime: " + bs.getEndTime()
                        + ", Notes: " + bs.getNotes();

                Log.d("SERVICE ADDED Result: ", log2);

                todaysBlockSessions.add(bs);
            }
        }
        return todaysBlockSessions;
    }

    //iterates over todays block sessions to find which one  is currently active
    public BlockSession getCurrentActiveBlockSession(List todaysBlockSessions){
        List <BlockSession> listOfTodaysBS = todaysBlockSessions;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Calendar currentCalendar = Calendar.getInstance();
        String currentDate = dateFormat.format(currentCalendar.getTime());

        String[] currentDateArr = currentDate.split(" ");
        String[] currentTimeArr = currentDateArr[1].split("\\:");
        int currentHour = Integer.parseInt(currentTimeArr[0]);
        int currentMinute = Integer.parseInt(currentTimeArr[1]);

        for(int i = 0; i < todaysBlockSessions.size(); i++){

            BlockSession bs = listOfTodaysBS.get(i);

            String [] bsStartTimeArr = bs.getStartTime().split("\\:");
            int bsStartHour = Integer.parseInt(bsStartTimeArr[0]);
            int bsStartMinute = Integer.parseInt(bsStartTimeArr[1]);

            String [] bsEndTImeArr = bs.getEndTime().split("\\:");
            int bsEndHour = Integer.parseInt(bsEndTImeArr[0]);
            int bsEndMinute = Integer.parseInt(bsEndTImeArr[1]);

            //if current time >= bs Start time && current time is <= bs End Time
            if(currentHour >= bsStartHour && currentMinute >= bsStartMinute && currentHour <= bsEndHour && currentMinute <= bsEndMinute) {

                String log3 = "ID: " + bs.getId() + " , Name: " + bs.getName() + ", Date: " + bs.getDate()
                        + ", StartTime: " + bs.getStartTime() + " , EndTime: " + bs.getEndTime()
                        + ", Notes: " + bs.getNotes();

                Log.d("SERVICE ACTIVE BS: ", log3);


                blockSessionIsActive = true;
                return bs;
            }
        }

        blockSessionIsActive = false;
        return null;
    }


    public void getUpcomingBSInfo (BlockSession bs) {

        if(bs != null){

            String BSStartTimeInfo = bs.getStartTime();
            String[] startTimeArr = BSStartTimeInfo.split("\\:");
            startHour = Integer.parseInt(startTimeArr[0]);
            startMinute = Integer.parseInt(startTimeArr[1]);

            String BSEndTimeInfo = bs.getEndTime();
            String[] endTimeArr = BSEndTimeInfo.split("\\:");
            endHour = Integer.parseInt(endTimeArr[0]);
            endMinute = Integer.parseInt(endTimeArr[1]);
        }
    }



}
