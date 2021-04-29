package com.example.newreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newreader.domain.Url;
import com.example.newreader.domain.User;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button bt_back = findViewById(R.id.btn_back);
        Button bt_register = findViewById(R.id.btn_register);
        final EditText re_username = findViewById(R.id.re_user_name);
        final EditText re_password = findViewById(R.id.re_psw);
        final EditText su_re_password = findViewById(R.id.su_re_pwd);
        Url url_pre = new Url();
        final String url =  url_pre.getUrl()+"/register";
//        final String url = "http://192.168.2.192:8090/register";
        final CheckBox checkBox1 = findViewById(R.id.re_show_hide);
        final CheckBox checkBox2 = findViewById(R.id.su_re_show_hide);
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox1.isChecked()){
                    checkBox1.setButtonDrawable(R.mipmap.password_show);
                    re_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    checkBox1.setButtonDrawable(R.mipmap.password_hide);
                    re_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
       checkBox2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(checkBox2.isChecked()){
                   checkBox2.setButtonDrawable(R.mipmap.password_show);
                   su_re_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
               }
               else{
                   checkBox2.setButtonDrawable(R.mipmap.password_hide);
                   su_re_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
               }
           }
       });
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                User user = new User();
                user.setUsername(re_username.getText().toString());
                user.setPassword(re_password.getText().toString());
                if(re_password.getText().toString().isEmpty()||re_username.getText().toString().isEmpty()
                        ||su_re_password.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this,"输入不能为空，请检查输入",Toast.LENGTH_LONG).show();
                }
                else if(!re_password.getText().toString().equals(su_re_password.getText().toString())){
                    re_password.setText("");
                    re_username.setText("");
                    su_re_password.setText("");
                    Toast.makeText(RegisterActivity.this,"两次密码不一样，请检查输入",Toast.LENGTH_LONG).show();
                }
                else{
                    OkHttpClient client = new OkHttpClient();
                    String json = gson.toJson(user);
                    //System.out.println(json);
                    RequestBody requestBodyPost = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),
                            json);
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBodyPost)
                            .build();
                    final Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            String err = e.getMessage().toString();
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String  rtn = response.body().string();
                            // final String code = String.valueOf(response.code());
                            if(rtn.equals("ok")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this,"注册成功，欢迎使用",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this,"用户名已存在，请重新输入",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
