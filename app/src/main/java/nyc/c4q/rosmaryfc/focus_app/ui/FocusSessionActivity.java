package nyc.c4q.rosmaryfc.focus_app.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import java.util.Calendar;

import nyc.c4q.rosmaryfc.focus_app.R;


public class FocusSessionActivity extends AppCompatActivity {

    //todo: fix issue where you press must press ET twice for time and date pickers to appear

    EditText nameET;
    EditText startTimeET;
    EditText endTimeET;
    EditText dateET;
    EditText notesET;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_session);

        initializeViews();

        startTimeET.setOnClickListener(startTimeListener);
        endTimeET.setOnClickListener(endTimeListener);
        dateET.setOnClickListener(dateListener);

    }

    public void initializeViews () {
        nameET = (EditText) findViewById(R.id.name_et);
        startTimeET = (EditText) findViewById(R.id.start_time_et);
        endTimeET = (EditText) findViewById(R.id.end_time_et);
        dateET = (EditText) findViewById(R.id.date_et);
        notesET = (EditText) findViewById(R.id.notes_et);
    }

    //todo: will not be used to demo MVP, use will choose between setting date or setting weekdays
    public void onCheckboxClicked(View view) {
        //is this view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        //check when checkbox is clicked
        switch(view.getId()){
            case R.id.checkbox_sunday:
                if(checked){
                    //checked stuff
                }
                else{
                    //unchecked stuff
                }
                break;
            case R.id.checkbox_monday:
                if(checked){
                    //checked stuff
                }
                else{
                    //unchecked stuff
                }
                break;
            case R.id.checkbox_tuesday:
                if(checked){
                    //checked stuff
                }
                else{
                    //unchecked stuff
                }
                break;
            case R.id.checkbox_wednesday:
                if(checked){
                    //checked stuff
                }
                else{
                    //unchecked stuff
                }
                break;
            case R.id.checkbox_thursday:
                if(checked){
                    //checked stuff
                }
                else{
                    //unchecked stuff
                }
                break;
            case R.id.checkbox_friday:
                if(checked){
                    //checked stuff
                }
                else{
                    //unchecked stuff
                }
                break;
            case R.id.checkbox_saturday:
                if(checked){
                    //checked stuff
                }
                else{
                    //unchecked stuff
                }
                break;
        }
    }

    //todo: will not be used to demo MVP, option whether to set notification reminder or not. can also be toast
    public void onRadioButtonClicked(View view){
        //is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        //check which radio button was clicked
        switch (view.getId()){
            case R.id.enable_radBtn:
                if(checked){

                }
                break;
            case R.id.disable_radBtn:
                if(checked) {

                }
                break;
        }
    }

    public View.OnClickListener startTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(FocusSessionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    startTimeET.setText( selectedHour + ":" + selectedMinute);
                }
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
    };

    public View.OnClickListener endTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(FocusSessionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    endTimeET.setText( selectedHour + ":" + selectedMinute);
                }
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
    };

    public View.OnClickListener dateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            //To show current date in the datepicker
            Calendar mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(FocusSessionActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                    // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                    selectedMonth = selectedMonth + 1;
                    dateET.setText(selectedDay + "/" + selectedMonth + "/" + selectedYear);
                }
            }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        }
    };

    public void saveOnClick (View view){

    }

}
