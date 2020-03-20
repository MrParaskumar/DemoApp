package com.example.demoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehelper extends SQLiteOpenHelper {

    public static final String table = "Profile";
    public static final String database = "userdatabase";
    public static final String Id = "id";
    public static final String col1 = "Name";
    public static final String col2 = "Phone_Number";
    public static final String col3 = "Email";
    public static final String col4 = "Password";

    public Databasehelper(Context context) {
        super(context, database, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT,Name,Phone_Number,Email,password)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + table);
        onCreate(db);
    }

    public boolean insertdata(String name, String phonenumber, String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, name);
        contentValues.put(col2, phonenumber);
        contentValues.put(col3, email);
        contentValues.put(col4, password);
        long result = sqLiteDatabase.insert(table, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getalldata() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + table, null);
//        if (cursor != null){
//            cursor.moveToFirst();
//        }
        return cursor;
    }

    public boolean updatedata(String newid,String newname, String newphonenumber, String newemail) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, newname);
        contentValues.put(col2, newphonenumber);
        contentValues.put(col3, newemail);
        sqLiteDatabase.update(table, contentValues, "id = ?", new String[]{newid});
        return true;
    }

    public Integer deletedata(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(table, "id = ?", new String[]{id});
    }
}
