package com.example.sxs10540.uicustomviews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.sxs10540.uiwigettest.MainActivity;
import com.example.sxs10540.uiwigettest.R;


/**
 * Created by sxs10540 on 2017/7/31.
 */

public class ToolbarLayout extends DrawerLayout {

    private Intent intent;
    private DrawerLayout drawerLayout;


    public ToolbarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.activity_ui, this);

        ImageView returns = (ImageView) findViewById(R.id.returns);

        returns.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(intent);
                ((Activity) getContext()).finish();
            }
        });

    }

}
