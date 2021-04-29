package com.example.newreader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newreader.R;
import com.example.newreader.domain.BookList;

import java.util.List;
//常规的RecyclerView的Adapter写法
public class TitleAdapter extends RecyclerView.Adapter {
    private OnRecyclerItemClickListener mOnItemClickListener;
    private List<BookList> books;
    public TitleAdapter(List<BookList> books){
        this.books=books;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_book_cover, parent, false);
        return new TitleAdapter.MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleAdapter.MyViewHolder) {
            ((TitleAdapter.MyViewHolder) holder).title.setText(books.get(position).getTitle());
            ((TitleAdapter.MyViewHolder) holder).title1.setText(books.get(position).getTitle());
            if(mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v,((TitleAdapter.MyViewHolder)holder).getLayoutPosition());
                    }
                });
            }
        }
    }
    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView title1;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.item_book_cover_title);
            title1 = itemView.findViewById(R.id.item_book_cover_title1);
        }
    }
    @Override
    public int getItemCount() {
        return books.size();
    }
    public interface OnRecyclerItemClickListener {
        public void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(TitleAdapter.OnRecyclerItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}
