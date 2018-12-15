package com.xq.mybreakpointdownload.singlethread.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xq.mybreakpointdownload.bean.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据访问接口实现
 */
public class ThreadDAOImpl implements ThreadDAO {

    private static final String TAG = "ThreadDAOImpl";

    private MyDBHelper myDBHelper;

    public ThreadDAOImpl(Context context) {
        this.myDBHelper = new MyDBHelper(context);
    }

    @Override
    public void insertThread(ThreadInfo threadInfo) {
        Log.e("insertThread: ", "insertThread");
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?)",
                new Object[]{threadInfo.getId(), threadInfo.getUrl(),
                        threadInfo.getStart(), threadInfo.getEnd(), threadInfo.getFinished()});
        db.close();
    }

    @Override
    public void deleteThread(String url, int thread_id) {
        Log.e("deleteThread: ", "deleteThread");
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        db.execSQL("delete from  thread_info where url = ? and thread_id=?",
                new Object[]{url, thread_id});
        db.close();
    }

    @Override
    public void updateThread(String url, int thread_id, long finished) {
        Log.e("updateThread: ", "updateThread");
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        db.execSQL("update thread_info set finished = ?  where url = ? and thread_id=?",
                new Object[]{finished, url, thread_id});
        db.close();
    }

    @Override
    public List<ThreadInfo> getThread(String url) {
        Log.e("getThread: ", "getThread");
        List<ThreadInfo> list = new ArrayList<>();
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url=?", new String[]{url});
        while (cursor.moveToNext()) {
            ThreadInfo thread = new ThreadInfo();
            thread.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            thread.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            thread.setStart(cursor.getLong(cursor.getColumnIndex("start")));
            thread.setEnd(cursor.getLong(cursor.getColumnIndex("end")));
            thread.setFinished(cursor.getLong(cursor.getColumnIndex("finished")));
            list.add(thread);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url=? and thread_id = ?",
                new String[]{url, String.valueOf(thread_id)});
        boolean isExist = cursor.moveToNext();
        cursor.close();
        db.close();
        Log.e(TAG, "isExists: " + isExist);
        return isExist;
    }
}
