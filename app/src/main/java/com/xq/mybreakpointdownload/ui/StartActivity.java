package com.xq.mybreakpointdownload.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xq.mybreakpointdownload.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.single)
    TextView single;
    @BindView(R.id.multith)
    TextView multith;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.single, R.id.multith})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.single:
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                break;
            case R.id.multith:
                startActivity(new Intent(StartActivity.this, MainActivity2.class));
                break;
        }
    }
}
