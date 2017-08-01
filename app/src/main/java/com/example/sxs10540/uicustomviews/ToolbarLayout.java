package com.example.sxs10540.uicustomviews;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.sxs10540.uiwigettest.DownloadActivity;
import com.example.sxs10540.uiwigettest.PersonActivity;
import com.example.sxs10540.uiwigettest.R;
import com.example.sxs10540.uiwigettest.WebActivity;


/**
 * Created by sxs10540 on 2017/7/31.
 */

public class ToolbarLayout extends LinearLayout {


    public ToolbarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.activity_ui,this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.download:
                        Intent intent = new Intent(getContext(),DownloadActivity.class);
                        getContext().startActivity(intent);
                        break;
                    case R.id.contact:
                        Intent intent1 = new Intent(getContext(), PersonActivity.class);
                        getContext().startActivity(intent1);
                        break;
                    case R.id.web:
                        Intent intent2 = new Intent(getContext(), WebActivity.class);
                        getContext().startActivity(intent2);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

}
