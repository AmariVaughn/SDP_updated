package com.example.cash.sdp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 

class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "WeeklyData.db";
    public static final String TABLE_NAME = "events";// Table for each category
    public static final String TABLE_NAME2 ="account";
    public static final String TABLE_NAME3 ="saved";
    public static final String TABLE_NAME4 ="added";
    public static final String COL_1 = "ID";//index of row
    public static final String COL_2 = "Building";//the recoreded lvl of stress that day
    public static final String COL_3 = "Event";//stress lvl number calculated for category
    public static final String COL_4 = "Time";//was progress made y/n,
    public static final String COL_5 = "Date";
    public static final String COL_6 = "Participants";
    public static final String COL_7 = "Description";

    public static final String user="Email";
    public static final String banner="banner";

 
    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,Building TEXT,Event TEXT,Time TEXT,Date TEXT,Participants TEXT,Description TEXT)");
        db.execSQL("create table " + TABLE_NAME2 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,Email TEXT,banner TEXT)");
        db.execSQL("create table " + TABLE_NAME3 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,Building TEXT,Event TEXT,Time TEXT,Date TEXT,Participants TEXT,Description TEXT)");
        db.execSQL("create table " + TABLE_NAME4 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,Building TEXT,Event TEXT,Time TEXT,Date TEXT,Participants TEXT,Description TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ");
        onCreate(sqLiteDatabase);

    }


 
    public boolean insertData(String building,String event,String Time,String date,String participants,String description, String user,String pass,String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if(true) {

            contentValues.put(COL_2, building);
            contentValues.put(COL_3, event);
            contentValues.put(COL_4, Time);
            contentValues.put(COL_5, date);
            contentValues.put(COL_6, participants);
            contentValues.put(COL_7, description);
        }else{
            contentValues.put(COL_2, user);
            contentValues.put(COL_3, pass);
        }
        long result = db.insert(table,null ,contentValues);
        if(result == -1) {
            return false;
        }else {
            return true;
        }

    }
 
    public Cursor getAllData(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+table,null);
        return res;
    }
 
    public boolean updateData(String id,String building,String event,String Time,String date,String participants, String description, String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, building);
        contentValues.put(COL_3, event);
        contentValues.put(COL_4, Time);
        contentValues.put(COL_5, date);
        contentValues.put(COL_6, participants);
        contentValues.put(COL_7, description);
        db.update(table, contentValues, "ID = ?",new String[] { id });
        return true;
    }
 
    public Integer deleteData (String id,String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table, "ID = ?",new String[] {id});
    }
}