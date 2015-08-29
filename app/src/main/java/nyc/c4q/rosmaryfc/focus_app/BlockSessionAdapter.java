package nyc.c4q.rosmaryfc.focus_app;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by c4q-rosmary on 8/23/15.
 */
public class BlockSessionAdapter extends ArrayAdapter <BlockSession> {

    Context context;
    int layoutResourceId;
    ArrayList <BlockSession> data;

    public BlockSessionAdapter(Context context, int layoutResourceId, ArrayList<BlockSession> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

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
            holder.mNotes = (TextView) row.findViewById(R.id.txt_notes);
            row.setTag(holder);
        } else {
            holder = (BlockHolder) row.getTag();
        }

        BlockSession session = data.get(position);
        holder.mBlockName.setText(session.name);
        holder.mDate.setText(session.date);
        holder.mStartTime.setText(session.startTime);
        holder.mEndTime.setText(session.endTime);
        holder.mNotes.setText(session.notes);

        String log = "ID: " + session.getId() + " , Name: " + session.getName() + ", Date: " + session.getDate()
                + ", StartTime: " + session.getStartTime() + " , EndTime: " + session.getEndTime()
                + ", Notes: " + session.getNotes();

        Log.d(" ADAPTER result: ", log);

        return row;
    }

    public static class BlockHolder {
        TextView mBlockName;
        TextView mStartTime;
        TextView mEndTime;
        TextView mDate;
        TextView mNotes;
    }
}
