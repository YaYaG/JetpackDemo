package com.wangjy.databinding;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

/**
 * 姓名: Jack
 * 时间： 2020-01-24
 * 描述：
 */
public class ViewModel {

    public String name;
    public int age;
    public String imgUrl;


    public void click(View view) {
        Toast.makeText(view.getContext(), "hhh", Toast.LENGTH_SHORT).show();
    }

    @BindingAdapter("app:hideIfZero")
    public static void hideIfZero(ProgressBar progressBar, int number) {
        progressBar.setVisibility(number == 0 ? View.GONE : View.VISIBLE);
        progressBar.setProgress(number);
    }

    @BindingAdapter(value = {"app:imgUrl", "app:placeHolder"}, requireAll = false)
    public static void imgUrl(ImageView imageView, String url, Drawable placeHolder) {
        Glide.with(imageView.getContext()).load(url).placeholder(placeHolder).into(imageView);
    }



}
