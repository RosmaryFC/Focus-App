package nyc.c4q.rosmaryfc.focus_app;

import android.util.Log;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.rosmaryfc.focus_app.ui.CreateNewBlockSessionActivity;

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
    boolean recurring;
    private List<String> recurDays; //should only be available if recurring is on.
    private Spinner daySpinner;


    String [] weekdays;
    boolean isEnabled;

    public BlockSession(){

    }

    public BlockSession(String name,String date, String startTime, String endTime, String notes){
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
        this.recurring = false; //By default
        //this.weekdays = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    }

    public BlockSession(String name, String date, String startTime, String endTime, String notes, boolean recurring){
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
        this.recurring = recurring;
        if(this.recurring = true){
            this.recurDays = new ArrayList<>();
        }
    }

    public BlockSession(int id,String name,String date, String startTime, String endTime, String notes){
        this.id = id;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
        //this.weekdays = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    }

    public void addDaytoRecur(String day){
        if(this.isRecurring() == false){
            return;
        } else {
            String[] DAYS = CreateNewBlockSessionActivity.DayConstants.DAYS;
            for (int i = 0; i < DAYS.length; i++) {
                boolean dayValid = day == DAYS[i];
                if (!dayValid) {
                    Log.wtf("This should never hapen", "valid day? - > " + dayValid);
                    break;
                } else {
                    this.recurDays.add(day);
                }
            }
        }

    }

    public boolean isRecurring(){
        return this.recurring;
    }

    public void settoRecur(){
        this.recurring = true;
    }



    public void setRepeatDay(String day){

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
