package com.example.newreader.fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.newreader.LishiActivity;
import com.example.newreader.R;
import com.example.newreader.adapter.BookChannelAdapter;
import com.example.newreader.domain.Type;
import com.example.newreader.domain.Url;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChannelFragment extends Fragment {
    List<Integer> ad_pics = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_channel, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ViewPager2 adview = getView().findViewById(R.id.view_pager_ad);
        final RecyclerView rv = getView().findViewById(R.id.rv_book_channel);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),2));

        final List<Type>types = new ArrayList<>();

        Type type0 = new Type("历史军事");
        Type type1 = new Type("玄幻奇幻");
        Type type2 = new Type("武侠仙侠");
        Type type3 = new Type("女频言情");
        Type type4 = new Type("都市生活");
        Type type5 = new Type("游戏竞技");
        Type type6 = new Type("科幻灵异");
        Type type7 = new Type("美文同人");

        types.add(type0);
        types.add(type1);
        types.add(type2);
        types.add(type3);
        types.add(type4);
        types.add(type5);
        types.add(type6);
        types.add(type7);

        BookChannelAdapter bookChannelAdapter = new BookChannelAdapter(getActivity(),types);
        rv.setAdapter(bookChannelAdapter);
        bookChannelAdapter.setOnItemClickListener(new BookChannelAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Url url_pre = new Url();
                final String url =  url_pre.getUrl()+"/Lishi";

//                final String url =  "http://192.168.2.192:8090/Lishi";
                //url = "http://192.168.2.192:8090/Lishi";
                Gson gson = new Gson();
                Type type = types.get(position);
                String json = gson.toJson(type);
                OkHttpClient client = new OkHttpClient();
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
                        // final String code = String.valueOf(response.code());
                        if(!rtn.equals("")){
                            Intent intent = new Intent(getActivity(), LishiActivity.class);
                            intent.putExtra("bookData",rtn);
                            startActivity(intent);
                        }
                        else{
                            //May NullPointer Error!
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),"加载列表失败",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
            }
        });
        //滚动广告页面
        ad_pics.add(R.drawable.guanggao4);
        ad_pics.add(R.drawable.guanggao1);
        ad_pics.add(R.drawable.guanggao2);
        ad_pics.add(R.drawable.guanggao3);
        ad_pics.add(R.drawable.guanggao4);
        ad_pics.add(R.drawable.guanggao1);
        // Log.e("!!!!!!!!!!!!", androidx.viewpager2.widget.ViewPager2.ScROLLSTAT)
        adview.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.e("--------------------", "current state=" + state);
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    if (adview.getCurrentItem() == 0) {
                        //Log.e("--------------------", "0 to size-2");
                        adview.setCurrentItem(ad_pics.size() - 2, false);
                    }

                    if (adview.getCurrentItem() == ad_pics.size() - 1) {
                        //Log.e("--------------------", "size-1 to 1");
                        adview.setCurrentItem(1, false);
                    }
                }
                super.onPageScrollStateChanged(state);
            }
        });
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(getActivity()).inflate(R.layout.ad_item,parent,false);
                return new ViewHolder(v);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ViewHolder h = (ViewHolder) holder;
                h.container.setBackgroundResource(ad_pics.get(position));
            }

            @Override
            public int getItemCount() {
                return ad_pics.size();
            }
        };
        adview.setAdapter(adapter);
        adview.setCurrentItem(1, false);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout container;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            container = itemView.findViewById(R.id.ad_container);
        }
    }
}
