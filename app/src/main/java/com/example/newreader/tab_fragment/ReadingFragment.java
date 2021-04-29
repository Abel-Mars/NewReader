package com.example.newreader.tab_fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newreader.R;
import com.example.newreader.adapter.ReadingAdapter;
import com.example.newreader.bean.BodyBean;
import com.example.newreader.bean.ExampleBaseBean;
import com.example.newreader.bean.TitleBean;
import com.example.newreader.domain.BookList;
import com.example.newreader.domain.Url;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReadingFragment extends Fragment {

    OkHttpClient client = new OkHttpClient();
    RecyclerView rv ;
    final VariableHolder holder = new VariableHolder();
    List<ExampleBaseBean> mlist = new ArrayList<>(2);
    //让adapter变成可以全局访问的
    private class VariableHolder {
        public ReadingAdapter adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reading, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rv = getView().findViewById(R.id.rv);
        BodyBean bodyBean = new BodyBean();
        bodyBean.setType(1002);
        List<BookList> list_books = new ArrayList<>();
        bodyBean.setBooks(list_books);
        mlist.add(bodyBean);
        mlist.add(bodyBean);
        initTitle();
        initBody();
        initAdapter();
    }

    public void initTitle(){
        //final String url =  "http://192.168.2.192:8090/scoreTop";
        Url url_pre = new Url();
        final String url =  url_pre.getUrl()+"/scoreTop";
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
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
                    Gson gson = new Gson();
                    final List<BookList>books =  gson.fromJson(rtn,new TypeToken<List<BookList>>(){}.getType());
                    Log.e("the size of noval is :",String.valueOf(books.size()));
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TitleBean titleBean =new TitleBean();
                            titleBean.setTitles(books);
                            titleBean.setType(ReadingAdapter.TITLE);
                            mlist.set(0, titleBean);

                            if (holder.adapter != null)
                                holder.adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        /*测试title数据
        final List<BookList>all_book = new ArrayList<>();
        for(int i=0;i<5;i++){
            BookList book =new BookList();
            book.setTitle("the "+i+" all_book");
            book.setAuthor("this is the "+i+" Author");
            book.setIntro("hhhhhhhhh,hhhhhhhhh,");
            all_book.add(book);
        }
        TitleBean titleBean =new TitleBean();
        titleBean.setTitles(all_book);
        titleBean.setType(ReadingAdapter.TITLE);
        mlist.add(titleBean);*/
    }

    public void initBody(){
        /*测试body数据
        final List<BookList>books = new ArrayList<>();
        for(int i=0;i<5;i++){
            BookList book =new BookList();
            book.setTitle("the "+i+" body");
            book.setAuthor("this is the "+i+" Author");
            book.setIntro("hhhhhhhhh,hhhhhhhhh,");
            book.setType("军事历史");
            book.setIdbook(5);
            books.add(book);
        }*/
        //final String url =  "http://192.168.2.192:8090/newTop";
        Url url_pre = new Url();
        final String url =  url_pre.getUrl()+"/newTop";

        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
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
                if(!rtn.equals("")) {
                    Gson gson = new Gson();
                    final List<BookList> books = gson.fromJson(rtn, new TypeToken<List<BookList>>() {}.getType());
                    Log.e("the size of noval is :", String.valueOf(books.size()));
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BodyBean bodyBean = new BodyBean();
                            bodyBean.setBooks(books);
                            bodyBean.setType(ReadingAdapter.BODY);
                            mlist.set(1, bodyBean);
                            if (holder.adapter != null)
                                holder.adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
    public void initAdapter(){
        //设置外层RecyclerView的Adapter（ReadingAdapter）
        if (holder.adapter == null) {
            holder.adapter = new ReadingAdapter(mlist);
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            rv.setAdapter(holder.adapter);
        } else {
            holder.adapter.notifyDataSetChanged();
        }

    }
}
