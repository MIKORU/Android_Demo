package com.example.sxs10540.uidatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by sxs10540 on 2017/7/26.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_USER = "create table user(" +
            " id integer primary key autoincrement, " +
            " name text uniqueadb, " +
            " password text)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        //创建管理员账号
        db.execSQL("Insert into User (name,password) values(?,?)",new String[]{
                "mikoru","27185306"
            });
        Toast.makeText(mContext, "初始化数据成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
