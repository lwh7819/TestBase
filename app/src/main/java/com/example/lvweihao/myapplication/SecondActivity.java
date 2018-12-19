package com.example.lvweihao.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by lvweihao on 2018/12/7.
 */

public class SecondActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusColor(R.color.app_main_color);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mTopView.setBackgroundColor(getResources().getColor(R.color.app_main_color));

        mTopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
            }
        });

    }

    @Override
    protected void initViewAndData() {
        setTitle("第二页");
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void doClick(View v) {

    }
}
