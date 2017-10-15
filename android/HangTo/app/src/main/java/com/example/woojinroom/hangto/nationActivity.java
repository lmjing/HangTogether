package com.example.woojinroom.hangto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by woojinroom on 2017-10-09.
 */

public class nationActivity extends AppCompatActivity {
    TextView textView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nation);

        textView = (TextView)findViewById(R.id.textView18);
        textView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //if 문으로 회원가입 완료되는지 확인 후 메인페이지로 넘어감
                Intent join_intent = new Intent(getApplicationContext(), joinActivity.class);
                startActivity(join_intent);
                finish();
            }
        });
    }
}