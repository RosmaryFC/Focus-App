package nyc.c4q.rosmaryfc.focus_app;

/**
 * Created by c4q-rosmary on 8/15/15.
 */
public class FocusSessionInfo {
    //name, start time, end time, date, notification, notes, weekdays,
    public String name;
    public String startTime;
    public String endTime;
    public String date;
    public boolean notificationEnabled;
    public String notes;
    public String [] weekdays;

    public FocusSessionInfo (){

    }

    public FocusSessionInfo(String name, String startTime, String endTime, String date, boolean notificationEnabled, String notes){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.notificationEnabled = notificationEnabled;
        this.notes = notes;
        this.weekdays = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isNotificationEnabled() {
        return notificationEnabled;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String[] getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String[] weekdays) {
        this.weekdays = weekdays;
    }


}
