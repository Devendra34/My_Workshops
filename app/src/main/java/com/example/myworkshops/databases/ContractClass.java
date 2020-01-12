package com.example.myworkshops.databases;

import android.provider.BaseColumns;

public final class ContractClass {

    private ContractClass() {
        //adding restriction to make objects of this class
    }

    public static class WorkshopsTable implements BaseColumns {
        public static final String TABLE_NAME = "workshops_table";
        public static final String COLUMN_COURSE_NAME = "course_name";
        public static final String COLUMN_INSTRUCTOR_NAME = "instructor_name";
        public static final String COLUMN_COURSE_DESCRIPTION = "course_description";

    }

    public static class UsersTable {
        public static final String TABLE_NAME = "users_table";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_EMAID_ID = "user_email_id";
        public static final String COLUMN__USER_PASSWORD = "user_password";

    }

    public static class WorkshopsRegistered implements BaseColumns {
        public static final String TABLE_NAME = "registered_workshops_table";
        public static final String USER_ID = "user_id";
        public static final String WORKSHOP_ID = "workshop_id";
    }
}