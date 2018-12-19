package com.example.lvweihao.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lvweihao.myapplication.R;

public abstract class LoadingFrame extends FrameLayout {

    private Context mContext;

    public static final int LOADING = 1;
    public static final int LOADERROR = 2;
    public static final int NETERROR = 3;
    public static final int LOADED = 4;
    public static final int NODATA = 5;

    private ImageView loadingView;
    private LinearLayout mlinearLayoutLoading;
    private ImageView noDataView;
    private LinearLayout mlinearLayoutNoData;
    private LinearLayout mlinearLayoutLoadError;
    private ImageView netErrorView;
    private LinearLayout mlinearLayoutNetError;
    private View successView;
    private ReloadFunction reloadFunction;

    private int currentState = LOADING;
    private LayoutParams params;

    public LoadingFrame(Context context) {
        super(context);
        this.mContext = context;
        createView();
    }

    public LoadingFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        createView();
    }

    public LoadingFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
        refreshView();
    }

    private void refreshView() {
        mlinearLayoutLoading.setVisibility(currentState == LOADING ? View.VISIBLE : View.GONE);
        mlinearLayoutNoData.setVisibility(currentState == NODATA ? View.VISIBLE : View.GONE);
        mlinearLayoutNetError.setVisibility(currentState == NETERROR ? View.VISIBLE : View.GONE);
        mlinearLayoutLoadError.setVisibility(currentState == LOADERROR ? View.VISIBLE : View.GONE);
        if (successView != null) {
            successView.setVisibility(currentState == LOADED ? View.VISIBLE : View.GONE);
        }
    }

    private void createView() {
        this.setBackgroundColor(mContext.getResources().getColor(R.color.main_bg));
        params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        successView = onSuccessView();

        createLoadingView();

        createNoDataView();

        createNetErrorView();

        createLoadedErrorView();

        addView(mlinearLayoutLoading, params);
        addView(mlinearLayoutNoData, params);
        addView(mlinearLayoutNetError, params);
        addView(mlinearLayoutLoadError, params);
        addView(successView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        refreshView();
    }

    private void createNetErrorView() {
        mlinearLayoutNetError = new LinearLayout(mContext);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.CENTER;
        mlinearLayoutNetError.setOrientation(LinearLayout.VERTICAL);

        netErrorView = new ImageView(mContext);
        netErrorView.setImageResource(R.drawable.ic_net_err);

        mlinearLayoutNetError.addView(netErrorView, linearLayoutParams);
        TextView textView = new TextView(mContext);
        textView.setText("网络错误，检查您的网络或点击重试");
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentState = LOADING;
                reload();
                refreshView();
            }
        });

        mlinearLayoutNetError.addView(textView, linearLayoutParams);

        mlinearLayoutNetError.setVisibility(View.GONE);
    }

    private void createNoDataView() {
        mlinearLayoutNoData = new LinearLayout(mContext);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.CENTER;
        mlinearLayoutNoData.setOrientation(LinearLayout.VERTICAL);

        noDataView = new ImageView(mContext);
        noDataView.setImageResource(R.drawable.ic_empty);
        mlinearLayoutNoData.addView(noDataView, linearLayoutParams);
        TextView textView = new TextView(mContext);
        textView.setText("没有数据可供显示！");
        mlinearLayoutNoData.addView(textView, linearLayoutParams);
        mlinearLayoutNoData.setVisibility(View.GONE);
    }

    private void createLoadedErrorView() {
        mlinearLayoutLoadError = new LinearLayout(mContext);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.CENTER;
        mlinearLayoutLoadError.setOrientation(LinearLayout.VERTICAL);

        noDataView = new ImageView(mContext);
        noDataView.setImageResource(R.drawable.ic_empty);
        mlinearLayoutLoadError.addView(noDataView, linearLayoutParams);
        TextView textView = new TextView(mContext);
        textView.setText("加载失败！点击重试");
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentState = LOADING;
                reload();
                refreshView();
            }
        });
        mlinearLayoutLoadError.addView(textView, linearLayoutParams);
        mlinearLayoutLoadError.setVisibility(View.GONE);
    }

    private void createLoadingView() {
        mlinearLayoutLoading = new LinearLayout(mContext);
        int width = mContext.getResources().getDimensionPixelOffset(R.dimen.loading_width);
        int height = mContext.getResources().getDimensionPixelOffset(R.dimen.loading_height);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(width, height);
        linearLayoutParams.gravity = Gravity.CENTER;
        mlinearLayoutLoading.setOrientation(LinearLayout.VERTICAL);

        loadingView = new ImageView(mContext);
        Glide.with(mContext)
                .load(R.drawable.gif_data_loading)
                .asGif()
                .into(loadingView);
        mlinearLayoutLoading.addView(loadingView, linearLayoutParams);
        TextView textView = new TextView(mContext);
        textView.setText("正在加载中");
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER;
        mlinearLayoutLoading.addView(textView, textParams);
        mlinearLayoutLoading.setVisibility(View.GONE);
    }

    private void reload() {
        if (reloadFunction != null) {
            reloadFunction.reload();
        }
    }

    public void setReloadeFunction(ReloadFunction reloadeFunction) {
        this.reloadFunction = reloadeFunction;
    }

    public abstract View onSuccessView();

    public interface ReloadFunction {
        void reload();
    };
}