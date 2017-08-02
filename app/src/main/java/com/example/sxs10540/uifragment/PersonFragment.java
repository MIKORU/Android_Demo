package com.example.sxs10540.uifragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sxs10540.uiadapter.PersonAdapter;
import com.example.sxs10540.uibean.Person;
import com.example.sxs10540.uiwigettest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxs10540 on 2017/8/2.
 */

public class PersonFragment extends Fragment {

    private RecyclerView personRecyclerView;
    private PersonAdapter adapter;
    private List<Person> personList = new ArrayList<>();
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_person,container,false);
        context = getContext();

        personRecyclerView = (RecyclerView) view.findViewById(R.id.person_recycle_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        personRecyclerView.setLayoutManager(layoutManager);
        adapter = new PersonAdapter(personList);
        personRecyclerView.setAdapter(adapter);

        if(ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            readContacts();
        }

        return view;
    }
    private void readContacts(){
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
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
                    Toast.makeText(context, "你拒绝了权限申请", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
