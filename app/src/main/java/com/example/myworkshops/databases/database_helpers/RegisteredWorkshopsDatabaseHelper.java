package com.example.myworkshops.databases.database_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myworkshops.model.Workshop;

import java.util.ArrayList;
import java.util.List;

import static com.example.myworkshops.databases.ContractClass.WorkshopsRegistered;

public class RegisteredWorkshopsDatabaseHelper extends SQLiteOpenHelper {

    private static String TABLE_NAME = "registered_workshops.db";
    private static int DATABASE_VERSION = 1;
    private static final String TAG = "UsersDatabaseHelper";

    private SQLiteDatabase db;

    public RegisteredWorkshopsDatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db = sqLiteDatabase;
        final String SQL_CREATE_WORKSHOPS_TABLE = "CREATE TABLE " +
                WorkshopsRegistered.TABLE_NAME + " ( " +
                WorkshopsRegistered._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WorkshopsRegistered.USER_ID + " TEXT, " +
                WorkshopsRegistered.WORKSHOP_ID + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_WORKSHOPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + WorkshopsRegistered.TABLE_NAME);
        onCreate(db);
    }

    public boolean alreadyRegisterd(String email, int workshop_id) {
        db = getReadableDatabase();
        String query = "SELECT " + WorkshopsRegistered.USER_ID +
                " FROM " + WorkshopsRegistered.TABLE_NAME +
                " WHERE " + WorkshopsRegistered.USER_ID +
                " = '" + email + "' AND " +
                WorkshopsRegistered.WORKSHOP_ID +
                " = " + workshop_id;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        return (count > 0);
    }

    public void registerWorkshop(String email, int workshopId) {
        ContentValues cv = new ContentValues();
        cv.put(WorkshopsRegistered.USER_ID, email);
        cv.put(WorkshopsRegistered.WORKSHOP_ID, workshopId);
        db.insert(WorkshopsRegistered.TABLE_NAME, null, cv);

    }

    public List<Integer> getRegisteredWorkshops(String emailId) {
        List<Integer> workshopIds = new ArrayList<>();
        db = getReadableDatabase();
        String query = "SELECT *" +
                " FROM " + WorkshopsRegistered.TABLE_NAME +
                " WHERE " + WorkshopsRegistered.USER_ID +
                " = '" + emailId + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex(WorkshopsRegistered.WORKSHOP_ID));
                workshopIds.add(id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return workshopIds;
    }
}
