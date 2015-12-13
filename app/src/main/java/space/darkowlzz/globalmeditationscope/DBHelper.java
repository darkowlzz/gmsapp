package space.darkowlzz.globalmeditationscope;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by sunny on 10/12/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "GMS.db";

    public static final String EVENT_TABLE_NAME = "events";
    public static final String EVENT_COLUMN_ID = "id";
    public static final String EVENT_COLUMN_TITLE = "title";
    public static final String EVENT_COLUMN_DESC = "desc";
    public static final String EVENT_COLUMN_HOST = "host";
    public static final String EVENT_COLUMN_PERI_HANDLE = "periHandle";
    public static final String EVENT_COLUMN_TWITTER_HANDLE = "twitterHandle";
    public static final String EVENT_COLUMN_CATEGORY = "category";

    public DBHelper(Context context) { super(context, DB_NAME, null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + EVENT_TABLE_NAME + " (" +
                EVENT_COLUMN_ID + " integer primary key, " +
                EVENT_COLUMN_TITLE + " text, " +
                EVENT_COLUMN_DESC + " text, " +
                EVENT_COLUMN_HOST + " text, " +
                EVENT_COLUMN_PERI_HANDLE + " text, " +
                EVENT_COLUMN_TWITTER_HANDLE + " text, " +
                EVENT_COLUMN_CATEGORY + " text" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /** Event related db transactions **/

    public boolean insertEvent(Integer eventID, String title, String desc, String host,
                               String periHandle, String twitterHandle, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_COLUMN_ID, eventID);
        contentValues.put(EVENT_COLUMN_TITLE, title);
        contentValues.put(EVENT_COLUMN_DESC, desc);
        contentValues.put(EVENT_COLUMN_HOST, host);
        contentValues.put(EVENT_COLUMN_PERI_HANDLE, periHandle);
        contentValues.put(EVENT_COLUMN_TWITTER_HANDLE, twitterHandle);
        contentValues.put(EVENT_COLUMN_CATEGORY, category);

        db.insert(EVENT_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateEvent(Integer eventID, String title, String desc, String host,
                               String periHandle, String twitterHandle, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_COLUMN_ID, eventID);
        contentValues.put(EVENT_COLUMN_TITLE, title);
        contentValues.put(EVENT_COLUMN_DESC, desc);
        contentValues.put(EVENT_COLUMN_HOST, host);
        contentValues.put(EVENT_COLUMN_PERI_HANDLE, periHandle);
        contentValues.put(EVENT_COLUMN_TWITTER_HANDLE, twitterHandle);
        contentValues.put(EVENT_COLUMN_CATEGORY, category);

        db.update(EVENT_TABLE_NAME, contentValues, "id= ? ", new String[]{Integer.toString(eventID)});
        return true;
    }

    public ArrayList<MediEvent> getAllCourses() {
        MediEvent aMediEvent;
        ArrayList<MediEvent> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + EVENT_TABLE_NAME + "", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            /*
            aMediEvent = new MediEvent(res.getInt(res.getColumnIndex(EVENT_COLUMN_ID)),
                                    res.getString(res.getColumnIndex(EVENT_COLUMN_TITLE)),
                                    res.getString(res.getColumnIndex(EVENT_COLUMN_DESC)),
                                    res.getString(res.getColumnIndex(EVENT_COLUMN_HOST)),
                                    res.getString(res.getColumnIndex(EVENT_COLUMN_PERI_HANDLE)),
                                    res.getString(res.getColumnIndex(EVENT_COLUMN_TWITTER_HANDLE)),
                                    res.getString(  ===INSERT A CATEGORY HERE===      ));
                                    */
        }

        return arrayList;
    }
}
