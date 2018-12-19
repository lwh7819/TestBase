package com.example.lvweihao.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lvweihao.myapplication.utils.StatusBarUtils;
import com.example.lvweihao.myapplication.view.LoadingAndRetryManager;
import com.example.lvweihao.myapplication.view.LoadingFrame;
import com.example.lvweihao.myapplication.view.OnLoadingAndRetryListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_hello_world)
    TextView tvHelloWorld;
    @BindView(R.id.m_view)
    View mView;
    @BindView(R.id.m_view_red)
    TextView mViewRed;
    @BindView(R.id.recycler_veiw)
    RecyclerView recyclerVeiw;
    private LoadingAndRetryManager loadingAndRetryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setChangeStatusTrans(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFullScreen();
        StatusBarUtils.setDarkMode(this);

        createLoadingManager(mView, new ReloadFunction() {
            @Override
            public void reload(View retryView) {
                MainActivity.this.setRetryEvent(retryView);
            }
        });

        loadingAndRetryManager.showLoading();

        tvHelloWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
        loadData();

        loadingView.setCurrentState(LoadingFrame.LOADED);
        loadingView.setReloadeFunction(new LoadingFrame.ReloadFunction() {
            @Override
            public void reload() {
                loadData();
            }
        });

//        showLoading(recyclerVeiw);
    }

    public void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(MainActivity.this, "retry event invoked", Toast.LENGTH_SHORT).show();
                loadData();
            }
        });
    }

    private void loadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingView.setCurrentState(LoadingFrame.LOADED);
                loadingAndRetryManager.showContent();
            }
        }, 2000);
    }

    @Override
    protected void initViewAndData() {
        setTitle("首页");
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void doClick(View v) {
        switch (v.getId()) {

        }
    }
}
