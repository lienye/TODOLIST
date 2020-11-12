package com.example.todolist.Dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.todolist.Bean.Todos;
import com.example.todolist.DBHelper.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

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

    /**
     * 获取并返回所有未被提醒的事项
     * @return
     */
    public List<Todos> getNotAlertTodos(){
        open();
        List<Todos> allTodos = new ArrayList<Todos>();
        Cursor cursor = database.query("Todo",
                null, "isAlerted = ?", new String[] { "0" }, null, null, "remindTime");
        while (cursor.moveToNext()) {
            Todos data = new Todos();
            data.setId(cursor.getInt(cursor.getColumnIndex("id")));
            data.setTitle(cursor.getString(cursor.getColumnIndex("todotitle")));
            data.setDesc(cursor.getString(cursor.getColumnIndex("tododsc")));
            data.setDate(cursor.getString(cursor.getColumnIndex("tododate")));
            data.setTime(cursor.getString(cursor.getColumnIndex("todotime")));
            data.setRemindTime(cursor.getLong(cursor.getColumnIndex("remindTime")));
            data.setRemindTimeNoDay(cursor.getLong(cursor.getColumnIndex("remindTimeNoDay")));
            data.setIsAlerted(cursor.getInt(cursor.getColumnIndex("isAlerted")));
            data.setIsRepeat(cursor.getInt(cursor.getColumnIndex("isRepeat")));
            data.setImgId(cursor.getInt(cursor.getColumnIndex("imgId")));
            allTodos.add(data);
        }

        cursor.close();
        close();
        return allTodos;
    }

    /**
     * 获取所有task
     *
     * @return
     */
    public List<Todos> getAllTask() {
        open();
        List<Todos> todosList = new ArrayList<Todos>();
        Cursor cursor=database.rawQuery("SELECT * FROM Todo", null);
        while(cursor.moveToNext()) {
            Todos data = new Todos();
            data.setId(cursor.getInt(cursor.getColumnIndex("id")));
            data.setTitle(cursor.getString(cursor.getColumnIndex("todotitle")));
            data.setDesc(cursor.getString(cursor.getColumnIndex("tododsc")));
            data.setDate(cursor.getString(cursor.getColumnIndex("tododate")));
            data.setTime(cursor.getString(cursor.getColumnIndex("todotime")));
            data.setRemindTime(cursor.getLong(cursor.getColumnIndex("remindTime")));
            data.setRemindTimeNoDay(cursor.getLong(cursor.getColumnIndex("remindTimeNoDay")));
            data.setIsAlerted(cursor.getInt(cursor.getColumnIndex("isAlerted")));
            data.setIsRepeat(cursor.getInt(cursor.getColumnIndex("isRepeat")));
            data.setImgId(cursor.getInt(cursor.getColumnIndex("imgId")));
            data.setObjectId(cursor.getString(cursor.getColumnIndex("objectId")));
            todosList.add(data);
        }
        // make sure to close the cursor

        cursor.close();
        close();
        Log.i("ToDoDao", "查询到本地的任务个数：" + todosList.size());
        return todosList;
    }

    /**
     * 获取单个待办事项
     * @param id
     * @return
     */
    public Todos getTask(int id) {
        open();
        Todos data = new Todos();
        Cursor cursor = database.rawQuery("SELECT * FROM Todo WHERE id =" + id, null);
        if (cursor.moveToNext()) {
            data.setId(cursor.getInt(cursor.getColumnIndex("id")));
            data.setTitle(cursor.getString(cursor.getColumnIndex("todotitle")));
            data.setDesc(cursor.getString(cursor.getColumnIndex("tododsc")));
            data.setDate(cursor.getString(cursor.getColumnIndex("tododate")));
            data.setTime(cursor.getString(cursor.getColumnIndex("todotime")));
            data.setRemindTime(cursor.getLong(cursor.getColumnIndex("remindTime")));
            data.setRemindTimeNoDay(cursor.getLong(cursor.getColumnIndex("remindTimeNoDay")));
            data.setIsAlerted(cursor.getInt(cursor.getColumnIndex("isAlerted")));
            data.setIsRepeat(cursor.getInt(cursor.getColumnIndex("isRepeat")));
            data.setImgId(cursor.getInt(cursor.getColumnIndex("imgId")));
            data.setObjectId(cursor.getString(cursor.getColumnIndex("objectId")));
        }
        cursor.close();
        close();
        return data;
    }

    /**
     * 设置待办事项为已提醒
     * @param id
     */
    public void setIsAlerted(int id){
        open();
        Log.i(TAG, "数据已更新");
        ContentValues values = new ContentValues();
        values.put("isAlerted", 1);
        Log.i(TAG, String.valueOf(id));
        database.update("Todo", values, "id = ?", new String[]{id + ""});
        close();
    }
}
