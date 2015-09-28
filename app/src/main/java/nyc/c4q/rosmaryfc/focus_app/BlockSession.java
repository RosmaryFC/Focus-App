package nyc.c4q.rosmaryfc.focus_app;

import android.util.Log;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    public List<Integer> getRecurDays() {
        return recurDays;
    }


    private List<Integer> recurDays; //should only be available if recurring is on.
    private Spinner daySpinner;


    String [] weekdays;
    boolean isEnabled;

    public BlockSession(){
        this.recurring = false; //by default
        if(this.recurring = true){
            this.recurDays = new ArrayList<>();
        }
    }

    public BlockSession(String name,String date, String startTime, String endTime, String notes) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
        this.recurring = false; //By default
        if(this.recurring = true){
            this.recurDays = new ArrayList<>();
        }
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

    public void addDaytoRecur(int day){
        if(this.isRecurring() == false){
            return;
        } else {
            int[] DAYS = {Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};
                    this.recurDays.add(day);
        }

    }

    public boolean recursToday() {
        boolean isPartOfTodaysTasks = false;
        if(!this.isRecurring()){
            return false;
        } else {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            for (int i = 0; i < this.getRecurDays().size(); i++) {
                List<Integer> setDays = this.getRecurDays();
                if(day == setDays.get(i)){
                    isPartOfTodaysTasks = true;
                }
            }
        }
        return isPartOfTodaysTasks;
    }

    public boolean isRecurring(){
      return this.recurring;
    }

    public void settoRecur(){
        this.recurring = true;
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


    public void setNotRecurring() {
        this.recurring = false;
    }
}
