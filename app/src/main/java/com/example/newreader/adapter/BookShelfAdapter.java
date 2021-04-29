package com.example.newreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newreader.R;
import com.example.newreader.domain.BookList;

import java.util.List;

public class BookShelfAdapter extends RecyclerView.Adapter  {
    private BookShelfAdapter.OnRecyclerItemClickListener mOnItemClickListener;
    private List<BookList> books;
    private Context context;
    public BookShelfAdapter(Context context,List<BookList>books){this.context=context;this.books=books;}
    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        TextView title1;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.item_book_cover_title);
            title1 = itemView.findViewById(R.id.item_book_cover_title1);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_book_cover, parent, false);
        return new BookShelfAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookShelfAdapter.MyViewHolder) {
            ((BookShelfAdapter.MyViewHolder) holder).title.setText(books.get(position).getTitle());
            ((BookShelfAdapter.MyViewHolder) holder).title1.setText(books.get(position).getTitle());
            if(mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v,((BookShelfAdapter.MyViewHolder)holder).getLayoutPosition());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
    public interface OnRecyclerItemClickListener {
        public void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(BookShelfAdapter.OnRecyclerItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}
