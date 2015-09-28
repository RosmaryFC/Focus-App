package nyc.c4q.rosmaryfc.focus_app;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionDBHelper;

/**
 * Created by c4q-rosmary on 8/23/15.
 */
public class BlockSessionAdapter extends ArrayAdapter<BlockSession> {

    Context context;
    int layoutResourceId;
    ArrayList<BlockSession> data;
    boolean deleteIsEnabled;
    FragmentManager fragManager;

    public BlockSessionAdapter(Context context, int layoutResourceId, ArrayList<BlockSession> data, boolean deleteIsEnabled,FragmentManager fragManager) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
        this.deleteIsEnabled = deleteIsEnabled;
        this.fragManager = fragManager;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        BlockHolder holder = null;

        for (int i = 0; i < data.size(); i++) {

            String log = "ID: " + data.get(i).getId() + " , Name: " + data.get(i).getName() + ", Date: " + data.get(i).getDate()
                    + ", StartTime: " + data.get(i).getStartTime() + " , EndTime: " + data.get(i).getEndTime()
                    + ", Recurring:? " + data.get(i).isRecurring()
                    + ", Notes: " + data.get(i).getNotes();

            Log.d("ADAPTER DATA: ", log);

        }

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new BlockHolder();
            holder.mBlockName = (TextView) row.findViewById(R.id.txt_block_name);
            holder.mDate = (TextView) row.findViewById(R.id.txt_date);
            holder.mStartTime = (TextView) row.findViewById(R.id.txt_start_time);
            holder.mEndTime = (TextView) row.findViewById(R.id.txt_end_time);
            holder.mMenu = (ImageButton) row.findViewById(R.id.btn_menu);

//            if(deleteIsEnabled) {
//                holder.mDelete.setVisibility(View.VISIBLE);
//            }else {
//                holder.mDelete.setVisibility(View.INVISIBLE);
//            }

            row.setTag(holder);
        } else {
            holder = (BlockHolder) row.getTag();
        }



        final BlockSession session = data.get(position);
        holder.bindBlockSession(session, session.isRecurring());
       // holder.bindBlockSession(session);


        final BlockSessionDBHelper helper = new BlockSessionDBHelper(context);

        holder.mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.menu_block_session_popup_list, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.delete_block_session_item:

                                Toast.makeText(context, "Block Session Deleted", Toast.LENGTH_SHORT).show();
                                helper.deleteBlockSession(session);
                                data.remove(position);
                                notifyDataSetChanged();

                                return true;
                            //todo: fix editBlockSession item
//                            case R.id.edit_block_session_item:
//
//
//                                BlockSessionAlertDialogFragment futureBSFrag = new BlockSessionAlertDialogFragment.newInstance(session);
//                                futureBSFrag.show(fragManager, "Diag");
//
//
////                                Intent editBlockSessionIntent = new Intent(context,CreateNewBlockSessionActivity.class);
////                                editBlockSessionIntent.putExtra("block type", "future");
////                                editBlockSessionIntent.putExtra("bs id", session.getId());
////                                editBlockSessionIntent.putExtra("bs name", session.getName());
////                                editBlockSessionIntent.putExtra("bs date", session.getDate());
////                                editBlockSessionIntent.putExtra("bs start time", session.getStartTime());
////                                editBlockSessionIntent.putExtra("bs end time", session.getEndTime());
////                                editBlockSessionIntent.putExtra("bsIsBeingEdited", true);
//
////                                helper.updateBlockSession(session);
//                                Toast.makeText(context, "edit list item pressed", Toast.LENGTH_SHORT).show();
////                                notifyDataSetChanged();
//
////                                Intent startEditBSIntent = new Intent (context, CreateNewBlockSessionActivity.class);
////                                context.startActivity(startEditBSIntent);
//
//                                notifyDataSetChanged();
//
//                                return true;
                            default:
                                return false;
                        }
                    }
                });


            }
        });
        return row;
    }


    public static class BlockHolder {
        TextView mBlockName;
        TextView mStartTime;
        TextView mEndTime;
        TextView mDate;
        ImageButton mMenu;

        public void bindBlockSession(BlockSession blockSession, boolean recur){
            mDate.setText(blockSession.date);
            mBlockName.setText(blockSession.name);
            mStartTime.setText(blockSession.startTime);
             mEndTime.setText(blockSession.endTime);

        }
    }
}
