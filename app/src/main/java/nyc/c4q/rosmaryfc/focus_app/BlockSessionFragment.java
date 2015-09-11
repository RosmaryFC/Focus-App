package nyc.c4q.rosmaryfc.focus_app;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

    ListView blockSessionsList;
    private boolean deleteBtnIsEnabled;

    View blockSessionView;

    ImageButton menuBtn;

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

        //registerForContextMenu(blockSessionsList);

        return blockSessionView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_block_session, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        android.view.MenuInflater inflater = getActivity().getMenuInflater();
//        inflater.inflate(R.menu.menu_block_session_context, menu);
//    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        switch(item.getItemId())
//        {
//            case R.id.edit_block_session_item:
//            {
//
//
//            }
//            break;
//            case R.id.delete_block_session_item:
//            {
//                Log.d("BS FRAG ITEM ID: ", item.getItemId() + " , MENU INFO: " + item.getMenuInfo());
//
//                item.
//
//
//                //                View v = (View) view.getParent();
////                TextView blockNameTv = (TextView) v.findViewById(R.id.txt_block_name);
////                String blockName = blockNameTv.getText().toString();
////
////                String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
////                        BlockSessionContract.TABLE_BLOCK_SESSIONS,
////                        BlockSessionContract.Columns.BLOCK_NAME,
////                        blockName);
////
////                helper = new BlockSessionDBHelper(blockSessionView.getContext());
////                SQLiteDatabase sqlDB = helper.getWritableDatabase();
////                sqlDB.execSQL(sql);
////                updateUI();
//
//                helper.deleteBlockSession();
//
//            }
//            break;
//        }
//
//        return super.onContextItemSelected(item);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_BlockSession:

                Intent intent = new Intent(blockSessionView.getContext(), FocusSessionActivity.class);
                startActivity(intent);

                break;
//            case R.id.action_del_BlockSession:
//                //TODO: get Delete button to work - when enabled set del btn to visible. keeps re-adding listview
////                int counter = 0;
////                counter ++;
////
////                if(counter % 2 == 0){
////                    deleteBtnIsEnabled = false;
////                   // deleteBtn.setOnClickListener(null);
//                        //adapter.notifydataSetChanged or something
////                    updateUI();
////
////                } else {
////                    deleteBtnIsEnabled = true;
//                //adapter.notifydataSetChanged or something
//
////                    updateUI();
////                }
//
//                break;
            case R.id.action_settings:
                //
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void updateUI() {
        helper = new BlockSessionDBHelper(blockSessionView.getContext());

        //CRUD Operations
        Log.d("BSActivity: ", "INSERTING...");
        List<BlockSession> blockSessions = helper.getAllBlockSessions();

        if(blockSessions.isEmpty()){
            helper.addBlockSession(new BlockSession("Test three added first", "2015/09/8", "12:30", "12:45", "no notes"));
            helper.addBlockSession(new BlockSession("Test four added second", "2015/09/8", "12:15", "12:35", "no notes"));
//            helper.addBlockSession(new BlockSession("Test one added third", "2015/09/8", "12:00", "12:10", "no notes"));
//            helper.addBlockSession(new BlockSession("Test two added fourth", "2015/09/8", "12:15", "12:25", "no notes"));
        }

        for (BlockSession bs : blockSessions) {
            String log = "ID: " + bs.getId() + " , Name: " + bs.getName() + ", Date: " + bs.getDate()
                    + ", StartTime: " + bs.getStartTime() + " , EndTime: " + bs.getEndTime()
                    + ", Notes: " + bs.getNotes();

            Log.d("Result: ", log);
            sessions.add(bs); //adding contacts data into array list
        }

        adapter = new BlockSessionAdapter(blockSessionView.getContext(), R.layout.block_session_list, sessions, deleteBtnIsEnabled);
        blockSessionsList = (ListView) blockSessionView.findViewById(R.id.list_block_times);
        blockSessionsList.setAdapter(adapter);
    }

    public View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            View v = (View) view.getParent();
            TextView blockNameTv = (TextView) v.findViewById(R.id.txt_block_name);
            String blockName = blockNameTv.getText().toString();

            String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                    BlockSessionContract.TABLE_BLOCK_SESSIONS,
                    BlockSessionContract.Columns.BLOCK_NAME,
                    blockName);

            helper = new BlockSessionDBHelper(blockSessionView.getContext());
            SQLiteDatabase sqlDB = helper.getWritableDatabase();
            sqlDB.execSQL(sql);
            updateUI();
        }
    };


}
