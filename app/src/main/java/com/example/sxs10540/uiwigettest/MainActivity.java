package com.example.sxs10540.uiwigettest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sxs10540.uidatabase.MyDatabaseHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText username;
    private EditText password;
    private CheckBox remeberPass;

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        remeberPass = (CheckBox) findViewById(R.id.remeber);
        Button button2 = (Button) findViewById(R.id.login);
        Button buttongo = (Button) findViewById(R.id.go);

        load();

        dbHelper = new MyDatabaseHelper(this,"User.db",null,1);
        db = dbHelper.getWritableDatabase();

        buttongo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = username.getText().toString();
                String pd = password.getText().toString();
                Cursor cursor = search(uname);
                if(cursor.moveToFirst()){
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String passwords = cursor.getString(cursor.getColumnIndex("password"));
                    if (uname.equals(name) && pd.equals(passwords)) {
                        Sqlsave(uname,pd);
                        save(uname, pd);
                        Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
//                else{
//                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
//                }
                else{
                    ContentValues values = new ContentValues();
                    values.put("name",uname);
                    values.put("password",pd);
                    db.insert("User", null, values);
                }

            }
        });
    }
    public Cursor search(String name){
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE " +
                " User.name = ? "
                ,new String[] { name });
        return cursor;
    }
    public void Sqlsave(String name,String password){
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("password",password);
        Cursor cursor = search(name);
        if(!cursor.isFirst()) {
            db.insert("User", null, values);
        }else{
            db.execSQL("UPDATE User SET password = ? WHERE " +
                    " User.name = ? ",new String[] { password, name });
        }

    }

    public void save(String uname, String pd) {
        SharedPreferences.Editor editor = getSharedPreferences("data_UITest",
                MODE_PRIVATE).edit();
        if (remeberPass.isChecked()) {
            editor.putBoolean("remeber", true);
            editor.putString("username", uname);
            editor.putString("password", pd);
        } else {
            editor.clear();
        }
        editor.apply();
    }


    public void load() {
        SharedPreferences pref = getSharedPreferences("data_UITest", MODE_PRIVATE);
        boolean isRemeber = pref.getBoolean("remeber", false);
        if (isRemeber) {
            String uname = pref.getString("username", "");
            String pd = pref.getString("password", "");

            username.setText(uname);
            password.setText(pd);
            remeberPass.setChecked(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void save(String[] data) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data_UITest", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            for (int i = 0; i < data.length; i++) {
                writer.write(data[i] + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
