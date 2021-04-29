package com.example.newreader.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newreader.BookInfoActivity;
import com.example.newreader.LoginActivity;
import com.example.newreader.R;
import com.example.newreader.adapter.BookShelfAdapter;
import com.example.newreader.domain.BookList;
import com.example.newreader.domain.BookShelf;
import com.example.newreader.domain.Url;
import com.example.newreader.domain.User;
import com.example.newreader.domain.UserName;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BookshelfFragment extends Fragment {
    User user = LoginActivity.user1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookshelf, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RecyclerView rv = getView().findViewById(R.id.rv_bookshelf);
        TextView tv_user = getView().findViewById(R.id.user_bookshelf);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),3));
        Url url_pre = new Url();
        final String url =  url_pre.getUrl()+"/BookShelf";
        OkHttpClient client = new OkHttpClient();
        UserName userName = new UserName();
        tv_user.setText(user.getUsername());
        if(user.getUsername()!="未登录"){
            userName.setUsername(user.getUsername());
            Log.e("this is the bookshelf username",userName.getUsername());
            final Gson gson = new Gson();
            String json = gson.toJson(userName);
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
                    final String rtn = response.body().string();
                    Log.e("this is the from :", rtn);
                    Gson gson = new Gson();
                    final List<BookList> books = gson.fromJson(rtn, new TypeToken<List<BookList>>() {}.getType());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BookShelfAdapter bookShelfAdapter = new BookShelfAdapter(getActivity(),books);
                            rv.setAdapter(bookShelfAdapter);
                            bookShelfAdapter.notifyDataSetChanged();
                            bookShelfAdapter.setOnItemClickListener(new BookShelfAdapter.OnRecyclerItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(getActivity(),BookInfoActivity.class);
                                    intent.putExtra("title",books.get(position).getTitle());
                                    intent.putExtra("author",books.get(position).getAuthor());
                                    intent.putExtra("intro",books.get(position).getIntro());
                                    getActivity().startActivity(intent);

                                }
                            });
                        }
                    });
                }
            });
        }
        else{
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(),"使用个人书架功能，请先登录,",Toast.LENGTH_LONG).show();
                }
            });
        }


    }
}
