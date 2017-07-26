package com.example.sxs10540.uiwigettest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sxs10540.uiadapter.MsgAdapter;
import com.example.sxs10540.uibean.Msg;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

public class SecondActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private Button phone;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        initMsgs();

        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        phone = (Button) findViewById(R.id.phone);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    Msg msg = new Msg(content,Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    inputText.setText("");
                }
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(SecondActivity.this,
                        android.Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(SecondActivity.this,
                            new String[]{android.Manifest.permission.CALL_PHONE},1);
                }else{
                    call();
                }

            }
        });

    }
    private void call(){
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:2333"));
            startActivity(intent);
        } catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED){
                    call();
                }else{
                    Toast.makeText(this, "你拒绝了权限申请", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void initMsgs(){
        Msg msg1 = new Msg("您好",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("您好，您是谁？",Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("你猜？",Msg.TYPE_RECEIVED);
        msgList.add(msg3);

    }
}
