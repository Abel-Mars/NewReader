package com.example.newreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newreader.adapter.BookCommentAdapter;
import com.example.newreader.domain.BookComment;
import com.example.newreader.domain.BookShelf;
import com.example.newreader.domain.PostScore;
import com.example.newreader.domain.Title;
import com.example.newreader.domain.Url;
import com.example.newreader.domain.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BookInfoActivity extends AppCompatActivity {
    public static List<BookComment> bookComments = new ArrayList<>();
    final OkHttpClient client = new OkHttpClient();
    final User user =LoginActivity.user1;
    String all_title;
    TextView tv_score;
    double all_score=0;
    private BookCommentAdapter bookCommentAdapter = new BookCommentAdapter(BookInfoActivity.this,bookComments);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        all_title =title;
        String author = intent.getStringExtra("author");
        String intro = intent.getStringExtra("intro");
//        String bid = intent.getStringExtra("bid");
        //Log.e("--------this is the bid",String.valueOf(bid));
        setContentView(R.layout.activity_book_info);
        TextView tv_title = findViewById(R.id.book_info_title);
        TextView tv_author = findViewById(R.id.book_info_author);
        TextView tv_intro = findViewById(R.id.book_info_intro);
        TextView tv_title1 = findViewById(R.id.book_info_title1);
        tv_score = findViewById(R.id.book_info_score);
        final RecyclerView rv = findViewById(R.id.book_info_rv);
        Button bt_to_bookshelf = findViewById(R.id.book_info_into_bookshelf);
        Button bt_start_read = findViewById(R.id.book_info_start_read);
        Button bt_insert_comment = findViewById(R.id.book_info_insert_comment);

        //设置书本的基本信息
        tv_title.setText(title);
        tv_author.setText(author);
        tv_intro.setText(intro);
        tv_title1.setText(title);

        //设置RecyclerView的adapter
        //final List<BookComment> comments = new ArrayList<>();
        /*for(int i =0 ;i<10;i++){
            BookComment b = new BookComment();
            b.setComment("芜湖起飞，好看好看太好看了，就是有点难看，可以看也可以不看，建议可以看一看，不过不看也没有关系，因为不好看所以好看");
            b.setScore(9.0);
            b.setTime(new Date());
            b.setUsername("tyy");
            comments.add(b);
        }*/
        //从服务端获取数据：
//        final String url="http://192.168.43.234:8090/getComment";
//        final String url = "http://192.168.2.192:8090/getComment";
        Url url_pre = new Url();
        final String url =  url_pre.getUrl()+"/getComment";
        Gson gson = new Gson();
        Title title_to_service = new Title();
        title_to_service.setTitle(all_title);
        final String json = gson.toJson(title_to_service);
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
                Log.e("this is the from :",rtn);
                if (rtn != "") {
                    Gson gson1 = new Gson();
                    final List<BookComment>bookComments1 = gson1.fromJson(rtn,new TypeToken<List<BookComment>>(){}.getType());
                    bookComments.clear();
                    bookComments.addAll(bookComments1);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookInfoActivity.this);
                            rv.setLayoutManager(linearLayoutManager);
                            //Log.i("this is the comment",bookComments.get(0).getComment());
                            rv.setAdapter(bookCommentAdapter);
//                            TextView tv_score = findViewById(R.id.book_info_score);

                            for(int i=0;i<bookComments.size();i++){
                                all_score+=bookComments.get(i).getScore();
                            }
                            double avg_score =all_score/bookComments.size();
                            if(bookComments.size()>0){
                                DecimalFormat df = new DecimalFormat("0.00");
                                tv_score.setText(df.format(avg_score));
                            }
                        }
                    });
                }
                else{

                }
            }
        });
