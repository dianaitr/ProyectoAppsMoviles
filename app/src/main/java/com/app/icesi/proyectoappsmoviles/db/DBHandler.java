package com.app.icesi.proyectoappsmoviles.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static DBHandler instance = null;

    public static final String DB_NAME = "HappyHome";
    public static final int DB_VERSION = 1;

    // TABLA

    private DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DBHandler(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
