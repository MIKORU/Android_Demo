package com.example.sxs10540.uifragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sxs10540.uidatabase.MyDatabaseHelper;
import com.example.sxs10540.uiwigettest.MainActivity;
import com.example.sxs10540.uiwigettest.R;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by sxs10540 on 2017/8/1.
 */

public class MainFragment extends Fragment {

    private EditText username;
    private EditText password;
    private CheckBox remeberPass;

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private DrawerLayout drawerLayout;
    private MainActivity mainActivity = (MainActivity) getActivity();
    private ChatFragment chatFragment = new ChatFragment();

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainlayout, container, false);
        context = getContext();

        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        remeberPass = (CheckBox) view.findViewById(R.id.remeber);
        Button button = (Button) view.findViewById(R.id.login);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer);

        load();

        dbHelper = new MyDatabaseHelper(context, "User.db", null, 1);
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
                        Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show();
                        mainActivity.replacesFragment(chatFragment);

                    } else {
                        Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "拒绝权限无法使用", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
                break;
        }
    }

    public void load() {
        SharedPreferences pref = context.getSharedPreferences("data_UITest", context.MODE_PRIVATE);
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
        SharedPreferences.Editor editor = context.getSharedPreferences("data_UITest",
                context.MODE_PRIVATE).edit();
        if (remeberPass.isChecked()) {
            editor.putBoolean("remeber", true);
            editor.putString("username", uname);
            editor.putString("password", pd);
        } else {
            editor.clear();
        }
        editor.apply();
    }


    public void save(String[] data) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput("data_UITest", Context.MODE_PRIVATE);
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
