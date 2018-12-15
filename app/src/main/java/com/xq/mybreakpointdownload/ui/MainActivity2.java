package com.xq.mybreakpointdownload.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.xq.mybreakpointdownload.R;
import com.xq.mybreakpointdownload.multithread.adapter.FileListAdapter;
import com.xq.mybreakpointdownload.bean.FileInfo;
import com.xq.mybreakpointdownload.multithread.service.DownloadService2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MainActivity2";
    @BindView(R.id.list)
    ListView list;

    private List<FileInfo> fileList;
    private FileListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        initData();
        initSetup();
        initRegister();


    }

    private void initRegister() {
        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService2.ACTION_UPDATE);
        filter.addAction(DownloadService2.ACTION_FINISHED);
        registerReceiver(mReceiver, filter);
    }

    /**
     * 添加数据
     */
    private void initData() {
        fileList = new ArrayList<>();
        FileInfo fileInfo1 = new FileInfo(0, "http://dldir1.qq.com/weixin/android/weixin6316android780.apk",
                "file1", 0, 0);
        FileInfo fileInfo2 = new FileInfo(1, "http://f1.market.xiaomi.com/download/AppStore/09cae652c05a548933fa7c84c7b88f84575d65ee6/com.dch.dai.apk",
                "file2", 0, 0);
        FileInfo fileInfo3 = new FileInfo(2, "https://apka.mumayi.com/2017/10/13/120/1201473/daicaixing_V2.5_mumayi_41823.apk",
                "file3", 0, 0);
        FileInfo fileInfo4 = new FileInfo(3, "http://shouji.360tpcdn.com/170815/3d0520d698950157a7071f9a409c8455/com.dch.dai_14.apk",
                "file4", 0, 0);
        fileList.add(fileInfo1);
        fileList.add(fileInfo2);
        fileList.add(fileInfo3);
        fileList.add(fileInfo4);
    }


    private void initSetup() {
        //创建适配器
        listAdapter = new FileListAdapter(this, fileList);
        //给listview设置适配器
        list.setAdapter(listAdapter);
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService2.ACTION_UPDATE.equals(intent.getAction())) {//注意使用long类型计算百分比
                int finished = (int) intent.getLongExtra("finished", 0);
                int id = intent.getIntExtra("id", 0);
                Log.e(TAG, "finished==" + finished);
                Log.e(TAG, "id==" + id);
                listAdapter.updateProgress(id, finished);
                //progressBar.setProgress(finished);
            } else if (DownloadService2.ACTION_FINISHED.equals(intent.getAction())) {
                FileInfo fileinfo = (FileInfo) intent.getSerializableExtra("fileinfo");
                //更新进度为100
                listAdapter.updateProgress(fileinfo.getId(), 100);
                Toast.makeText(
                        MainActivity2.this,
                        fileinfo.getFileName() + "下载完成",
                        Toast.LENGTH_SHORT).show();
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


}
