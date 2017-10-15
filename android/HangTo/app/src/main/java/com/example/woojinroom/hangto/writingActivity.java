package com.example.woojinroom.hangto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by woojinroom on 2017-09-25.
 */

public class writingActivity extends AppCompatActivity {
    Button button_message,button_request;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        //if문으로 작성자인지 아닌지 판별 후 사이드메뉴 설정과 글보이는게 다르게 설정
        button_message =(Button) findViewById(R.id.button8);
        button_message.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent message_intent = new Intent(getApplicationContext(), messageActivity.class);
                startActivity(message_intent);
            }
        });
        button_request =(Button) findViewById(R.id.button9);
        button_request.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent request_intent = new Intent(getApplicationContext(), requestActivity.class);
                startActivity(request_intent);
            }
        });
    }
}
