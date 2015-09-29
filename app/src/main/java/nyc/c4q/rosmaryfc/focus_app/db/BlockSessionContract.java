package nyc.c4q.rosmaryfc.focus_app.db;

import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c4q-rosmary on 8/23/15.
 */
public class BlockSessionContract {
    public static final String DB_NAME = "focus_app.db";
    public static final int DB_VERSION = 2;
    public static final String TABLE_BLOCK_SESSIONS = "blocksessions";

    public BlockSessionContract(){

    }

    public static abstract class Columns implements BaseColumns {
        public static final String BLOCK_ID = BaseColumns._ID;
        public static final String BLOCK_NAME = "blockname";
        public static final String BLOCK_START_TIME = "starttime";
        public static final String BLOCK_END_TIME = "endtime";
        public static final String BLOCK_DATE = "date";
        public static final String RECUR_BOOL = "recurring";
        public static final String BLOCK_DAYS = "daysToRecur";
        public static final String BLOCK_NOTES = "notes";
    }

    public static class DayStrings {
        public static final String SUNDAY = "SUNDAY";
        public static final String MONDAY = "MONDAY";
        public static final String TUESDAY = "TUESDAY";
        public static final String WEDNESDAY = "WEDNESDAY";
        public static final String THURSDAY = "THURSDAY";
        public static final String FRIDAY = "FRIDAY";
        public static final String SATURDAY = "SATURDAY";
    }
}
