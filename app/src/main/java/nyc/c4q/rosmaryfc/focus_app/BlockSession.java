package nyc.c4q.rosmaryfc.focus_app;

/**
 * Created by c4q-rosmary on 8/15/15.
 */
public class BlockSession {
    //name, start time, end time, date, notification, notes, weekdays,
    int id;
    String name;
    String startTime;
    String endTime;
    String date;
    String notes;
    String [] weekdays;

    public BlockSession(){

    }

    public BlockSession(String name,String date, String startTime, String endTime, String notes){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.notes = notes;
        //this.weekdays = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
