package com.example.woojinroom.hangto.Splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.woojinroom.hangto.loginActivity;
import com.example.woojinroom.hangto.R;
import com.example.woojinroom.hangto.TabActivity.TabActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {
    SplashActivity activity;

//    @ViewById
//    Toolbar cs_toolbar;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

//        setSupportActionBar(cs_toolbar);
//        getSupportActionBar().setTitle("스플래쉬");

        // 로그인 성공 후 시작
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity, loginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    void refresh() {

    }

    @UiThread
    void uiThread() {

    }

    void connectTestCall() {

    }
}
