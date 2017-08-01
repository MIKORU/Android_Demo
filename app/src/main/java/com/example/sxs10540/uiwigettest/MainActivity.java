package com.example.sxs10540.uiwigettest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sxs10540.uidatabase.MyDatabaseHelper;

import java.io.BufferedWriter;
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

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        remeberPass = (CheckBox) findViewById(R.id.remeber);
        Button button = (Button) findViewById(R.id.login);

        load();

        dbHelper = new MyDatabaseHelper(this, "User.db", null, 1);
        db = dbHelper.getWritableDatabase();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = username.getText().toString();
                String pd = password.getText().toString();
                Cursor cursor = search(uname);
                if (cursor.moveToFirst()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String passwords = cursor.getString(cursor.getColumnIndex("password"));
                    if (uname.equals(name) && pd.equals(passwords)) {
                        Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "拒绝权限无法使用", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
                break;
        }
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

    public Cursor search(String name) {
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE " +
                        " User.name = ? "
                , new String[]{name});
        return cursor;
    }

    public void Sqlsave(String name, String password) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("password", password);
        Cursor cursor = search(name);
        if (!cursor.isFirst()) {
            db.insert("User", null, values);
        } else {
            db.execSQL("UPDATE User SET password = ? WHERE " +
                    " User.name = ? ", new String[]{password, name});
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
