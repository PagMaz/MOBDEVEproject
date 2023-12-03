package com.example.mainproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper2 extends SQLiteOpenHelper {

    // Database Information
    static final String DB_NAME = "POSTS.DB";

    // database version
    static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "POSTS";

    public static final String _ID = "_id";
    public static final String USERNAME = "username";
    public static final String REGION = "region";
    public static final String POSITION = "position";
    public static final String POST = "post";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + _ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USERNAME + " TEXT NOT NULL, "
            + REGION   + " TEXT NOT NULL, "
            + POSITION + " TEXT NOT NULL, "
            + POST     + " TEXT NOT NULL);";

    public DatabaseHelper2(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
