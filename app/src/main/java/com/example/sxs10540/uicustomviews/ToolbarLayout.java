package com.example.sxs10540.uicustomviews;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.sxs10540.uiwigettest.R;


/**
 * Created by sxs10540 on 2017/7/31.
 */

public class ToolbarLayout extends DrawerLayout {

    private Context contexts;

    public ToolbarLayout(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);

        this.contexts = context;

        LayoutInflater.from(context).inflate(R.layout.activity_ui, this);

        ImageView imageViews = (ImageView) findViewById(R.id.returns);

        imageViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                调用系统返回键
                ((Activity) contexts).onBackPressed();
            }
        });

    }

}
