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
                + BlockSessionContract.Columns.COLUMN_NAME_BLOCK_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BlockSessionContract.Columns.COLUMN_NAME_BlOCK_NAME + " TEXT, "
                + BlockSessionContract.Columns.COLUMN_NAME_START_TIME + " TEXT, "
                + BlockSessionContract.Columns.COLUMN_NAME_END_TIME + " TEXT, "
                + BlockSessionContract.Columns.COLUMN_NAME_DATE + " TEXT, "
                + BlockSessionContract.Columns.COLUMN_NAME_RECUR_WEEKDAYS + "TEXT, "
                + BlockSessionContract.Columns.COLUMN_NAME_NOTES + "TEXT" + ")";

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
        values.put(BlockSessionContract.Columns.COLUMN_NAME_BlOCK_NAME, blockSession.getName());
        values.put(BlockSessionContract.Columns.COLUMN_NAME_DATE, blockSession.getDate());
        values.put(BlockSessionContract.Columns.COLUMN_NAME_START_TIME, blockSession.getStartTime());
        values.put(BlockSessionContract.Columns.COLUMN_NAME_END_TIME, blockSession.getEndTime());
        values.put(BlockSessionContract.Columns.COLUMN_NAME_NOTES, blockSession.getNotes());

        db.insert(BlockSessionContract.TABLE_BLOCK_SESSIONS, null, values);
        db.close();
    }

    //get All Block Sessions
    public List<BlockSession> getAllBlockSessions(){
        List<BlockSession> blockSessionList = new ArrayList<BlockSession>();

        //selecting all query
        String selectQuery = "SELECT * FROM " + BlockSessionContract.TABLE_BLOCK_SESSIONS + " ORDER BY name";// + BlockSessionContract.Columns.COLUMN_NAME_BlOCK_NAME;

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


}
