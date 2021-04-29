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
import android.widget.TextView;
import android.widget.Toast;

import com.example.newreader.domain.Url;
import com.example.newreader.domain.User;
import com.example.newreader.fragment.UserFragment;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.*;
public class LoginActivity extends AppCompatActivity {
    public static User user1 = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = findViewById(R.id.btn_login);
        final EditText username = findViewById(R.id.et_user_name);
        final EditText password = findViewById(R.id.et_psw);
        TextView register = findViewById(R.id.tv_register);
        final CheckBox isCheck = findViewById(R.id.show_hide);
        isCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCheck.isChecked()){
                    isCheck.setButtonDrawable(R.mipmap.password_show);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    isCheck.setButtonDrawable(R.mipmap.password_hide);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
//        final String url = "http://192.168.43.234:8090/login";
        Url url_pre =new Url();
        final String url =  url_pre.getUrl()+"/login";
//        final String url = "http://192.168.2.192:8090/login";
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(username.getText().toString()+password.getText().toString());
                OkHttpClient client = new OkHttpClient();
                Gson gson = new Gson();
                final User user =  new User();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                if(username.getText().toString().equals("")||password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"用户名密码不能为空",Toast.LENGTH_LONG).show();
                }
                else{
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
                                user1 = user;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this,"欢迎回来,"+user.getUsername(),Toast.LENGTH_LONG).show();
                                    }
                                });
                                Intent intent = new Intent(LoginActivity.this,ShowActivity.class);
                                startActivity(intent);
                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        username.setText("");
                                        password.setText("");
                                        Toast.makeText(LoginActivity.this,"用户名或密码错误,请先注册",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }
}





