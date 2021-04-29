package com.example.newreader.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newreader.LoginActivity;
import com.example.newreader.R;
import com.example.newreader.domain.User;

public class UserFragment extends Fragment {
    String Activity = "UserFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Button linearLayout_login = getView().findViewById(R.id.user_login);
        TextView login_te = getView().findViewById(R.id.login_judge);
        Log.i(Activity,LoginActivity.user1.getUsername());
        if(LoginActivity.user1.getUsername()!="未登录"){
            TextView user_tx = getView().findViewById(R.id.te_user_frg_username);
            try{
                linearLayout_login.setBackgroundResource(R.color.colorPrimary);
                linearLayout_login.setText("退出登录");
                user_tx.setText(LoginActivity.user1.getUsername());
                login_te.setText("已登录");

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            linearLayout_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
