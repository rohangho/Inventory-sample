package com.example.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

/**
 * Created by Nicholas on 5/24/2017.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Stock.db";


    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ITEM_TABLE = "CREATE TABLE " + InventoryEntry.Table_Name + "(" +
                InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                InventoryEntry.Column_Item_Name + " TEXT NOT NULL," +
                InventoryEntry.Column_Item_Price + " INTEGER NOT NULL," +
                InventoryEntry.Column_Item_Quantity + " INTEGER," +
                InventoryEntry.Column_Item_Image + " TEXT);";

        db.execSQL(SQL_CREATE_ITEM_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVerison, int newVersion) {

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVerison, int newVersion) {
        onUpgrade(db, oldVerison, newVersion);
    }
}
