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

    public Cursor fetchByRegion(String region) {
        String[] columns = new String[]{DatabaseHelper2._ID, DatabaseHelper2.USERNAME, DatabaseHelper2.POST, DatabaseHelper2.REGION, DatabaseHelper2.POSITION};
        String selection = DatabaseHelper2.REGION + " = ?";
        String[] selectionArgs = new String[]{region};
        String groupBy = null;
        String having = null;
        String orderBy = null;

        return database.query(DatabaseHelper2.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public Cursor fetchByPosition(String region) {
        String[] columns = new String[]{DatabaseHelper2._ID, DatabaseHelper2.USERNAME, DatabaseHelper2.POST, DatabaseHelper2.REGION, DatabaseHelper2.POSITION};
        String selection = DatabaseHelper2.POSITION + " = ?";
        String[] selectionArgs = new String[]{region};
        String groupBy = null;
        String having = null;
        String orderBy = null;

        return database.query(DatabaseHelper2.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public Cursor fetchSortedByUsername(String sort) {
        String type;
        if (sort.equals("Ascending")) {
            type = "ASC";
        }
        else {
            type = "DESC";
        }
        String query = "SELECT * FROM " + DatabaseHelper2.TABLE_NAME + " ORDER BY " + DatabaseHelper2.USERNAME + " " + type; // You can change "ASC" to "DESC" for descending order
        return database.rawQuery(query, null);
    }

    public Cursor fetchByUsername(String username) {
        String query = "SELECT * FROM " + DatabaseHelper2.TABLE_NAME + " WHERE " + DatabaseHelper2.USERNAME + " LIKE '%" + username + "%'";
        return database.rawQuery(query, null);
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper2.TABLE_NAME, DatabaseHelper2._ID + "=" + _id, null);
    }
}
