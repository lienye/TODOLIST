package com.example.todolist.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * 数据库帮助类
 * 本地SQLite
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    //SQL语句，不可外部修改
    public static final String TODO = "create table Todos(" +
            "tid INTEGER primary key autoincrement," +
            "todotitle TEXT," +
            "tododsc TEXT," +
            "tododate TEXT," +
            "todotime TEXT," +
            "objectId TEXT," +
            "remindTime LONG," +
            "remindTimeNoDay LONG," +
            "isAlerted INT," +
            "imgID INT," +
            "isRepeat INT)";

    public static final String CLOCK = "create table Clock(" +
            "cid INTEGER primary key autoincrement," +
            "objectId TEXT," +
            "clocktitle TEXT," +
            "workLength INT," +
            "shortBreak INT," +
            "longBreak INT," +
            "frequency INT," +
            "imgId INT)";

    public static final String TIME = "create table timer_schedule(" +
            "_id INTEGER primary key autoincrement," +
            "clocktitle TEXT," +
            "start_time DATETIME," +
            "end_time DATETIME," +
            "duration INTEGER," +
            "date_add DATE)";

    public static final String USER = "create table _User(" +
            "uid INTEGER primary key autoincrement," +
            "name TEXT," +
            "password TEXT," +
            "img TEXT)";

    private Context myContext;

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myContext = context;
    }

    /**
     * 数据库创建
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TODO);
        db.execSQL(CLOCK);
        db.execSQL(TIME);
        db.execSQL(USER);
    }

    /**
     * 数据库更新
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Todos");
        db.execSQL("drop table if exists Clock");
        db.execSQL("drop table if exists timer_schedule");
        db.execSQL("drop table if exists _User");
        onCreate(db);
    }

}
