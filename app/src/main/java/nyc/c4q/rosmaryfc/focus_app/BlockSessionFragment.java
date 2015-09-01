package nyc.c4q.rosmaryfc.focus_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionContract;
import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionDBHelper;
import nyc.c4q.rosmaryfc.focus_app.ui.FocusSessionActivity;

/**
 * Created by c4q-rosmary on 9/1/15.
 */
public class BlockSessionFragment extends Fragment {

    private ArrayList<BlockSession> sessions = new ArrayList<BlockSession>();
    private BlockSessionAdapter adapter;
    private BlockSessionDBHelper helper;

    View blockSessionView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        blockSessionView = inflater.inflate(R.layout.activity_block_session, container, false);

        updateUI();

//        Button addBtn = (Button) blockSessionView.findViewById(R.id.btn_add);
//        addBtn.setOnClickListener(addBlockSessionListener);

        return blockSessionView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_block_session, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_BlockSession:

                Intent intent = new Intent (blockSessionView.getContext(), FocusSessionActivity.class);
                startActivity(intent);

                break;
            case R.id.action_del_BlockSession:
                //
                break;
            case R.id.action_settings:
                //
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void updateUI (){

        String selectQuery = "SELECT * FROM " + BlockSessionContract.TABLE_BLOCK_SESSIONS;

        helper = new BlockSessionDBHelper(blockSessionView.getContext());
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

                Log.d("BLOCK ACTIVITY result: ", log);

                sessions.add(blockSession);

            }while(cursor.moveToNext());
        }
        db.close();

        for (int i = 0; i < sessions.size(); i++) {

            String log = "ID: " + sessions.get(i).getId() + " , Name: " + sessions.get(i).getName() + ", Date: " + sessions.get(i).getDate()
                    + ", StartTime: " + sessions.get(i).getStartTime() + " , EndTime: " + sessions.get(i).getEndTime()
                    + ", Notes: " + sessions.get(i).getNotes();

            Log.d("SESSIONS DATA: ", i + log);
        }

        adapter = new BlockSessionAdapter(blockSessionView.getContext(), R.layout.block_session_list, sessions);
        ListView blockSessionsList = (ListView) blockSessionView.findViewById(R.id.list_block_times);
        blockSessionsList.setAdapter(adapter);

    }

//        public View.OnClickListener addBlockSessionListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//            Intent intent = new Intent (blockSessionView.getContext(), FocusSessionActivity.class);
//            startActivity(intent);
//        }
//    };



}
