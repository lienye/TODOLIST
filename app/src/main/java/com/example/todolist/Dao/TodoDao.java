package com.example.todolist.Dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.todolist.Bean.Todos;
import com.example.todolist.DBHelper.MyDatabaseHelper;

/**
 * 待办事项本地数据库操作
 */
public class TodoDao {
    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase database;

    public TodoDao(Context context){
        myDatabaseHelper = new MyDatabaseHelper(context.getApplicationContext(),"Data.db",null,1);
    }

    public void open() throws SQLException{
        database = myDatabaseHelper.getWritableDatabase();
    }

    public void close(){
       database.close();
    }

    /**
     * 根据传入的Todo Bean，插入数据，并返回记录ID
     */
    public long create(Todos todos){
        this.open();
        ContentValues values = new ContentValues();
        values.put("todotitle",todos.getTitle());
        values.put("tododes",todos.getDesc());
        values.put("tododate",todos.getDate());
        values.put("todotime",todos.getTime());
        values.put("remindTime",todos.getRemindTime());
        values.put("isAlerted",todos.getIsAlerted());
        values.put("isRepeat",todos.getIsRepeat());
        values.put("imageId",todos.getImgId());
        long tid = database.insert("Todo",null,values);
        close();
        return tid;
    }


}
