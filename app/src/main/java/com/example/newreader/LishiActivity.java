package com.example.newreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.newreader.adapter.BookListAdapter;
import com.example.newreader.domain.BookList;
import com.example.newreader.domain.Title;
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

public class LishiActivity extends AppCompatActivity {
//    List<String> titles = new ArrayList<>();
//    List<String> jianjies = new ArrayList<>();
//    List<String> author = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private BookListAdapter junshiAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lishi);
        Intent intent = getIntent();
        String rtn = intent.getStringExtra("bookData");
        Gson gson1 = new Gson();
        final List<BookList> booklist = gson1.fromJson(rtn,new TypeToken<List<BookList>>(){}.getType());
        for (BookList book : booklist) {
            Log.e("把jsonList转化为一个list对象", book.toString());
        }
        junshiAdapter = new BookListAdapter(this,booklist);
        mRecyclerView =  findViewById(R.id.lishi_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        junshiAdapter.setOnItemClickListener(new BookListAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final String c_title = booklist.get(position).getTitle();
                final String c_author = booklist.get(position).getAuthor();
                final String c_intro = booklist.get(position).getIntro();
                //final int c_bid = booklist.get(position).getIdbook();
               // Log.e("-------this is the bid in lishiActivity",String.valueOf(c_bid));
                Intent intent = new Intent(LishiActivity.this,BookInfoActivity.class);
                //finish();
                intent.putExtra("title",c_title);
                intent.putExtra("author",c_author);
                intent.putExtra("intro",c_intro);
               // intent.putExtra("bid",c_bid);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(junshiAdapter);
    }
}
