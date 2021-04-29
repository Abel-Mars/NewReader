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

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> {
    private Context mContext;
    private List<BookList> books;
   /* private List<String> titles;
    private List<String> jianjies;
    private List<String> authors;*/
    private OnRecyclerItemClickListener mOnItemClickListener;
    public BookListAdapter(Context context, List<BookList> books1){
        this.mContext = context;
        this.books = books1;
    }

    //创建并返回ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_layout_book_cover_horizen,parent,false));
    }
    //ViewHolder绑定数据
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.jianjie.setText(books.get(position).getIntro());
        holder.title.setText(books.get(position).getTitle());
        holder.title1.setText(books.get(position).getTitle());
        holder.author.setText(books.get(position).getAuthor());
        if(mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v,holder.getLayoutPosition());
                }

            });
        }
    }
    //返回数据数量
    @Override
    public int getItemCount() {
        return books.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView jianjie;
        TextView author;
        TextView title1;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.title_item_junshi);
            jianjie= itemView.findViewById(R.id.jianjie_item_junshi);
            author = itemView.findViewById(R.id.author_item_junshi);
            title1 = itemView.findViewById(R.id.title1_item_junshi);
        }
    }
    public interface OnRecyclerItemClickListener {
        public void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}
