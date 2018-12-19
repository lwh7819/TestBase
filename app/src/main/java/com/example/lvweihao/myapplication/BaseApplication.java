package com.example.lvweihao.myapplication;

import android.app.Application;
import android.content.Context;

import com.example.lvweihao.myapplication.view.LoadingAndRetryManager;

/**
 * Created by lvweihao on 2018/12/7.
 */

public class BaseApplication extends Application {
    public static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();

        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;
    }
}
