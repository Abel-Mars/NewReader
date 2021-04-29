package com.example.newreader.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.xtablayout.XTabLayout;
import com.example.newreader.R;
import com.example.newreader.tab_fragment.ReadingFragment;
import com.example.newreader.tab_fragment.FindingFragment;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    XTabLayout tabLayout;
    ViewPager2 viewPager;
    List<Fragment> fragments = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        tabLayout=getView().findViewById(R.id.tab);

        viewPager=getView().findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("看小说"));

        tabLayout.addTab(tabLayout.newTab().setText("求小说"));

        tabLayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {

            @Override

            public void onTabSelected(XTabLayout.Tab tab) {
                //获取当前导航卡的位置及文本
                int position = tab.getPosition();

                viewPager.setCurrentItem(position);//使ViewPager和的导航块联动

            }

            @Override

            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override

            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });

        fragments.add(new ReadingFragment());

        fragments.add(new FindingFragment());


        viewPager.setAdapter(new FragmentStateAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                super.onPageSelected(position);

                tabLayout.getTabAt(position).select();//设置指定位置的被选中

            }
        });
    }
}
