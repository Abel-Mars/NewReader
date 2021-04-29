package com.example.newreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.newreader.adapter.ScanViewAdapter;
import com.example.newreader.domain.Chapter;
import com.example.newreader.domain.Title;
import com.example.newreader.domain.Url;
import com.example.newreader.view.ScanView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReadActivity extends AppCompatActivity {
    List<Chapter> all_chapter = new ArrayList<>();
    int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_layout);
        final TextView Tchapter =  findViewById(R.id.chapter);
        final TextView Tcontent =  findViewById(R.id.content_page);
        final Button pre = findViewById(R.id.pre_chapter);
        final Button next = findViewById(R.id.next_chapter);
        Intent intent = getIntent();
        final String c_title = intent.getStringExtra("title");
//        final String url="http://192.168.43.234:8090/getChapter";
        Url url_pre = new Url();
        String url =  url_pre.getUrl()+"/getChapter";

//        String url = "http://192.168.2.192:8090/getChapter";
        Log.e("找到对应的title",c_title);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
//        final String c_title=booklist.get(position).getTitle();
        Log.e("找到对应的title",c_title);
        Title title = new Title();
        title.setTitle(c_title);
        Gson gson = new Gson();
        String json=gson.toJson(title);
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
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String  rtn = response.body().string();
                Log.e("this is the from :",rtn);
                // final String code = String.valueOf(response.code());
                if(!rtn.equals("")){
                    Gson gson1 = new Gson();
                    all_chapter =  gson1.fromJson(rtn,new TypeToken<List<Chapter>>(){}.getType());
                    Log.e("the size of noval is :",String.valueOf(all_chapter.size()));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Tchapter.setText(all_chapter.get(position).getChapter());
                            Tcontent.setText(all_chapter.get(position).getContent());
                        }
                    });
                }
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position>=0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Tchapter.setText(all_chapter.get(position).getChapter());
                            Tcontent.setText(all_chapter.get(position).getContent());
                        }
                    });
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position<all_chapter.size()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Tchapter.setText(all_chapter.get(position).getChapter());
                            Tcontent.setText(all_chapter.get(position).getContent());
                        }
                    });
                }
            }
        });
    }

}
