package com.example.attendence_analysis;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class databasehelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "login_new.db";

    public databasehelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE signindata (" +
                "id INTEGER PRIMARY KEY," +
                " pass TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS signindata");
        onCreate(db);
    }

    public boolean insertData(String pass, int id) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("pass", pass);

        long newRowId = db.insert("signindata", null, values);
        if (newRowId == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("select * from signindata", null);
        return res;
    }
}