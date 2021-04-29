package com.example.newreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newreader.R;
import com.example.newreader.domain.BookComment;

import java.util.List;

public class BookCommentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<BookComment> bookComments;
    public BookCommentAdapter(Context mContext,List<BookComment>bookComments){
        this.mContext =mContext;
        this.bookComments =bookComments;
    }
    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView score;
        TextView comment;
        TextView time;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.item_book_comment_username);
            score= itemView.findViewById(R.id.item_book_comment_score);
            time = itemView.findViewById(R.id.item_book_comment_time);
            comment = itemView.findViewById(R.id.item_book_comment_comment);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_book_comment, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).username.setText(bookComments.get(position).getUsername());
            ((MyViewHolder) holder).score.setText(String.valueOf(bookComments.get(position).getScore()));
            ((MyViewHolder) holder).comment.setText(bookComments.get(position).getComment());
            ((MyViewHolder) holder).time.setText(String.valueOf(bookComments.get(position).getTime()));
        }
    }

    @Override
    public int getItemCount() {
        return bookComments.size();
    }
}
