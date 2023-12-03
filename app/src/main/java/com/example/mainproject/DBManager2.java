package com.example.mainproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager2 {
    private DatabaseHelper2 dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager2(Context c) {
        context = c;
    }

    public DBManager2 open() throws SQLException {
        dbHelper = new DatabaseHelper2(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String userName, String region, String position, String post) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper2.USERNAME, userName);
        contentValue.put(DatabaseHelper2.REGION, region);
        contentValue.put(DatabaseHelper2.POSITION, position);
        contentValue.put(DatabaseHelper2.POST, post);
        database.insert(DatabaseHelper2.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper2._ID, DatabaseHelper2.USERNAME, DatabaseHelper2.POST, DatabaseHelper2.REGION, DatabaseHelper2.POSITION };
        Cursor cursor = database.query(DatabaseHelper2.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper2.TABLE_NAME, DatabaseHelper2._ID + "=" + _id, null);
    }
}
