package com.example.woojinroom.hangto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.woojinroom.hangto.TabActivity.TabActivity_;

public class loginActivity extends AppCompatActivity {
    EditText editText_id;
    EditText editText_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_id = (EditText)findViewById(R.id.editText);
        editText_pass = (EditText)findViewById(R.id.editText2);

        Button button = (Button)findViewById(R.id.button);
        Button button2 = (Button)findViewById(R.id.button2);

        //id 와 pw 를 db에 있는거랑 비교해서 존재하면 통과
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(editText_id.getText().toString().equals("id")) {
                    if(editText_pass.getText().toString().equals("pw")) {
                        Intent login_intent = new Intent(getApplicationContext(), TabActivity_.class);
                        startActivity(login_intent);
                        finish();
                    } else{
                        Toast.makeText(getApplicationContext(),"비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(),"아이디를 확인해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent join_intent = new Intent(getApplicationContext(), nationActivity.class);
                startActivity(join_intent);
                finish();
            }
        });

    }
}
