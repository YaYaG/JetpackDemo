package com.wangjy.room;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User user = new User();
                        user.firstName="jack"+ new Date().getTime();
                        user.lastName = "wang";
                        AppDatabase.getInstance(MainActivity.this)
                                .userDao().insertAll(user);
                    }
                }).start();

            }
        });


        findViewById(R.id.btnSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final List<User> all = AppDatabase.getInstance(MainActivity.this).userDao()
                                .getAll();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, all.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
