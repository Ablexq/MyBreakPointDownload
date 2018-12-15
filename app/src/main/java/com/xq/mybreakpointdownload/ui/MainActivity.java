package com.xq.mybreakpointdownload.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xq.mybreakpointdownload.R;
import com.xq.mybreakpointdownload.bean.FileInfo;
import com.xq.mybreakpointdownload.singlethread.service.DownloadService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    String url;
    FileInfo fileInfo;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.pro_text)
    TextView proText;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.pause)
    Button pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();

        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver, filter);
    }

    private void init() {
        proText.setVisibility(View.VISIBLE);
        progressBar.setMax(100);
        //创建文件信息对象
        url = "http://dldir1.qq.com/weixin/android/weixin6316android780.apk";
        fileInfo = new FileInfo(0, url, "file1", 0, 0);
        name.setText(fileInfo.getFileName());
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {//注意使用long类型计算百分比
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                int finished = (int) intent.getLongExtra("finished", 0);
//                System.out.println("mReceiver finished========================================" + finished);
                progressBar.setProgress(finished);
                proText.setText(new StringBuffer().append(finished).append("%"));
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }

    @OnClick({R.id.start, R.id.pause})
    public void onViewClicked(View view) {
        Intent intent = new Intent(MainActivity.this, DownloadService.class);
        switch (view.getId()) {
            case R.id.start:
                intent.setAction(DownloadService.ACTION_START);
                intent.putExtra("fileinfo", fileInfo);
                startService(intent);
                break;
            case R.id.pause:
                intent.setAction(DownloadService.ACTION_PAUSE);
                intent.putExtra("fileinfo", fileInfo);
                startService(intent);
                break;
        }
    }
}
