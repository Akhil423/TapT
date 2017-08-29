package com.project.greenhat.tap_t;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by greenhat on 24/8/17.
 */

public class database extends SQLiteOpenHelper {
    public database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS tstat(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERID TEXT,TWEETID TEXT,URL TEXT,TIMESTAMP TEXT,IMAGE TEXT )");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
