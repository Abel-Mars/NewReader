package com.example.newreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.newreader.fragment.BookshelfFragment;
import com.example.newreader.fragment.MainFragment;
import com.example.newreader.fragment.ChannelFragment;
import com.example.newreader.fragment.UserFragment;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        MainFragment mainFragment = new MainFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, mainFragment);
        transaction.commit();
    }
    public void myClick(View view){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.rb_main:
                transaction.replace(R.id.container,new MainFragment());
                break;
            case R.id.rb_channel:

                transaction.replace(R.id.container,new ChannelFragment());
                break;
            case R.id.rb_bookshelf:
                transaction.replace(R.id.container,new BookshelfFragment());
                break;
            case R.id.rb_user:
                transaction.replace(R.id.container,new UserFragment());
                break;
        }
        transaction.commit();
    }
}
