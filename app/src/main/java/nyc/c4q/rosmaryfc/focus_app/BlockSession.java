package nyc.c4q.rosmaryfc.focus_app;

import android.util.Log;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionContract;

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
    String daysToRecur;
    boolean recurring;

    public String getDaysToRecur() {
        if(this.isRecurring()) {
            return getCommaSeparatedDays(this.getRecurDays());
        } else {
            return null;
        }
    }



    public static int[] DAYS = {Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};

    public ArrayList<Integer> getRecurDays() {
        return recurDays;
    }


    private ArrayList<Integer> recurDays; //should only be available if recurring is on.
    private Spinner daySpinner;


    String [] weekdays;
    boolean isEnabled;

    public BlockSession(){
        this.recurDays = new ArrayList<>();
        this.recurring = this.recurDays.size() > 0;
    }

    public BlockSession(String name,String date, String startTime, String endTime, String notes) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
        this.recurDays = new ArrayList<>();
        this.recurring = this.recurDays.size() > 0;
        if(recurring)
        this.daysToRecur = getCommaSeparatedDays(this.getRecurDays());
    }

    public BlockSession(String name, String date, String startTime, String endTime, String notes, boolean recurring){
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
        this.recurDays = new ArrayList<>();
        this.recurring = this.recurDays.size() > 0;

    }

    public BlockSession(int id,String name,String date, String startTime, String endTime, String notes){
        this.id = id;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
        this.recurDays = new ArrayList<>();
        this.recurring = this.recurDays.size() > 0;
        //this.weekdays = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    }

    public void addDaytoRecur(int day){
           // if (intValidDay(day)){
                this.recurDays.add(day);
            //}
    }

    //FIXME : Placeholder internal check to see if object is set for Today.
    // Production should actually be querying this in Database
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
      return this.recurDays.size() > 0;
    }

//    public void settoRecur(){
//        this.recurring = true;
//    }


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


    public String getCommaSeparatedDays(List<Integer> intList){
        String daysStr = "";
        for (int i = 0; i < intList.size(); i++) {
            int iterInt = intList.get(i);
            if(intValidDay(iterInt)){
                daysStr += convertIntToDayStr(iterInt) + ",";
                Log.d("DAY", daysStr);
            }

        }
        return daysStr;
    }

    public boolean intValidDay(int x){
        boolean intValid = true;
        for (int i = 0; i < BlockSession.DAYS.length; i++) {
            if(x!= BlockSession.DAYS[i]){
                intValid = false;
            }
        }
        return intValid;
    }
    public String convertIntToDayStr(int x){  //turns an individual day to the necessary String. i.e 1 -> "SUNDAY"
        String dayStr = "";
        switch(x){
            case Calendar.SUNDAY: dayStr = BlockSessionContract.DayStrings.SUNDAY;
                break;
            case Calendar.MONDAY: dayStr = BlockSessionContract.DayStrings.SUNDAY;
                break;
            case Calendar.TUESDAY: dayStr = BlockSessionContract.DayStrings.SUNDAY;
                break;
            case Calendar.WEDNESDAY: dayStr = BlockSessionContract.DayStrings.SUNDAY;
                break;
            case Calendar.THURSDAY: dayStr = BlockSessionContract.DayStrings.SUNDAY;
                break;
            case Calendar.FRIDAY: dayStr = BlockSessionContract.DayStrings.SUNDAY;
                break;
            case Calendar.SATURDAY: dayStr = BlockSessionContract.DayStrings.SUNDAY;
                break;
        }
        return dayStr;

    }





}
