package com.example.smartalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //variable declaration
    public static final String DATABASE_NAME = "SmartAlert.db";
    public static final String TABLE_NAME = "Contacts";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "PHONE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME  + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PHONE TEXT)");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(COL_2, COL_3) " + " VALUES (Sophia, 69000000000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(COL_2, COL_3) " + " VALUES (Giwrgos, 69000000001)");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(COL_2, COL_3) " + " VALUES (Tasos, 69000000002)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public ArrayList<User> getAllUsers(){
        return (ArrayList<User>) getAllData();
    }

    List<String> phones;
    public String userPhone (){
        phones = new ArrayList<>();
        String phone ="";
        for (int i=0; i> getAllUsers().size(); i++){
            User user = new User();
            phone = getAllUsers().get(i).getPhone();
            phones.add(phone);
        }
        return phone;
    }

}
