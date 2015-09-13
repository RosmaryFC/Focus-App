package nyc.c4q.rosmaryfc.focus_app.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import nyc.c4q.rosmaryfc.focus_app.BlockSession;
import nyc.c4q.rosmaryfc.focus_app.R;
import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionContract;
import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionDBHelper;


public class CreateNewBlockSessionActivity extends AppCompatActivity {

    //todo: fix issue where you press must press ET twice for time and date pickers to appear

    EditText nameET;
    EditText startTimeET;
    EditText endTimeET;
    EditText dateET;

    private BlockSessionDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent blockTypeIntent = getIntent();
        String blockType = blockTypeIntent.getExtras().getString("block type");

        if (blockType.equals("future")){
            setContentView(R.layout.activity_future_block_session);

            //write oncreate stuff here for future
            dateET = (EditText) findViewById(R.id.date_et);
            dateET.setInputType(InputType.TYPE_NULL);
            dateET.setOnClickListener(dateListener);

            //dateET.setOnTouchListener(dateListener);


        }else if (blockType.equals("recur")){
            setContentView(R.layout.activity_recur_block_session);

            //write on create stuff here for recur
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameET = (EditText) findViewById(R.id.name_et);
        startTimeET = (EditText) findViewById(R.id.start_time_et);
        endTimeET = (EditText) findViewById(R.id.end_time_et);

        startTimeET.setInputType(InputType.TYPE_NULL);
        startTimeET.setOnClickListener(startTimeListener);

        endTimeET.setInputType(InputType.TYPE_NULL);
        endTimeET.setOnClickListener(endTimeListener);

//        startTimeET.setOnTouchListener(startTimeListener);
//        endTimeET.setOnTouchListener(endTimeListener);

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


    public View.OnClickListener startTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(CreateNewBlockSessionActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
            mTimePicker = new TimePickerDialog(CreateNewBlockSessionActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
            mDatePicker = new DatePickerDialog(CreateNewBlockSessionActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                    // TODO Auto-generated method stub
                    /*  get date and time */
                    selectedMonth = selectedMonth + 1;
                    dateET.setText(selectedYear + "/" + selectedMonth + "/" + selectedDay);
                }
            }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        }
    };

    public void saveOnClick (View view){
        String name = nameET.getText().toString();
        String date = dateET.getText().toString();
        String startTime = startTimeET.getText().toString();
        String endTime = endTimeET.getText().toString();
        String notes = "N/A";

        helper = new BlockSessionDBHelper(this);
        helper.addBlockSession(new BlockSession(name, date, startTime, endTime, notes));

        getData();

        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);

    }

    public void cancelOnClick(View view){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

    public void getData(){

        String selectQuery = "SELECT * FROM " + BlockSessionContract.TABLE_BLOCK_SESSIONS;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to the list
        if(cursor.moveToFirst()){
            do{
                BlockSession blockSession = new BlockSession();
                blockSession.setName(cursor.getString(1));
                blockSession.setDate(cursor.getString(2));
                blockSession.setStartTime(cursor.getString(3));
                blockSession.setEndTime(cursor.getString(4));
                blockSession.setNotes(cursor.getString(5));

                String log = "ID: " + blockSession.getId() + " , Name: " + blockSession.getName() + ", Date: " + blockSession.getDate()
                        + ", StartTime: " + blockSession.getStartTime() + " , EndTime: " + blockSession.getEndTime()
                        + ", Notes: " + blockSession.getNotes();

                Log.d("FOCUS SESSION result: ", log);

            }while(cursor.moveToNext());
        }
        db.close();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_focus_session, menu);
        return true;
    }
}

//    public View.OnTouchListener startTimeListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            // TODO Auto-generated method stub
//            Calendar mcurrentTime = Calendar.getInstance();
//            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//            int minute = mcurrentTime.get(Calendar.MINUTE);
//
//            TimePickerDialog mTimePicker;
//            mTimePicker = new TimePickerDialog(FocusSessionActivity.this, new TimePickerDialog.OnTimeSetListener() {
//                @Override
//                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                    startTimeET.setText( selectedHour + ":" + selectedMinute);
//                }
//            }, hour, minute, true);
//            mTimePicker.setTitle("Select Time");
//            mTimePicker.show();
//            return true;
//        }
//    };
//
//    public View.OnTouchListener endTimeListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//
//            // TODO Auto-generated method stub
//            //To show current date in the datepicker
//            Calendar mcurrentDate = Calendar.getInstance();
//            int mYear = mcurrentDate.get(Calendar.YEAR);
//            int mMonth = mcurrentDate.get(Calendar.MONTH);
//            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
//
//            DatePickerDialog mDatePicker;
//            mDatePicker = new DatePickerDialog(FocusSessionActivity.this, new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
//                    // TODO Auto-generated method stub
//                    /*  get date and time */
//                    selectedMonth = selectedMonth + 1;
//                    dateET.setText(selectedMonth + "/" + selectedDay + "/" + selectedYear);
//                }
//            }, mYear, mMonth, mDay);
//            mDatePicker.setTitle("Select Date");
//            mDatePicker.show();
//            return true;
//        }
//    };
//
//    public View.OnTouchListener dateListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//
//            // TODO Auto-generated method stub
//            //To show current date in the datepicker
//            Calendar mcurrentDate = Calendar.getInstance();
//            int mYear = mcurrentDate.get(Calendar.YEAR);
//            int mMonth = mcurrentDate.get(Calendar.MONTH);
//            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
//
//            DatePickerDialog mDatePicker;
//            mDatePicker = new DatePickerDialog(FocusSessionActivity.this, new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
//                    // TODO Auto-generated method stub
//                    /*  get date and time */
//                    selectedMonth = selectedMonth + 1;
//                    dateET.setText(selectedMonth + "/" + selectedDay + "/" + selectedYear);
//                }
//            }, mYear, mMonth, mDay);
//            mDatePicker.setTitle("Select Date");
//            mDatePicker.show();
//            return true;
//        }
//    };

//    public View.OnFocusChangeListener startTimeListener = new View.OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View view, boolean b) {
//
//        }
//    };
//
//    public View.OnFocusChangeListener endTimeListener = new View.OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View view, boolean b) {
//
//        }
//    };
//
//    public View.OnFocusChangeListener dateListener = new View.OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View view, boolean b) {
//
//        }
//    };

//
//    //todo: will not be used to demo MVP, option whether to set notification reminder or not. can also be toast
//    public void onRadioButtonClicked(View view){
//        //is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        //check which radio button was clicked
//        switch (view.getId()){
//            case R.id.enable_radBtn:
//                if(checked){
//
//                }
//                break;
//            case R.id.disable_radBtn:
//                if(checked) {
//
//                }
//                break;
//        }
//    }
