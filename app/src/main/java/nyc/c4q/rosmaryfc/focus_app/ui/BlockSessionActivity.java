package nyc.c4q.rosmaryfc.focus_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.rosmaryfc.focus_app.BlockSession;
import nyc.c4q.rosmaryfc.focus_app.BlockSessionAdapter;
import nyc.c4q.rosmaryfc.focus_app.R;
import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionDBHelper;


public class BlockSessionActivity extends AppCompatActivity {

    private ArrayList <BlockSession> sessions = new ArrayList<BlockSession>();
    private BlockSessionAdapter adapter;
    private BlockSessionDBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_session);

        updateUI();

    }

    protected void updateUI (){

        BlockSessionDBHelper db = new BlockSessionDBHelper(this);

        //CRUD Operations
        Log.d("BSActivity: ", "INSERTING...");
        //db.addBlockSession(new BlockSession("Test Block Time", "23/8/2015", "12:00", "12:30", "No Notes"));
        List<BlockSession> blockSessions = db.getAllBlockSessions();
        for(BlockSession bs : blockSessions){
            String log = "ID: " + bs.getId() + " , Name: " + bs.getName() + ", Date: " + bs.getDate()
                    + ", StartTime: " + bs.getStartTime() + " , EndTime: " + bs.getEndTime()
                    + ", Notes: " + bs.getNotes();

            Log.d("Result: ", log);
            sessions.add(bs); //adding contacts data into array list
        }
        adapter = new BlockSessionAdapter(this, R.layout.block_session_list, sessions);
        ListView blockSessionsList = (ListView) findViewById(R.id.list_block_times);
        blockSessionsList.setAdapter(adapter);

        //________________________________________________________cleaner version underneath must be tested

//        helper = new BlockSessionDBHelper(BlockSessionActivity.this);
//        SQLiteDatabase db = helper.getReadableDatabase();
//
//        Cursor cursor = db.query(BlockSessionContract.TABLE_BLOCK_SESSIONS,
//                new String[]{BlockSessionContract.Columns.BLOCK_ID,
//                        BlockSessionContract.Columns.BLOCK_NAME,
//                        BlockSessionContract.Columns.BLOCK_DATE,
//                        BlockSessionContract.Columns.BLOCK_START_TIME,
//                        BlockSessionContract.Columns.BLOCK_END_TIME,
//                        BlockSessionContract.Columns.BLOCK_NOTES},
//                null, null, null, null, null);
//
//        adapter = new BlockSessionAdapter(this, R.layout.block_session_list, sessions);
//        ListView blockSessionsList = (ListView) findViewById(R.id.list_block_times);
//        blockSessionsList.setAdapter(adapter);

    }

    public void addBlockSessionOnClick (View view) {
        Intent intent = new Intent (this, FocusSessionActivity.class );
        startActivity(intent);
    }

    public void removeBlockSessionOnClick(View view) {

    }


}
