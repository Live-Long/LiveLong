package com.example.asus.livelong;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nowrin on 10/25/17.
 */

public class BP_Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BP.db";
    public static final String TABLE_NAME = "BP_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DATE";
    public static final String COL_3 = "TIME";
    public static final String COL_4 = "Systolic";
    public static final String COL_5 = "Diastolic";

    public BP_Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,TIME TEXT,SYSTOLIC INTEGER,DIASTOLIC INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void deleteTable(){
        SQLiteDatabase BPdb = this.getWritableDatabase();

        BPdb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(BPdb);
    }

    public boolean insertData(String date, String time, String systolic,String diastolic) {
        SQLiteDatabase BPdb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, date);
        contentValues.put(COL_3, time);
        contentValues.put(COL_4, systolic);
        contentValues.put(COL_5, diastolic);

        long result = BPdb.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase BPdb = this.getWritableDatabase();
        Cursor res = BPdb.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }


}