package com.example.carlos.tabproject1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Carlos on 5/7/2018.
 */

public class TaskDBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todoList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Todo List";
    private static final String TODO_NAME = "task";
    private static final String TODO_NOTE = "note";
    private static final int TODO_ID = 0;
    private static final boolean TODO_EDITABLE = false;

    public TaskDBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s(%d INTEGER PRIMARY KEY AUTOINCREMENT," +
                " %s TEXT NOT NULL,%s NUMBER NOT NULL," +
                "%s BOOLEAN NOT NULL);",
                TABLE_NAME, TODO_ID, TODO_NAME, TODO_NOTE,
                TODO_EDITABLE));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);


    }
}
