package com.example.newreader.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newreader.BookInfoActivity;
import com.example.newreader.R;
import com.example.newreader.ReadActivity;
import com.example.newreader.bean.BodyBean;
import com.example.newreader.bean.ExampleBaseBean;
import com.example.newreader.bean.TitleBean;
import com.example.newreader.domain.BookList;

import java.util.List;

public class ReadingAdapter extends RecyclerView.Adapter {
    //获取类型
    public final static int TITLE = 1001;
    public final static int BODY = 1002;
    //父类对象（从外面传进来）
    private List<ExampleBaseBean> mlist;
    private Context context;
    private LayoutInflater inflater;
    public ReadingAdapter(List<ExampleBaseBean>mlist){
        this.mlist = mlist;
    }
    //自定义的Item的数量
    @Override
    public int getItemCount() {
        return mlist.size();
    }
    //获取Item类型
    @Override
    public int getItemViewType(int position){
        if(mlist.size()>0){
            return mlist.get(position).getType();
        }
        return super.getItemViewType(position);
    }
    //获取不同的类型的ViewHolder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        if (inflater == null)
            inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case TITLE:
                //设置titleRecyclerView的界面
                view = inflater.inflate(R.layout.item_layout_reading_title, parent, false);
                return new TitleHolder(view);
            case BODY:
                //设置BodyRecyclerView的界面
                view = inflater.inflate(R.layout.item_layout_reading_body, parent, false);
                return new BodyHolder(view);
        }
        return null;
    }
    //找到子控件的view id
    private class TitleHolder extends RecyclerView.ViewHolder{
        RecyclerView rw;
        public TitleHolder(View view){
            super(view);
            rw=itemView.findViewById(R.id.title_rv);

        }
    }
    private class BodyHolder extends RecyclerView.ViewHolder{
        RecyclerView rw1;
        public BodyHolder(View view){
            super(view);
            rw1=itemView.findViewById(R.id.body_rv);

        }
    }
    //绑定数据，包括点击事件
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleHolder) {
            final TitleBean titleBean = (TitleBean) mlist.get(position);
            //设置titleRecyclerView的布局和点击事件
            ((TitleHolder) holder).rw.setLayoutManager(new GridLayoutManager(context,4));
            TitleAdapter titleAdapter =new TitleAdapter(titleBean.getTitles());
            titleAdapter.setOnItemClickListener(new TitleAdapter.OnRecyclerItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(context, BookInfoActivity.class);
                    //finish();
                    List<BookList> books= titleBean.getTitles();
                    Log.e("this is the readingAdapter's titlsadpter's booktitle",books.get(position).getTitle());
                    intent.putExtra("title",books.get(position).getTitle());
                    intent.putExtra("author",books.get(position).getAuthor());
                    intent.putExtra("intro",books.get(position).getIntro());
                    context.startActivity(intent);
                }
            });
            ((TitleHolder) holder).rw.setAdapter(titleAdapter);
        }

        if (holder instanceof BodyHolder) {
            final BodyBean bodyBean = (BodyBean) mlist.get(position);
            ((BodyHolder) holder).rw1.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            final BodyAdapter bodyAdapter =new BodyAdapter(bodyBean.getBooks());
            bodyAdapter.setOnItemClickListener(new BodyAdapter.OnRecyclerItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(context, BookInfoActivity.class);
                    //finish();
                    List<BookList> books= bodyBean.getBooks();
                    Log.e("this is the readingAdapter's titlsadpter's booktitle",books.get(position).getTitle());
                    intent.putExtra("title",books.get(position).getTitle());
                    intent.putExtra("author",books.get(position).getAuthor());
                    intent.putExtra("intro",books.get(position).getIntro());
                    context.startActivity(intent);
                }
            });
            ((BodyHolder) holder).rw1.setAdapter(bodyAdapter);
        }
    }

}
