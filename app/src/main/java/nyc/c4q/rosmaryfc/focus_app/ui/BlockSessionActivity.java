package nyc.c4q.rosmaryfc.focus_app.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

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

//        Button add = (Button) findViewById(R.id.btn_add);
//        add.setOnClickListener(addBlockSessionListener);

    }


    protected void updateUI (){

//        BlockSessionDBHelper db = new BlockSessionDBHelper(this);

//        CRUD Operations
//        Log.d("BSActivity: ", "INSERTING...");
//        db.addBlockSession(new BlockSession("Test Block Time", "23/8/2015", "12:00", "12:30", "No Notes"));
//        List<BlockSession> blockSessions = db.getAllBlockSessions();

//        for(int i = 0; i < blockSessions.size(); i++) {
//
//            String log = "ID: " + blockSessions.get(i).getId() + " , Name: " + blockSessions.get(i).getName() + ", Date: " + blockSessions.get(i).getDate()
//                    + ", StartTime: " + blockSessions.get(i).getStartTime() + " , EndTime: " + blockSessions.get(i).getEndTime()
//                    + ", Notes: " + blockSessions.get(i).getNotes();
//
//            Log.d("Result: ", log);
//
//            sessions.add(blockSessions.get(i));
//        }
//
//        for(BlockSession bs : blockSessions){
//            String log = "ID: " + bs.getId() + " , Name: " + bs.getName() + ", Date: " + bs.getDate()
//                    + ", StartTime: " + bs.getStartTime() + " , EndTime: " + bs.getEndTime()
//                    + ", Notes: " + bs.getNotes();
//
//            Log.d("Result: ", log);
//            sessions.add(bs); //adding contacts data into array list
//        }
//
//        adapter = new BlockSessionAdapter(this, R.layout.block_session_list, sessions);
//        ListView blockSessionsList = (ListView) findViewById(R.id.list_block_times);
//        blockSessionsList.setAdapter(adapter);

        //________________________________________________________cleaner version underneath must be tested

//        String selectQuery = "SELECT * FROM " + BlockSessionContract.TABLE_BLOCK_SESSIONS;
//
//        helper = new BlockSessionDBHelper(BlockSessionActivity.this);
//        SQLiteDatabase db = helper.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        //looping through all rows and adding to the list
//        if(cursor.moveToFirst()){
//            do{
//                BlockSession blockSession = new BlockSession();
//                blockSession.setName(cursor.getString(1));
//                blockSession.setDate(cursor.getString(2));
//                blockSession.setStartTime(cursor.getString(3));
//                blockSession.setEndTime(cursor.getString(4));
//                blockSession.setNotes(cursor.getString(5));
//
//                String log = "ID: " + blockSession.getId() + " , Name: " + blockSession.getName() + ", Date: " + blockSession.getDate()
//                        + ", StartTime: " + blockSession.getStartTime() + " , EndTime: " + blockSession.getEndTime()
//                        + ", Notes: " + blockSession.getNotes();
//
//                Log.d("BLOCK ACTIVITY result: ", log);
//
//                sessions.add(blockSession);
//
//            }while(cursor.moveToNext());
//        }
//        db.close();
//
//        for (int i = 0; i < sessions.size(); i++) {
//
//            String log = "ID: " + sessions.get(i).getId() + " , Name: " + sessions.get(i).getName() + ", Date: " + sessions.get(i).getDate()
//                    + ", StartTime: " + sessions.get(i).getStartTime() + " , EndTime: " + sessions.get(i).getEndTime()
//                    + ", Notes: " + sessions.get(i).getNotes();
//
//            Log.d("SESSIONS DATA: ", i + log);
//        }
//
//
//
//        adapter = new BlockSessionAdapter(this, R.layout.block_session_list, sessions);
//        ListView blockSessionsList = (ListView) findViewById(R.id.list_block_times);
//        blockSessionsList.setAdapter(adapter);

    }

//    public void addBlockSessionOnClick (View view) {
//        Intent intent = new Intent (this, FocusSessionActivity.class );
//        startActivity(intent);
//    }

//    public void removeBlockSessionOnClick(View view) {
//
//    }

//    public View.OnClickListener addBlockSessionListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//            Intent intent = new Intent (BlockSessionActivity.this, FocusSessionActivity.class);
//            startActivity(intent);
//        }
//    };


}
