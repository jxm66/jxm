package com.example.a02paomadeng;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv1).setSelected(true);
        linearLayout = findViewById(R.id.linear);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                sendBtn();
            }
        });
        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                linearLayout.removeAllViews();
                return false;
            }
        });
    }
    Random random = new Random(20);

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sendBtn() {
        TextView tv = new TextView(this);
        String aaa="";
        if (random.nextInt() >10){
            aaa="你在干嘛";
        }else if (random.nextInt()<10){
            aaa="不告诉你";
        }else{
            aaa="不说拉倒";
        }
        String str = "2020-12-20"+random.nextInt()+":"+aaa;
        tv.setText(str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(layoutParams);
        linearLayout.addView(tv);
    }

}