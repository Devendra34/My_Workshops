package com.example.myworkshops.databases.database_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myworkshops.model.Workshop;

import java.util.ArrayList;
import java.util.List;

import static com.example.myworkshops.databases.ContractClass.WorkshopsTable;

public class WorkshopsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "workshops.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public WorkshopsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db = sqLiteDatabase;

        final String SQL_CREATE_WORKSHOPS_TABLE = "CREATE TABLE " +
                WorkshopsTable.TABLE_NAME + " ( " +
                WorkshopsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WorkshopsTable.COLUMN_COURSE_NAME + " TEXT, " +
                WorkshopsTable.COLUMN_INSTRUCTOR_NAME + " TEXT, " +
                WorkshopsTable.COLUMN_COURSE_DESCRIPTION + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_WORKSHOPS_TABLE);
        fillWorkshopsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + WorkshopsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillWorkshopsTable() {
        Workshop w1 = new Workshop("DevOps",
                "Taurius Litvinavicious",
                "Number of Sections: 10\nNumber of Lectures: 17");
        addWorkshop(w1);
        Workshop w2 = new Workshop("Kotlin for Android",
                "Joe Parsy",
                "Number of Sections: 15\nNumber of Lectures: 191");
        addWorkshop(w2);
        Workshop w3 = new Workshop("Ruby Programming",
                "DoEdu IT Solutions",
                "Number of Sections: 2\nNumber of Lectures: 13");
        addWorkshop(w3);
        Workshop w4 = new Workshop("PHP for Beginners",
                "Melta Sudha Sekhar",
                "Number of Sections: 5\nNumber of Lectures: 26");
        addWorkshop(w4);
        Workshop w5 = new Workshop("JavaScript Certification",
                "YouAccel Training",
                "Number of Sections: 2\nNumber of Lectures: 81");
        addWorkshop(w5);
        Workshop w6 = new Workshop("Python for Excel Users",
                "Derrick Sherril",
                "Number of Sections: 15\nNumber of Lectures: 70");
        addWorkshop(w6);
        Workshop w7 = new Workshop("Hacking Tools in Kali Linux",
                "Amit Huddar",
                "Number of Sections: 18\nNumber of Lectures: 184");
        addWorkshop(w7);
        Workshop w8 = new Workshop("The Complete Cyber Security",
                "Derrick Sherrik",
                "Number of Sections: 15\nNumber of Lectures: 70");
        addWorkshop(w8);
        Workshop w9 = new Workshop("Pearl Programming",
                "Steven Hobs",
                "Number of Sections: 18\nNumber of Lectures: 84");
        addWorkshop(w9);
        Workshop w10 = new Workshop("HTML Fundamentals",
                "Web Solution Tech",
                "Number of Sections: 9\nNumber of Lectures: 70");
        addWorkshop(w10);
        Workshop w11 = new Workshop("Android Core Topics",
                "Allen Stark",
                "Number of Sections: 38\nNumber of Lectures: 284");
        addWorkshop(w11);
        Workshop w12 = new Workshop("Natural Language",
                "BarkHausen Pots",
                "Number of Sections: 15\nNumber of Lectures: 90");
        addWorkshop(w12);
        Workshop w13 = new Workshop("Python for ML",
                "Steve Gates",
                "Number of Sections: 28\nNumber of Lectures: 184");
        addWorkshop(w13);
        Workshop w14 = new Workshop("AI for Beginners",
                "Tony Stark",
                "Number of Sections: 45\nNumber of Lectures: 280");
        addWorkshop(w14);
        Workshop w15 = new Workshop("Spring Framework",
                "The Tech Lab",
                "Number of Sections: 18\nNumber of Lectures: 84");
        addWorkshop(w15);
        Workshop w16 = new Workshop("Android Testing BootCamp",
                "Unique Solutions",
                "Number of Sections: 28\nNumber of Lectures: 154");
        addWorkshop(w16);
        Workshop w17 = new Workshop("Graphic Designers for Beginners",
                "Helan Mendivs",
                "Number of Sections: 5\nNumber of Lectures: 90");
        addWorkshop(w17);
        Workshop w18 = new Workshop("PhotoShop BootCamps",
                "David Hausen",
                "Number of Sections: 8\nNumber of Lectures: 94");
        addWorkshop(w18);
        Workshop w19 = new Workshop("Adobe After Effects Basics",
                "John Docker",
                "Number of Sections: 4\nNumber of Lectures: 80");
        addWorkshop(w19);
        Workshop w20 = new Workshop("Java Beginner to Advanced",
                "Willion Fredrik",
                "Number of Sections: 8\nNumber of Lectures: 234");
        addWorkshop(w20);
    }

    private void addWorkshop(Workshop workshop) {
        ContentValues cv = new ContentValues();
        cv.put(WorkshopsTable.COLUMN_COURSE_NAME, workshop.getCourseName());
        cv.put(WorkshopsTable.COLUMN_COURSE_DESCRIPTION, workshop.getDescription());
        cv.put(WorkshopsTable.COLUMN_INSTRUCTOR_NAME, workshop.getInstructorName());

        db.insert(WorkshopsTable.TABLE_NAME, null, cv);
    }

    public List<Workshop> getWorkshopsList() {
        List<Workshop> workshops = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + WorkshopsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Workshop workshop = new Workshop();
                workshop.setId(c.getInt(c.getColumnIndex(WorkshopsTable._ID)));
                workshop.setCourseName(c.getString(c.getColumnIndex(WorkshopsTable.COLUMN_COURSE_NAME)));
                workshop.setDescription(c.getString(c.getColumnIndex(WorkshopsTable.COLUMN_COURSE_DESCRIPTION)));
                workshop.setInstructorName(c.getString(c.getColumnIndex(WorkshopsTable.COLUMN_INSTRUCTOR_NAME)));
                workshops.add(workshop);
            } while (c.moveToNext());
        }
        c.close();
        return workshops;
    }

    public Workshop getWorkshopsListById(int id) {
        Workshop workshop = new Workshop();
        db = getReadableDatabase();
        String query = "SELECT * FROM " + WorkshopsTable.TABLE_NAME +
                " WHERE " + WorkshopsTable._ID + " = " + id;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            workshop.setId(c.getInt(c.getColumnIndex(WorkshopsTable._ID)));
            workshop.setCourseName(c.getString(c.getColumnIndex(WorkshopsTable.COLUMN_COURSE_NAME)));
            workshop.setDescription(c.getString(c.getColumnIndex(WorkshopsTable.COLUMN_COURSE_DESCRIPTION)));
            workshop.setInstructorName(c.getString(c.getColumnIndex(WorkshopsTable.COLUMN_INSTRUCTOR_NAME)));
        }
        c.close();
        return workshop;
    }
}
