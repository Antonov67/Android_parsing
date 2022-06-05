package com.example.wiki_change;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_WIKI_PAGES = "wiki_pages";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_URL = "url";

    private static final String DATABASE_NAME = "wiki.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_WIKI_PAGES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_URL
            + " text not null);";

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WIKI_PAGES);
        onCreate(db);
    }

}
