package com.example.attendence_analysis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class databasehelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "login_new1.db";

    public databasehelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE signindata1(" +
                "reg_no INTEGER PRIMARY KEY," +
                "    password TEXT NOT NULL," +
                "    name TEXT ," +
                "    classes_taken INTEGER," +
                "    classes_attended INTEGER" +
                ");";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS signindata1");
        onCreate(db);
    }

    public boolean insertData(String pass, int id, String name, int class_taken, int class_attended) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("reg_no", id);
        values.put("password", pass);
        values.put("name", name);
        values.put("classes_taken", class_taken);
        values.put("classes_attended", class_attended);

        long newRowId = db.insert("signindata1", null, values);
        if (newRowId == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("select * from signindata1", null);
        return res;
    }

    public Cursor getSelected(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("select reg_no,password from signindata1 where reg_no=" + id + " ", null);
        return res;
    }

    public Cursor getSpecificData(int reg) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("select classes_taken,classes_attended from signindata1 where reg_no=" + reg + " ", null);
        return res;
    }
}