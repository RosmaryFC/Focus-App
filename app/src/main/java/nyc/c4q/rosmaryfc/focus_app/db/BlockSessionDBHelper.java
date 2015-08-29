package nyc.c4q.rosmaryfc.focus_app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.rosmaryfc.focus_app.BlockSession;

/**
 * Created by c4q-rosmary on 8/23/15.
 */
public class BlockSessionDBHelper extends SQLiteOpenHelper {


    public BlockSessionDBHelper(Context context) {
        super(context, BlockSessionContract.DB_NAME, null, BlockSessionContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_BLOCK_SESSIONS_TABLE = "CREATE TABLE " + BlockSessionContract.TABLE_BLOCK_SESSIONS + "("
                + BlockSessionContract.Columns.BLOCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BlockSessionContract.Columns.BLOCK_NAME + " TEXT, "
                + BlockSessionContract.Columns.BLOCK_DATE + " TEXT, "
                + BlockSessionContract.Columns.BLOCK_START_TIME + " TEXT, "
                + BlockSessionContract.Columns.BLOCK_END_TIME + " TEXT, "
                //+ BlockSessionContract.Columns.COLUMN_NAME_RECUR_WEEKDAYS + "TEXT, "
                + BlockSessionContract.Columns.BLOCK_NOTES + " TEXT" + ")";

        Log.d("BlockSessionDBHelper", "Query to form table: " + CREATE_BLOCK_SESSIONS_TABLE);
        db.execSQL(CREATE_BLOCK_SESSIONS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BlockSessionContract.TABLE_BLOCK_SESSIONS);
        onCreate(db);

    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Add new Block Session
    public void addBlockSession (BlockSession blockSession){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BlockSessionContract.Columns.BLOCK_NAME, blockSession.getName());
        values.put(BlockSessionContract.Columns.BLOCK_DATE, blockSession.getDate());
        values.put(BlockSessionContract.Columns.BLOCK_START_TIME, blockSession.getStartTime());
        values.put(BlockSessionContract.Columns.BLOCK_END_TIME, blockSession.getEndTime());
        values.put(BlockSessionContract.Columns.BLOCK_NOTES, blockSession.getNotes());

        db.insert(BlockSessionContract.TABLE_BLOCK_SESSIONS, null, values);
        db.close();
    }

    //get Single Block Session
    public BlockSession getBlockSession (int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(BlockSessionContract.TABLE_BLOCK_SESSIONS,
                 new String []{BlockSessionContract.Columns.BLOCK_ID,
                         BlockSessionContract.Columns.BLOCK_NAME,
                         BlockSessionContract.Columns.BLOCK_DATE,
                         BlockSessionContract.Columns.BLOCK_START_TIME,
                         BlockSessionContract.Columns.BLOCK_END_TIME,
                         BlockSessionContract.Columns.BLOCK_NOTES},
                BlockSessionContract.Columns.BLOCK_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        BlockSession blockSession = new BlockSession(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getString(5));

        return blockSession;
    }

    //get All Block Sessions
    public List<BlockSession> getAllBlockSessions(){
        List<BlockSession> blockSessionList = new ArrayList<BlockSession>();

        //selecting all query
        String selectQuery = "SELECT * FROM BlockSessions ORDER BY blockname";// + BlockSessionContract.Columns.BLOCK_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
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
                //add block session to list
                blockSessionList.add(blockSession);
            }while(cursor.moveToNext());
        }
        db.close();

        return blockSessionList;
    }

    //updating single BlockSession
    public int updateBlockSession (BlockSession blockSession) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BlockSessionContract.Columns.BLOCK_NAME, BlockSession.getName());
        values.put(BlockSessionContract.Columns.BLOCK_DATE, BlockSession.getDate());
        values.put(BlockSessionContract.Columns.BLOCK_START_TIME, BlockSession.getStartTime());
        values.put(BlockSessionContract.Columns.BLOCK_END_TIME, BlockSession.getEndTime());
        values.put(BlockSessionContract.Columns.BLOCK_NOTES, BlockSession.getNotes());

        //update Row
        return db.update(BlockSessionContract.TABLE_BLOCK_SESSIONS, values, BlockSessionContract.Columns.BLOCK_ID + " = ?",
                new String[] {String.valueOf(blockSession.getId()) });
    }

        //delete BlockSession
    public void deleteBlockSession (BlockSession blockSession) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(BlockSessionContract.TABLE_BLOCK_SESSIONS, BlockSessionContract.Columns.BLOCK_ID + " = ?",
                new String[]{String.valueOf(BlockSession.getId())});
        db.close();
    }

    //get BlockSession count
    public int getBlockSessionCount () {
        String countQuery = "SELECT * FROM " + BlockSessionContract.TABLE_BLOCK_SESSIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null );
        cursor.close();

        //returning count
        return cursor.getCount();
    }
}
