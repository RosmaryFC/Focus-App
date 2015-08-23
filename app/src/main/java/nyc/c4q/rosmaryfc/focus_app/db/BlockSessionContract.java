package nyc.c4q.rosmaryfc.focus_app.db;

import android.provider.BaseColumns;

/**
 * Created by c4q-rosmary on 8/23/15.
 */
public class BlockSessionContract {
    public static final String DB_NAME = "nyc.c4q.rosmaryfc.focus_app.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_BLOCK_SESSIONS = "Block Sessions";

    public BlockSessionContract(){

    }

    public static abstract class Columns implements BaseColumns {
        public static final String COLUMN_NAME_BLOCK_SESSION_ID = "SessionId";
        public static final String COLUMN_NAME_BlOCK_NAME = "BlockName";
        public static final String COLUMN_NAME_START_TIME = "StartTime";
        public static final String COLUMN_NAME_END_TIME = "EndTime";
        public static final String COLUMN_NAME_DATE = "Date";
        public static final String COLUMN_NAME_RECUR_WEEKDAYS = "RecurDays";
        public static final String COLUMN_NAME_NOTES = "Notes";
    }
}
