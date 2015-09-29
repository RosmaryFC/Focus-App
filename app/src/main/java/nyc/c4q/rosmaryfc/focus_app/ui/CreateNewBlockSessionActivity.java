package nyc.c4q.rosmaryfc.focus_app.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
    private BlockSession blockSession;
    private ArrayList<Integer>daysToRecur;

    int layout;

    int editBsId;
    String editName;
    String editDate;
    String editStartTime;
    String editEndTime;
    boolean isBeingEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blockSession = new BlockSession();
        Intent blockIntent = getIntent();
//        if (blockIntent.getExtras().getString("block type") != null){
//            blockIntent.removeExtra("block type");
//        }
        String blockType = blockIntent.getExtras().getString("block type");
//        editBsId = blockIntent.getExtras().getInt("bs id");
//        editName = blockIntent.getExtras().getString("bs name");
//        editDate = blockIntent.getExtras().getString("bs date");
//        editStartTime = blockIntent.getExtras().getString("bs start time");
//        editEndTime = blockIntent.getExtras().getString("bs end time");
//        isBeingEdited = blockIntent.getExtras().getBoolean("bsIsBeingEdited")

        //block sesh being currently edited


        if (blockType.equals("future")){
            layout = R.layout.activity_future_block_session;
            setContentView(layout);

            //write oncreate stuff here for future
            dateET = (EditText) findViewById(R.id.date_et);
            dateET.setInputType(InputType.TYPE_NULL);
            dateET.setOnClickListener(dateListener);

//            if(isBeingEdited){
//                dateET.setText(editDate);
//            }

            //dateET.setOnTouchListener(dateListener);


        }else if (blockType.equals("recur")){
            layout = R.layout.activity_recur_block_session;
            setContentView(layout);
            daysToRecur = new ArrayList<>();
           // blockSession.settoRecur();

            //write on create stuff here for recur
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        nameET = (EditText) findViewById(R.id.name_et);
        startTimeET = (EditText) findViewById(R.id.start_time_et);
        endTimeET = (EditText) findViewById(R.id.end_time_et);

        startTimeET.setInputType(InputType.TYPE_NULL);
        startTimeET.setOnClickListener(startTimeListener);

        endTimeET.setInputType(InputType.TYPE_NULL);
        endTimeET.setOnClickListener(endTimeListener);

//        if(isBeingEdited){
//            nameET.setText(editName);
//            startTimeET.setText(editStartTime);
//            endTimeET.setText(editEndTime);
//        }

//        startTimeET.setOnTouchListener(startTimeListener);
//        endTimeET.setOnTouchListener(endTimeListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_create_new_block_session, menu);
//        return true;
//    }

    //todo: will not be used to demo MVP, use will choose between setting date or setting weekdays
    public void onCheckboxClicked (View view) {

        //is this view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        int dayToRecur = 0;
        //check when checkbox is clicked
        switch(view.getId()){

            case R.id.checkbox_sunday:
                if(checked){
                    dayToRecur = (Calendar.SUNDAY);
                    daysToRecur.add(dayToRecur);
                    //blockSession.addDaytoRecur(dayToRecur);
                    Log.d("recurring?","" + blockSession.isRecurring());
                }
                break;
            case R.id.checkbox_monday:
                if(checked){
                    dayToRecur = (Calendar.MONDAY);
                    daysToRecur.add(dayToRecur);
                    //blockSession.addDaytoRecur(dayToRecur);
                    Log.d("recurring?", "" + blockSession.isRecurring());
                }
                break;
            case R.id.checkbox_tuesday:
                if(checked){
                    dayToRecur = (Calendar.TUESDAY);
                    daysToRecur.add(dayToRecur);
                    //blockSession.addDaytoRecur(dayToRecur);
                    Log.d("recurring?", "" + blockSession.isRecurring());
                }
                break;
            case R.id.checkbox_wednesday:
                if(checked){
                    dayToRecur = (Calendar.WEDNESDAY);
                    daysToRecur.add(dayToRecur);
                    //blockSession.addDaytoRecur(dayToRecur);
                    Log.d("recurring?", "" + blockSession.isRecurring());
                }
                break;
            case R.id.checkbox_thursday:
                if(checked){
                    dayToRecur = (Calendar.THURSDAY);
                    daysToRecur.add(dayToRecur);
                    //blockSession.addDaytoRecur(dayToRecur);
                    Log.d("recurring?", "" + blockSession.isRecurring());

                }
                break;
            case R.id.checkbox_friday:
                if(checked){
                    dayToRecur = (Calendar.FRIDAY);
                    daysToRecur.add(dayToRecur);
                    //blockSession.addDaytoRecur(dayToRecur);
                    Log.d("recurring?", "" + blockSession.isRecurring());
                }
                break;
            case R.id.checkbox_saturday:
                if(checked){
                    dayToRecur = (Calendar.FRIDAY);
                    daysToRecur.add(dayToRecur);
                    //blockSession.addDaytoRecur(dayToRecur);
                    Log.d("recurring?", "" + blockSession.isRecurring());
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
                    Calendar dateTime = Calendar.getInstance();
                    dateTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                    dateTime.set(Calendar.MINUTE, selectedMinute);

                    String formatAmPm = "h:mm a";
                    SimpleDateFormat sdf = new SimpleDateFormat(formatAmPm, Locale.US);
                    String formattedTime = sdf.format(dateTime.getTime());

                    startTimeET.setText(formattedTime);
                }
            }, hour, minute, false);
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
                    Calendar dateTime = Calendar.getInstance();
                    dateTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                    dateTime.set(Calendar.MINUTE, selectedMinute);

                    String format = "h:mm a";
                    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                    String formattedTime = sdf.format(dateTime.getTime());

                    endTimeET.setText(formattedTime);
                }
            }, hour, minute, false);
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
                    /* get date and time */
                    selectedMonth = selectedMonth + 1;
                    dateET.setText(selectedMonth + "/" + selectedDay +"/" + selectedYear);
                }
            }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        }
    };

    public void saveOnClick (View view){
        String date;
        String notes = "N/A";//todo: remove notes from database, BlockSession,
        String name = nameET.getText().toString();
        String startTime = startTimeET.getText().toString();
        String endTime = endTimeET.getText().toString();
        blockSession.setName(name);
        blockSession.setStartTime(startTime);
        blockSession.setEndTime(endTime);
        helper = new BlockSessionDBHelper(this);

        if(layout == R.layout.activity_recur_block_session ) {
            //blockSession.settoRecur();
            Calendar mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
            date = String.valueOf((mMonth + 1) + "/" + mDay + "/" + mYear); //Stores start date for recurring sessions as current date.
            blockSession.setDate(date);
            for(int x : daysToRecur){
                blockSession.addDaytoRecur(x);
            }
            Log.wtf("Is this boolean working?","" + blockSession.isRecurring() );
            helper.addblockSession(blockSession);
            Log.d("recurring test", "" + blockSession.isRecurring());
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
        } else {
            date = dateET.getText().toString();

            if(FutureBSFieldsAreNotEmpty(name, date, startTime, endTime)){

//                if(isBeingEdited){
//                    BlockSession updatedBS = new BlockSession(editBsId, name, date, startTime, endTime, notes);
//                    helper.updateBlockSession(updatedBS);
//                }else{

                //
//                }
            helper.addBlockSession(new BlockSession(name, date, startTime, endTime, notes));
                getData();

                Intent intent = new Intent (this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Input Field Missing", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public boolean FutureBSFieldsAreNotEmpty (String name, String date, String startTime, String endTime){
        if(name.isEmpty() || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty()){
            return false;
        }
        return true;
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

                String log =
                        "ID: " + blockSession.getId() + " , " +
                                "Name: " + blockSession.getName() + ", " +
                                "Date: " + blockSession.getDate()
                        + ", StartTime: " + blockSession.getStartTime() + " , " +
                                "EndTime: " + blockSession.getEndTime()
                        + ", Notes: " + blockSession.getNotes()
                        + ",Recurring: " + blockSession.isRecurring();


                Log.d("FOCUS SESSION result: ", log);

            }while(cursor.moveToNext());
        }
        db.close();

    }

    public void saveRecurBS(){

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



//String [] timesplitAmPmArr = formattedTime.split(" ");
//String [] hourMinuteArr = timesplitAmPmArr[0].split("\\:");
//
//String am_pm = timesplitAmPmArr[1];
//int hour = Integer.parseInt(hourMinuteArr[0]);
//String minute = hourMinuteArr[1];
//
//int militaryHour;
//
//if(am_pm.equalsIgnoreCase("pm")){
//        militaryHour = (hour + 12);
//        } else {
//        militaryHour = hour;
//        }
//
//        String militaryTime = militaryHour +":" + minute;
//
//
//        Log.d("NEW BS TIME SET ", "military to formattedTime: " + formattedTime + " , formatted to military " +militaryTime );

//                    String minString = selectedMinute + "";
//                    if(minString.length() == 1){
//                        startTimeET.setText( selectedHour + ":0" + selectedMinute);
//                    }else {
//                        startTimeET.setText( selectedHour + ":" + selectedMinute);
//                    }