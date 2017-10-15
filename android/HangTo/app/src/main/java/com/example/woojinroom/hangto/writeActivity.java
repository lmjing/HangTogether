package com.example.woojinroom.hangto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by woojinroom on 2017-09-25.
 */

public class writeActivity extends AppCompatActivity {
    EditText editText14,editText15,editText16;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        editText14 =(EditText)findViewById(R.id.editText14);
        editText14.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(getApplicationContext(),"시작 눌림",Toast.LENGTH_SHORT).show();
            }
        });
        editText15 =(EditText)findViewById(R.id.editText15);
        editText15.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(getApplicationContext(),"종료 눌림",Toast.LENGTH_SHORT).show();
            }
        });
        editText16 =(EditText)findViewById(R.id.editText16);
        editText16.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent spot_intent = new Intent(getApplicationContext(), spotActivity.class);
                startActivity(spot_intent);
            }
        });
    }
}
