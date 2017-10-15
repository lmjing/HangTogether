package com.example.woojinroom.hangto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.woojinroom.hangto.Model.joinUser;
import com.example.woojinroom.hangto.TabActivity.TabActivity_;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by woojinroom on 2017-09-24.
 */

public class joinActivity extends AppCompatActivity {
    Retrofit retrofit;
    IDCheck idCheck;
    boolean email_check=false,nick_check=false;
    String user_email,user_nickname,user_password,user_sex,user_birth,user_profileUrl,user_type,user_languages,user_introduce;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        retrofit = new Retrofit.Builder().baseUrl(IDCheck.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        idCheck = retrofit.create(IDCheck.class);

        final Button button_male = (Button)findViewById(R.id.button_male);
        final Button button_female = (Button)findViewById(R.id.button_female);
        final EditText editText_email =(EditText)findViewById(R.id.editText_email);
        final Button button_email =(Button)findViewById(R.id.button_email);
        button_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_email = editText_email.getText().toString();
                Call<ResponseBody> email = idCheck.getEmail(user_email);
                email.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(!editText_email.getText().toString().equals("")) {
                            try {
                                if (response.code() == 200) {
                                    Toast.makeText(getApplicationContext(), response.body().string(), Toast.LENGTH_SHORT).show();
                                    email_check = true;
                                } else if (response.code() == 409) {
                                    Toast.makeText(getApplicationContext(), "이미 존재하는 이메일입니다", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        final EditText editText_nickname =(EditText)findViewById(R.id.editText_nickname);
        Button button_nickname =(Button)findViewById(R.id.button_nickname);
        button_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_nickname = editText_nickname.getText().toString();
                Call<ResponseBody> nickname = idCheck.getNickname(user_nickname);
                nickname.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (!editText_nickname.getText().toString().equals("")) {
                            try {
                                if (response.code() == 200) {
                                    Toast.makeText(getApplicationContext(), response.body().string(), Toast.LENGTH_SHORT).show();
                                    nick_check = true;
                                } else if (response.code() == 409) {
                                    Toast.makeText(getApplicationContext(), "이미 존재하는 닉네임입니다", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        ImageButton button_left =(ImageButton)findViewById(R.id.imagebutton_left);
        button_left.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //if 문으로 회원가입 완료되는지 확인 후 메인페이지로 넘어감
                finish();
            }
        });
        final EditText editText_password =(EditText)findViewById(R.id.editText_password);
        final EditText editText_password2 =(EditText)findViewById(R.id.editText_password2);
        final EditText editText_year =(EditText)findViewById(R.id.editText_year);
        final EditText editText_month =(EditText)findViewById(R.id.editText_month);
        final EditText editText_day =(EditText)findViewById(R.id.editText_day);
        ImageButton button_right =(ImageButton)findViewById(R.id.imagebutton_right);

        button_right.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (email_check) {
                    if (nick_check) {
                        if(editText_password.getText()!=null && editText_password2.getText()!=null && editText_password.getText().toString().equals(editText_password2.getText().toString())) {
                            user_password = editText_password.getText().toString();
                            user_profileUrl = "/resource/cowooding.png";
                            user_birth = editText_year.getText().toString() + "-" + editText_month.getText().toString() + "-" + editText_day.getText().toString();
                            user_type = "korean"; //nationActivity 에서 받은 정보로 korean or foreigner 또는 어플 번역되었는지 여부로!
                            user_languages = "Korean";
                            user_introduce = "hello!";

                            Call<joinUser> joinUser = idCheck.getUserData(user_email, user_password, user_nickname, user_sex, user_birth, user_profileUrl, user_type, user_languages, user_introduce);
                            joinUser.enqueue(new Callback<joinUser>() {
                                @Override
                                public void onResponse(Call<joinUser> call, Response<joinUser> response) {

                                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call<joinUser> call, Throwable t) {

                                }
                            });
                            //if 문으로 회원가입 완료되는지 확인 후 메인페이지로 넘어감
                            Intent login_intent = new Intent(getApplicationContext(), TabActivity_.class);
                            startActivity(login_intent);
                            finish();
                            }else{

                            Toast.makeText(getApplicationContext(),"비밀번호를 확인 해주세요.",Toast.LENGTH_SHORT).show();
                        }
                        } else{
                        Toast.makeText(getApplicationContext(),"닉네임 중복확인을 해주세요.",Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(),"이메일 중복확인을 해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });


        button_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_sex = "male";
                button_male.setBackgroundColor(Color.rgb(53,186,188));
                button_male.setTextColor(Color.rgb(255,255,255));
                button_female.setBackgroundColor(Color.rgb(225,225,225));
                button_female.setTextColor(Color.rgb(170,170,170));
            }
        });

        button_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_sex = "female";
                button_male.setBackgroundColor(Color.rgb(225,225,225));
                button_male.setTextColor(Color.rgb(170,170,170));
                button_female.setBackgroundColor(Color.rgb(53,186,188));
                button_female.setTextColor(Color.rgb(255,255,255));
            }
        });

    }
}
