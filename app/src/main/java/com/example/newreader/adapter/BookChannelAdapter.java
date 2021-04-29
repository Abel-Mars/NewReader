package com.example.newreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newreader.R;
import com.example.newreader.domain.Type;

import java.util.List;

public class BookChannelAdapter extends RecyclerView.Adapter<BookChannelAdapter.MyViewHolder>{
    private List<Type> types;
    private Context mContext;
    private OnRecyclerItemClickListener mOnItemClickListener;

    public BookChannelAdapter(Context mContext,List<Type>types){
        this.mContext = mContext;
        this.types = types;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView type;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_channel, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            holder.type.setText(types.get(position).getType());
            if(mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v,holder.getLayoutPosition());
                    }

                });
            }
    }

    @Override
    public int getItemCount() {
        return types.size();
    }
    public interface OnRecyclerItemClickListener {
        public void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}
