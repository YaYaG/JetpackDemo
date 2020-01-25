package com.wangjy.databinding;

import android.os.Bundle;

import com.wangjy.databinding.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding =
                DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setLifecycleOwner(this);
        activityMainBinding.setName("Jack");

        ViewModel viewModel = new ViewModel();
        viewModel.name = "hello";
        viewModel.age = 12;
        viewModel.imgUrl = "https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1035415831,1465727770&fm=26&gp=0.jpg";
        viewModel.firstName.set("我的名字");

        activityMainBinding.setViewmodel(viewModel);

        RecyclerView recyclerView = activityMainBinding.recyclerView;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("item"+i);
        }
        MyViewAdapter myViewAdapter = new MyViewAdapter(this, list);
        recyclerView.setAdapter(myViewAdapter);
    }
}
