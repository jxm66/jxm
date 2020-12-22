package com.example.a09;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GridViewTest extends AppCompatActivity {
    private GridView gridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        gridView = findViewById(R.id.gv);
        init();
    }

    private void init(){
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("测试" + i);
        }

        gridView.setAdapter(new MyAdapter(data,this));
    }

    public  class MyAdapter extends BaseAdapter{
        private List<String> dataList;
        private LayoutInflater layoutInflater;
        public MyAdapter(List<String> data, Context context) {
            dataList=data;
            layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.grid_item,null);
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String str = dataList.get(position);
            if (str != null) {
                holder.text.setText(str);
            }
            return convertView;
        }

        class ViewHolder {
            TextView text;
        }

    }
}
