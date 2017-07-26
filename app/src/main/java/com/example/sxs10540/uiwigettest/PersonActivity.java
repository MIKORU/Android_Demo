package com.example.sxs10540.uiwigettest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.sxs10540.uiadapter.PersonAdapter;
import com.example.sxs10540.uibean.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonActivity extends AppCompatActivity {

    private RecyclerView personRecyclerView;
    private PersonAdapter adapter;
    private List<Person> personList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        personRecyclerView = (RecyclerView) findViewById(R.id.person_recycle_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        personRecyclerView.setLayoutManager(layoutManager);
        adapter = new PersonAdapter(personList);
        personRecyclerView.setAdapter(adapter);

        if(ContextCompat.checkSelfPermission(PersonActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PersonActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            readContacts();
        }
    }
    private void readContacts(){
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    ));
                    String phone = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    ));
                    personList.add(new Person(name, phone));
                }
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null)
                cursor.close();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else{
                    Toast.makeText(this, "你拒绝了权限申请", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
