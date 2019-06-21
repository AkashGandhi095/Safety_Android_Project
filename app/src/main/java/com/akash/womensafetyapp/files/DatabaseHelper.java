package com.akash.womensafetyapp.files;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "WomenSafety_Data.db";
    private static final int DB_VERSION = 1;
    public static final String TableName = "Contacts";

    public DatabaseHelper(Context context) {
        super(context , DB_NAME , null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CreateTable(SQLiteDatabase db)
    {
        db.execSQL("Create Table " +TableName+ "(NAME TEXT , PHONENO TEXT)");
    }

    public void InsertData(DetailsModel detailsModel)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("NAME" , detailsModel.getName());
        data.put("PHONENO" , detailsModel.getPhoneNo());
        database.insert(TableName , null , data);
        database.close();
    }

    public void DeleteData(String Name , String PhoneNo)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TableName , "NAME" + " = ? AND " + "PHONENO" + " = ?" , new String[] {Name , PhoneNo});
        database.close();
    }

    public ArrayList<DetailsModel> getAllData()
    {
        SQLiteDatabase db  = this.getReadableDatabase();
        ArrayList<DetailsModel> dataList = new ArrayList<>();
        DetailsModel detailsModel;

        try {
            Cursor cursor = db.rawQuery("Select * from " +TableName , null);

            while(cursor.moveToNext())
            {
                detailsModel = new DetailsModel(cursor.getString(0) , cursor.getString(1));
                dataList.add(detailsModel);
            }
            cursor.close();
            db.close();
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }

        return dataList;
    }
}
