package com.example.a09;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class RecycleTest extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        recyclerView = findViewById(R.id.rv);
        init();
    }

    private void init() {
        List<DataModel> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataModel entity = new DataModel();
            entity.setLabel("测试" + i);
            data.add(entity);
        }

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new MyAdapter(data));
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<DataModel> mDataModels;

        MyAdapter(List<DataModel> dataModels) {
            mDataModels = dataModels;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            DataModel dataModel = mDataModels.get(position);
            holder.getTvLabel().setText(dataModel.getLabel());
        }

        @Override
        public int getItemCount() {
            return mDataModels.size();
        }
    }
}
