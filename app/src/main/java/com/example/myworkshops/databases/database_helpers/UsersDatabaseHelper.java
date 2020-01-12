package com.example.myworkshops.databases.database_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import static com.example.myworkshops.databases.ContractClass.UsersTable;

public class UsersDatabaseHelper extends SQLiteOpenHelper {

    private static String TABLE_NAME = "users_table.db";
    private static int DATABASE_VERSION = 1;
    private static final String TAG = "UsersDatabaseHelper";

    private SQLiteDatabase db;

    public UsersDatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db = sqLiteDatabase;
        final String SQL_CREATE_WORKSHOPS_TABLE = "CREATE TABLE " +
                UsersTable.TABLE_NAME + " ( " +
                UsersTable.COLUMN_EMAID_ID + " TEXT PRIMARY KEY, " +
                UsersTable.COLUMN_USER_NAME + " TEXT, " +
                UsersTable.COLUMN__USER_PASSWORD + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_WORKSHOPS_TABLE);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + UsersTable.TABLE_NAME);
        onCreate(db);
    }

    public boolean checkAlredyExists(String emailId) {
        String query = "SELECT * FROM " + UsersTable.TABLE_NAME +
                " WHERE " + UsersTable.COLUMN_EMAID_ID + " =?";
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{emailId});
        int count = cursor.getCount();
        cursor.close();
        return (count > 0);

    }

    public boolean authenticated(String email, String password) {
        String query = "SELECT " + UsersTable.COLUMN__USER_PASSWORD +
                " FROM " + UsersTable.TABLE_NAME +
                " WHERE " + UsersTable.COLUMN_EMAID_ID + " =?";

        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor.moveToNext() && password.equals(cursor.getString(
                cursor.getColumnIndex(UsersTable.COLUMN__USER_PASSWORD)))) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public String getUserName(String email) {
        db = getReadableDatabase();
        String name = null;
        String query = "SELECT *" +
                " FROM " + UsersTable.TABLE_NAME +
                " WHERE " + UsersTable.COLUMN_EMAID_ID + " = '" + email + "'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_USER_NAME));
        }
        cursor.close();
        return name;
    }

    public void putUser(String name, String emailId, String password) {

        ContentValues cv = new ContentValues();
        cv.put(UsersTable.COLUMN_USER_NAME, name);
        cv.put(UsersTable.COLUMN_EMAID_ID, emailId);
        cv.put(UsersTable.COLUMN__USER_PASSWORD, password);

        db.insert(UsersTable.TABLE_NAME, null, cv);

    }
}
