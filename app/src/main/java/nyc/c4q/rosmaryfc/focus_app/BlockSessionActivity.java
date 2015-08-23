package nyc.c4q.rosmaryfc.focus_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionDBHelper;


public class BlockSessionActivity extends AppCompatActivity {

    ArrayList <BlockSession> sessions = new ArrayList<BlockSession>();
    BlockSessionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_session);

        BlockSessionDBHelper db = new BlockSessionDBHelper(this);

        //CRUD Operations
        Log.d("BSActivity: ", "INSERTING...");
        db.addBlockSession(new BlockSession("Test Block Time", "23/8/2015", "12:00", "12:30", "No Notes"));
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
    }


}
