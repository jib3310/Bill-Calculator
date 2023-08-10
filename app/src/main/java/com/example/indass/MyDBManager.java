package com.example.indass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBManager {

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_REFERENCES = "reference";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_PEOPLE = "people";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_RECEIPT = "receipt";
    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_table";
    private Context context;
    private SQLiteDatabase database;

    public MyDBManager(Context context) {
        this.context = context;
    }

    public void open() {
        MyDBHelper dbHelper = new MyDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    public long insertData(String references, double amount, int people, String receipt) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_REFERENCES, references);
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_PEOPLE, people);
        values.put(COLUMN_RECEIPT, receipt);
        return database.insert(TABLE_NAME, null, values);
    }

    public Cursor getDataById(long id) {
        String[] projection = {
                COLUMN_ID,
                COLUMN_REFERENCES,
                COLUMN_AMOUNT,
                COLUMN_PEOPLE,
                COLUMN_DATE,
                COLUMN_TIME,
                COLUMN_RECEIPT
        };

        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        return database.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    public Cursor getAllData() {
        String[] columns = {
                COLUMN_ID,
                COLUMN_REFERENCES,
                COLUMN_AMOUNT,
                COLUMN_PEOPLE,
                COLUMN_DATE,
                COLUMN_TIME,
                COLUMN_RECEIPT
        };

        return database.query(
                TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );
    }


    private static class MyDBHelper extends SQLiteOpenHelper {

        MyDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_REFERENCES + " TEXT, " +
                    COLUMN_AMOUNT + " REAL, " +
                    COLUMN_PEOPLE + " INTEGER, " +
                    COLUMN_DATE + " TEXT DEFAULT (strftime('%Y-%m-%d', 'now')), " +
                    COLUMN_TIME + " TEXT DEFAULT (strftime('%H:%M:%S', 'now')), " +
                    COLUMN_RECEIPT + " TEXT)";


            db.execSQL(createTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Handle database schema changes if necessary
        }
    }
}



