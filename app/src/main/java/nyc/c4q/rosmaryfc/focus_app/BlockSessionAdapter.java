package nyc.c4q.rosmaryfc.focus_app;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nyc.c4q.rosmaryfc.focus_app.db.BlockSessionDBHelper;

/**
 * Created by c4q-rosmary on 8/23/15.
 */
public class BlockSessionAdapter extends ArrayAdapter <BlockSession> implements View.OnCreateContextMenuListener{

    Context context;
    int layoutResourceId;
    ArrayList <BlockSession> data;
    boolean deleteIsEnabled;

    public BlockSessionAdapter(Context context, int layoutResourceId, ArrayList<BlockSession> data, boolean deleteIsEnabled) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
        this.deleteIsEnabled = deleteIsEnabled;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        for (int i = 0; i < data.size(); i++) {

            String log = "ID: " + data.get(i).getId() + " , Name: " + data.get(i).getName() + ", Date: " + data.get(i).getDate()
                    + ", StartTime: " + data.get(i).getStartTime() + " , EndTime: " + data.get(i).getEndTime()
                    + ", Notes: " + data.get(i).getNotes();

            Log.d("ADAPTER DATA: ", log);
        }

        View row = convertView;
        BlockHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new BlockHolder();
            holder.mBlockName = (TextView) row.findViewById(R.id.txt_block_name);
            holder.mDate = (TextView) row.findViewById(R.id.txt_date);
            holder.mStartTime = (TextView) row.findViewById(R.id.txt_start_time);
            holder.mEndTime = (TextView) row.findViewById(R.id.txt_end_time);
            holder.mDeleteBtn = (ImageButton) row.findViewById(R.id.btn_delete);
            holder.mEditBtn = (ImageButton) row.findViewById(R.id.btn_edit);
//            holder.mNotes = (TextView) row.findViewById(R.id.txt_notes);
//            holder.mMenu = (ImageButton) row.findViewById(R.id.btn_menu);


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
        holder.mBlockName.setText(session.name);
        holder.mDate.setText(session.date);
        holder.mStartTime.setText(session.startTime);
        holder.mEndTime.setText(session.endTime);
//        holder.mNotes.setText(session.notes);

        final BlockSessionDBHelper helper = new BlockSessionDBHelper(context);

        holder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.deleteBlockSession(session);
                data.remove(position);
                Toast.makeText(context, "deleteBtn pressed", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

//        holder.mEditBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                helper.updateBlockSession(session);
//                Toast.makeText(context, "editBtn pressed", Toast.LENGTH_SHORT).show();
//                notifyDataSetChanged();
//            }
//        });

//        row.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//            @Override
//            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//                AdapterViewCompat.AdapterContextMenuInfo info = (AdapterViewCompat.AdapterContextMenuInfo) contextMenuInfo;
//                currentposition = info.position;
//
//
//            }
//        });


        return row;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

    }


    public static class BlockHolder {
        TextView mBlockName;
        TextView mStartTime;
        TextView mEndTime;
        TextView mDate;
 //       TextView mNotes;
        ImageButton mMenu;
        ImageButton mDeleteBtn;
        ImageButton mEditBtn;
    }
}
