package nyc.c4q.rosmaryfc.focus_app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionContract;
import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionDBHelper;

/**
 * Created by c4q-rosmary on 9/12/15.
 */
public class BlockSessionAlertDialogFragment extends DialogFragment implements View.OnClickListener{

    View rootView;

    String blockType;

    int blockId;
    String blockName;
    String blockStartTime;
    String blockEndTime;
    String blockDate;

    EditText nameET;
    EditText startTimeET;
    EditText endTimeET;
    EditText dateET;

    Button saveBtn;
    Button cancelBtn;

    private BlockSessionDBHelper helper;

    static BlockSessionAlertDialogFragment newInstance(BlockSession blockSession) {
        BlockSessionAlertDialogFragment f = new BlockSessionAlertDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("id", blockSession.getId());
        args.putString("name", blockSession.getName());
        args.putString("startTime", blockSession.getStartTime());
        args.putString("endTime", blockSession.getEndTime());
        args.putString("date",blockSession.getDate());
        f.setArguments(args);

        return f;
    }

    public static interface DialogButtons
    {

        public void save();
        public void cancel();

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blockId = getArguments().getInt("id");
        blockName = getArguments().getString("name");
        blockStartTime = getArguments().getString("startTime");
        blockEndTime = getArguments().getString("endTime");
        blockDate = getArguments().getString("date");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.block_session_future_dialog, container,
                false);

        initializeViews();

        nameET.setText(blockName);
        startTimeET.setText(blockStartTime);
        endTimeET.setText(blockEndTime);
        dateET.setText(blockDate);

        startTimeET.setInputType(InputType.TYPE_NULL);
        startTimeET.setOnClickListener(startTimeListener);

        endTimeET.setInputType(InputType.TYPE_NULL);
        endTimeET.setOnClickListener(endTimeListener);

        dateET.setInputType(InputType.TYPE_NULL);
        dateET.setOnClickListener(dateListener);

        saveBtn.setOnClickListener(this);

        return rootView;

    }

    public void initializeViews () {
        nameET = (EditText) rootView.findViewById(R.id.name_et);
        startTimeET = (EditText) rootView.findViewById(R.id.start_time_et);
        endTimeET = (EditText) rootView.findViewById(R.id.end_time_et);
        dateET = (EditText) rootView.findViewById(R.id.date_et);

        saveBtn = (Button) rootView.findViewById(R.id.save_btn);
    }

    public View.OnClickListener startTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    Calendar dateTime = Calendar.getInstance();
                    dateTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                    dateTime.set(Calendar.MINUTE, selectedMinute);

                    String format = "h:mm a";
                    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                    String formattedTime = sdf.format(dateTime.getTime());

                    startTimeET.setText(formattedTime);
                }
            }, hour, minute, false);
            mTimePicker.setTitle("Select Start Time");
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
            mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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
            mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                    // TODO Auto-generated method stub
                    /*  get date and time */
                    selectedMonth = selectedMonth + 1;
                    dateET.setText(selectedMonth + "/" + selectedDay +"/" + selectedYear);
                }
            }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        }
    };

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
    public void onClick(View view) {

            if (view == saveBtn)
            {
                String name = nameET.getText().toString();
                String date = dateET.getText().toString();
                String startTime = startTimeET.getText().toString();
                String endTime = endTimeET.getText().toString();

//
//                helper = new BlockSessionDBHelper(getActivity());
//                helper.updateBlockSession()

                //helper.addBlockSession(new BlockSession(name, date, startTime, endTime, "N/A"));

                getData();
                this.dismiss();
            }
            else if (view == cancelBtn)
            {
                //buttons.cancel();
                this.dismiss();
            }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        blockType = "Future";

        return new AlertDialog.Builder(getActivity())
        // Set Dialog Icon
        .setIcon(R.drawable.icon_1)
                // Set Dialog Title
                .setTitle("Create " + blockType + " Block Session")
                        // Set Dialog Message
                .setMessage("Please fill out all fields below")

                        // Positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                    }
                })

                        // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                    }
                }).create();


    }
}