package com.example.a09;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder{
    private TextView mTvLabel; // 标签

    public MyViewHolder(View itemView) {
        super(itemView);
        mTvLabel = (TextView) itemView.findViewById(R.id.content);
    }

    public TextView getTvLabel() {
        return mTvLabel;
    }


}
