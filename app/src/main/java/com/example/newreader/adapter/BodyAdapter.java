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

public class BodyAdapter extends RecyclerView.Adapter {
    private List<BookList> books;
    private OnRecyclerItemClickListener mOnItemClickListener;
    public BodyAdapter(List<BookList> books){
        this.books=books;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_book_cover_horizen, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).title.setText(books.get(position).getTitle());
            ((MyViewHolder) holder).jianjie.setText(books.get(position).getIntro());
            ((MyViewHolder) holder).author.setText(books.get(position).getAuthor());
            ((MyViewHolder) holder).title1.setText(books.get(position).getTitle());
            if(mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v,((MyViewHolder)holder).getLayoutPosition());
                    }
                });
            }
        }
    }
    private class MyViewHolder extends RecyclerView.ViewHolder {

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
    @Override
    public int getItemCount() {
        return books.size();
    }
    public interface OnRecyclerItemClickListener {
        public void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(BodyAdapter.OnRecyclerItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}

