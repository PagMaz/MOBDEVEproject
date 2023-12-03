package com.example.mainproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String userName, String password, String firstName, String lastName) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.USERNAME, userName);
        contentValue.put(DatabaseHelper.PASSWORD, password);
        contentValue.put(DatabaseHelper.FIRST, firstName);
        contentValue.put(DatabaseHelper.LAST, lastName);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.USERNAME, DatabaseHelper.PASSWORD, DatabaseHelper.FIRST, DatabaseHelper.LAST };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

//    public int update(long _id, String userName, String password) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.USERNAME, userName);
//        contentValues.put(DatabaseHelper.PASSWORD, password);
//        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
//        return i;
//    }

    public boolean isUsernameExists(String username) {
        Cursor cursor = database.rawQuery(
                "SELECT 1 FROM "
                        + DatabaseHelper.TABLE_NAME
                        + " WHERE "
                        + DatabaseHelper.USERNAME
                        + " = ?", new String[]{username});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}
