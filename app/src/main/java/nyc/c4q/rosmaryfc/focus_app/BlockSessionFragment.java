package nyc.c4q.rosmaryfc.focus_app;

import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionDBHelper;
import nyc.c4q.rosmaryfc.focus_app.ui.CreateNewBlockSessionActivity;

/**
 * Created by c4q-rosmary on 9/1/15.
 */
public class BlockSessionFragment extends Fragment {

    private ArrayList<BlockSession> sessions = new ArrayList<BlockSession>();
    private BlockSessionAdapter adapter;
    private BlockSessionDBHelper helper;

    private ListView blockSessionsList;
    private View blockSessionView;

    boolean blockSessionIsEnabled; // todo: used to make bs clock visible for active block session
    boolean blockSessionIsRecurring; //todo:

    protected String blockType;

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

        return blockSessionView;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onResume() {
        Log.d("BS FRAG On RESUME: ", "...");

        //blockSessionsList.invalidateViews();
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("BS FRAG On PAUSE: ", ".....");
        super.onPause();

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_block_session, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                //
                return true;
            case R.id.start_bs_now_item:
                blockType = "immediate";

            //todo: will not be added in database, user will specify length of block session up to 6 hours
                // have option to disable current block session by going into settings.
                // will kill appmonitor but then take into consideration that block service will keep checking bs list and reactivate
                //possible have two tables, one of enabled block sessions, another with disabled block sessions

                Toast.makeText(blockSessionView.getContext(), "start bs now pressed", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.recur_bs_item:
                blockType = "recur";

                Toast.makeText(blockSessionView.getContext(), "recur bs now pressed",Toast.LENGTH_SHORT).show();
//todo: finish adding recur to database
//                Intent recurIntent = new Intent (blockSessionView.getContext(), CreateNewBlockSessionActivity.class);
//                recurIntent.putExtra("block type", blockType);
//                startActivity(recurIntent);

                return true;
            case R.id.future_bs_item:
                blockType = "future";

                Toast.makeText(blockSessionView.getContext(), "future bs now pressed",Toast.LENGTH_SHORT).show();

                Intent futureIntent = new Intent(blockSessionView.getContext(), CreateNewBlockSessionActivity.class);
                futureIntent.putExtra("block type", blockType);
                startActivity(futureIntent);

//                BlockSessionAlertDialogFragment futureBSDialogFrag = new BlockSessionAlertDialogFragment();
//                futureBSDialogFrag.show(getFragmentManager(), "Future Block Session Frag");
//
//                adapter.notifyDataSetChanged();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

        adapter = new BlockSessionAdapter(blockSessionView.getContext(), R.layout.block_session_row, sessions, blockSessionIsEnabled);
        blockSessionsList = (ListView) blockSessionView.findViewById(R.id.list_block_times);
        blockSessionsList.setAdapter(adapter);
    }


}


//            case R.id.action_add_BlockSession:
//                Intent intent = new Intent(blockSessionView.getContext(), FocusSessionActivity.class);
//                startActivity(intent);
//                return true;
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

