package com.example.a09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlistView = findViewById(R.id.lv);
        init();
    }

    private void init() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("测试" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);
        mlistView.setAdapter(adapter);
    }

    public void onListView(View view) {


    }

    public void onGridView(View view) {
        startActivity(new Intent(this,GridViewTest.class));
    }

    public void onRecycleView(View view) {
        startActivity(new Intent(this,RecycleTest.class));
    }
}