//        设置评论区域的rv
        //加入书架
        bt_to_bookshelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("username is this one",user.getUsername());
                if(user.getUsername()!="未登录"){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            postToBookShelf();
                        }
                    });
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BookInfoActivity.this,"添加书架前，请先登录,",Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent2 = new Intent(BookInfoActivity.this,LoginActivity.class);
                    startActivity(intent2);
                }

            }
        });
        //bookComments.clear();
        //进入阅读模式
        bt_start_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BookInfoActivity.this,ReadActivity.class);
                intent1.putExtra("title",all_title);
                startActivity(intent1);
            }
        });
        //添加评论
        bt_insert_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("username is this one",user.getUsername());
                if(user.getUsername()!="未登录"){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            insertComment();
                        }
                    });
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BookInfoActivity.this,"评论前，请先登录,",Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent2 = new Intent(BookInfoActivity.this,LoginActivity.class);
                    startActivity(intent2);
                }
            }
        });

    }
    public void postToBookShelf() {
        Url url_pre = new Url();
        String url_to_book_shelf =  url_pre.getUrl()+"/toBookShelf";

        //String url_to_book_shelf="http://192.168.2.192:8090/toBookShelf";
        BookShelf bookShelf = new BookShelf();

        bookShelf.setTitle(all_title);
        Log.e("----------this is the title for bookshelf",user.getUsername());
        bookShelf.setUsername(user.getUsername());
        Gson gson_comment = new Gson();
        final String json3 = gson_comment.toJson(bookShelf);
        RequestBody requestBodyPost = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),
                json3);
        Request request = new Request.Builder()
                .url(url_to_book_shelf)
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
                final String rtn = response.body().string();
                Log.e("this is the from :", rtn);
                if (rtn.equals("ok")) {
                    //再确认插入后
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BookInfoActivity.this,"已加入书架,",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
    public void insertComment(){
        AlertDialog.Builder builder = new AlertDialog.Builder(BookInfoActivity.this);
        builder.setTitle("添加评论");//设置标题
        View view = LayoutInflater.from(BookInfoActivity.this).inflate(R.layout.dialog_insert_comment,null);//获得布局信息
        final EditText score = (EditText) view.findViewById(R.id.dialog_insert_score);
        final EditText comments = (EditText) view.findViewById(R.id.dialog_insert_comment);
        builder.setView(view);//给对话框设置布局
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //点击确定按钮的操作
                Url url_pre = new Url();
                //String url_insert = "http://192.168.2.192:8090/insertComment";
                String url_insert =  url_pre+"/insertComment";
//                String url_insert="http://192.168.43.234:8090/insertComment";
                final BookComment bookComment_to_service = new BookComment();
                bookComment_to_service.setUsername(user.getUsername());
                bookComment_to_service.setScore(Double.valueOf(score.getText().toString()));
                bookComment_to_service.setTitle(all_title);
                bookComment_to_service.setComment(comments.getText().toString());
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sim = dateFormat.format(date);
                bookComment_to_service.setTime(sim);
//                Log.e("BookInfo_insert","the username:"+bookComment_to_service.getUsername()+"\n the score:"+bookComment_to_service.getScore()
//                +"\n the title:"+bookComment_to_service.getTitle()+"\n the time:"+bookComment_to_service.getTime());
                Gson gson_comment = new Gson();
                final String json2 = gson_comment.toJson(bookComment_to_service);
                RequestBody requestBodyPost = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),
                        json2);
                Request request = new Request.Builder()
                        .url(url_insert)
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
                        Log.e("this is the from :",rtn);
                        if(rtn.equals("ok")){
                            //再确认插入后，不再请求数据，而是直接在前端修改


                            bookComments.add(bookComments.size(),bookComment_to_service);
                            Log.e("size of book comment in adapter", String.valueOf(bookCommentAdapter.getItemCount()));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    double avg_score =(all_score+Double.valueOf(score.getText().toString()))/bookComments.size();
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    tv_score.setText(df.format(avg_score));
                                    bookCommentAdapter.notifyDataSetChanged();
                                    PostScore postScore = new PostScore();
                                    postScore.setScore(avg_score);
                                    postScore.setTitle(all_title);
                                    Gson gson_score = new Gson();
                                    Url url_pre = new Url();
                                    String url_socre =  url_pre+"/updateScore";
                                    //String url_socre = "http://192.168.2.192:8090/updateScore";
                                    final String json_score = gson_score.toJson(postScore);
                                    RequestBody requestBodyPost = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),
                                            json_score);
                                    Request request = new Request.Builder()
                                            .url(url_socre)
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
                                            final String rtn = response.body().string();
                                            Log.e("this is the from :", rtn);
                                        }
                                    });

                                }
                            });
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
}
