package nyc.c4q.rosmaryfc.focus_app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nyc.c4q.rosmaryfc.focus_app.BlockSession;

import static nyc.c4q.rosmaryfc.focus_app.db.BlockSessionContract.Columns.BLOCK_DATE;
import static nyc.c4q.rosmaryfc.focus_app.db.BlockSessionContract.Columns.BLOCK_END_TIME;
import static nyc.c4q.rosmaryfc.focus_app.db.BlockSessionContract.Columns.BLOCK_ID;
import static nyc.c4q.rosmaryfc.focus_app.db.BlockSessionContract.Columns.BLOCK_NAME;
import static nyc.c4q.rosmaryfc.focus_app.db.BlockSessionContract.Columns.BLOCK_NOTES;
import static nyc.c4q.rosmaryfc.focus_app.db.BlockSessionContract.Columns.BLOCK_START_TIME;

/**
 * Created by c4q-rosmary on 8/23/15.
 */
public class BlockSessionDBHelper extends SQLiteOpenHelper {


    public BlockSessionDBHelper(Context context) {
        super(context, BlockSessionContract.DB_NAME, null, BlockSessionContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_BLOCK_SESSIONS_TABLE =
                "CREATE TABLE " + BlockSessionContract.TABLE_BLOCK_SESSIONS + " ("
                + BLOCK_ID + " INTEGER PRIMARY KEY, "
                + BlockSessionContract.Columns.BLOCK_NAME + " TEXT, "
                + BlockSessionContract.Columns.BLOCK_DATE + " TEXT, "
                + BlockSessionContract.Columns.BLOCK_START_TIME + " TEXT, "
                + BlockSessionContract.Columns.BLOCK_END_TIME + " TEXT, "
                + BlockSessionContract.Columns.BLOCK_NOTES + " TEXT, "
                + BlockSessionContract.Columns.RECUR_BOOL + " BOOL, "
                + BlockSessionContract.Columns.BLOCK_DAYS + " TEXT " +
                        ")";

        Log.d("BlockSessionDBHelper", "Query to form table: " + CREATE_BLOCK_SESSIONS_TABLE);
        db.execSQL(CREATE_BLOCK_SESSIONS_TABLE);

        //+ BlockSessionContract.Columns.COLUMN_NAME_RECUR_WEEKDAYS + "TEXT, "

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BlockSessionContract.TABLE_BLOCK_SESSIONS);
        onCreate(db);

    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public void addblockSession(BlockSession blockSession){
        addBlockSession(blockSession);
        //calculate recurring schedule and duration //fixme
//        for(BlockSession bs : blockSession.makeRecurring("test", "test", "test")){
//            addBlockSession(bs);
//        }
    }


    //Add new Block Session
    public void addBlockSession(BlockSession blockSession) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BlockSessionContract.Columns.BLOCK_NAME, blockSession.getName());
        values.put(BlockSessionContract.Columns.BLOCK_DATE, blockSession.getDate());
        values.put(BlockSessionContract.Columns.BLOCK_START_TIME, blockSession.getStartTime());
        values.put(BlockSessionContract.Columns.BLOCK_END_TIME, blockSession.getEndTime());
        values.put(BlockSessionContract.Columns.BLOCK_NOTES, blockSession.getNotes());
        values.put(BlockSessionContract.Columns.RECUR_BOOL, blockSession.isRecurring());
        values.put(BlockSessionContract.Columns.BLOCK_DAYS, blockSession.getDaysToRecur());
        db.insert(BlockSessionContract.TABLE_BLOCK_SESSIONS, null, values);
        db.close();
    }



    //get Single Block Session
    public BlockSession getBlockSession(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(BlockSessionContract.TABLE_BLOCK_SESSIONS,
                new String[]{BLOCK_ID,
                        BlockSessionContract.Columns.BLOCK_NAME,
                        BlockSessionContract.Columns.BLOCK_DATE,
                        BlockSessionContract.Columns.BLOCK_START_TIME,
                        BlockSessionContract.Columns.BLOCK_END_TIME,
                        BlockSessionContract.Columns.BLOCK_NOTES},
                BLOCK_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        BlockSession blockSession = new BlockSession(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

        return blockSession;
    }

    //todo: possibly order by date and start time and end time here and remove other method
    //get All Block Sessions
    public List<BlockSession> getAllBlockSessions() {
        List<BlockSession> blockSessionList = new ArrayList<BlockSession>();

        //selecting all query
        String selectQuery = "SELECT * FROM BlockSessions ORDER BY " + BLOCK_ID;// + BlockSessionContract.Columns.BLOCK_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to the list
        if (cursor.moveToFirst()) {
            do {
                BlockSession blockSession = new BlockSession();
                blockSession.setId(cursor.getInt(0));
                blockSession.setName(cursor.getString(1));
                blockSession.setDate(cursor.getString(2));
                blockSession.setStartTime(cursor.getString(3));
                blockSession.setEndTime(cursor.getString(4));
                blockSession.setNotes(cursor.getString(5));

                String log = "ID: " + blockSession.getId() + " , Name: " + blockSession.getName() + ", Date: " + blockSession.getDate()
                        + ", StartTime: " + blockSession.getStartTime() + " , EndTime: " + blockSession.getEndTime()
                        + ", Notes: " + blockSession.getNotes();

                Log.d("DBHELPER result: ", log);

                //add block session to list
                blockSessionList.add(blockSession);
            } while (cursor.moveToNext());
        }
        db.close();

        return blockSessionList;
    }

    //updating single BlockSession
    public int updateBlockSession(BlockSession blockSession) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BlockSessionContract.Columns.BLOCK_NAME, blockSession.getName());
        values.put(BlockSessionContract.Columns.BLOCK_DATE, blockSession.getDate());
        values.put(BlockSessionContract.Columns.BLOCK_START_TIME, blockSession.getStartTime());
        values.put(BlockSessionContract.Columns.BLOCK_END_TIME, blockSession.getEndTime());
        values.put(BlockSessionContract.Columns.BLOCK_NOTES, blockSession.getNotes());

        //update Row
        return db.update(BlockSessionContract.TABLE_BLOCK_SESSIONS, values, BLOCK_ID + " = ?",
                new String[]{String.valueOf(blockSession.getId())});
    }

    //delete BlockSession
    public void deleteBlockSession(BlockSession blockSession) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(BlockSessionContract.TABLE_BLOCK_SESSIONS, BLOCK_ID + " = ?",
                new String[]{String.valueOf(blockSession.getId())});
        db.close();
    }

    //get BlockSession count
    public int getBlockSessionCount() {
        String countQuery = "SELECT * FROM " + BlockSessionContract.TABLE_BLOCK_SESSIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        //returning count
        return cursor.getCount();
    }

    // get next BlockSession
    public List<BlockSession> orderedBlockSessions() {
        List<BlockSession> blockSessionList = new ArrayList<>();
        //fixme Modify query to FIRST SELECT FROM LIST ORDER WHERE RECURRING IS ON TO CHECK IF ANY ARE SET TO RECUR TODAY..Then

        //created query by ordered dates and ordered start times and ordered end times
        String selectQuery = "SELECT * FROM BlockSessions ORDER BY " + BlockSessionContract.Columns.BLOCK_DATE + ", " + BlockSessionContract.Columns.BLOCK_START_TIME + ", " + BlockSessionContract.Columns.BLOCK_END_TIME;



        Log.d("UPCOMING BS: ", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to the list
        while (cursor.moveToNext()) {
            BlockSession blockSession = new BlockSession();
            blockSession.setId(cursor.getInt(cursor.getColumnIndex(BLOCK_ID)));
            blockSession.setName(cursor.getString(cursor.getColumnIndex(BLOCK_NAME)));
            blockSession.setDate(cursor.getString(cursor.getColumnIndex(BLOCK_DATE)));
            blockSession.setStartTime(cursor.getString(cursor.getColumnIndex(BLOCK_START_TIME)));
            blockSession.setEndTime(cursor.getString(cursor.getColumnIndex(BLOCK_END_TIME)));
            blockSession.setNotes(cursor.getString(cursor.getColumnIndex(BLOCK_NOTES)));

            String log = "ID: " + blockSession.getId() + " , Name: " + blockSession.getName() + ", Date: " + blockSession.getDate()
                    + ", StartTime: " + blockSession.getStartTime() + " , EndTime: " + blockSession.getEndTime()
                    + ", Notes: " + blockSession.getNotes();

            Log.d("DBHELPER result: ", log);

            //add block session to list
            blockSessionList.add(blockSession);
        }

        db.close();

        return blockSessionList;

//        if(cursor != null) {
//            if(cursor.moveToFirst()){
//                bs.setName(cursor.getString(1));
//                bs.setDate(cursor.getString(2));
//                bs.setStartTime(cursor.getString(3));
//                bs.setEndTime(cursor.getString(4));
//                bs.setNotes(cursor.getString(5));
//            }
//
//        }
//
//        String log = "ID: " + bs.getId() + " , Name: " + bs.getName() + ", Date: " + bs.getDate()
//                + ", StartTime: " + bs.getStartTime() + " , EndTime: " + bs.getEndTime()
//                + ", Notes: " + bs.getNotes();
//
//        Log.d("DBHELPER UPCOMING BS: ", log);
//
//        cursor.close();
//        db.close();
//
//        return bs;
    }


}
