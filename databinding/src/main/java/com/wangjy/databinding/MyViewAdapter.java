package com.wangjy.databinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wangjy.databinding.databinding.ItemViewBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 姓名: Jack
 * 时间： 2020-01-25
 * 描述：
 */
public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mStringList;

    public MyViewAdapter(Context context, List<String> stringList) {
        mContext = context;
        mStringList = stringList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemViewBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_view, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String s = mStringList.get(position);
        holder.mItemViewBinding.setName(s);
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemViewBinding mItemViewBinding;
        public MyViewHolder(@NonNull ItemViewBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            this.mItemViewBinding = itemViewBinding;
        }
    }
}
