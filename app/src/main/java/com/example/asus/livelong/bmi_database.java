package com.example.asus.livelong;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nowrin on 10/25/17.
 */

public class bmi_database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BMI.db";
    public static final String TABLE_NAME = "bmi_table";
    public static final String COL_1 = "ID";

    public static final String COL_2 = "DATE";
    public static final String COL_3 = "HEIGHT";
    public static final String COL_4 = "WEIGHT";
    //public static final String COL_5 = "BMI";

    public bmi_database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,HEIGHT TEXT,WEIGHT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

//    @Override
//    public void onUpgrade(SQLiteDatabase db) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        //onCreate(db);
//    }


    //    @Override
//    public void onUpgrade(SQLiteDatabase db, int older, int i1) {
//
//    }
    public void deleteTable(){
        SQLiteDatabase Temparaturedb = this.getWritableDatabase();

        Temparaturedb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(Temparaturedb);
    }




    //insert data in database.....
    public boolean insertData(String date, String height, String weight) {
        SQLiteDatabase bmidb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, date);
        contentValues.put(COL_3, height);
        contentValues.put(COL_4, weight);
        //contentValues.put(COL_5, bmi);

        long result = bmidb.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
//
//    public boolean updateData(String id,String name,String surname,String marks) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1,id);
//        contentValues.put(COL_2,name);
//        contentValues.put(COL_3,surname);
//        contentValues.put(COL_4,marks);
//        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
//        return true;
//    }
//
//    public Integer deleteData (String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
//    }


}