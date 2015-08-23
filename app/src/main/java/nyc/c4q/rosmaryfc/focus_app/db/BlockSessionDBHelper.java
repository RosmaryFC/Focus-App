package nyc.c4q.rosmaryfc.focus_app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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


}
