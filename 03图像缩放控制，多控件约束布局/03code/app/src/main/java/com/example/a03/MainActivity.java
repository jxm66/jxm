package com.example.a03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=findViewById(R.id.image);
        findViewById(R.id.txt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setScaleType(ImageView.ScaleType.CENTER);
            }
        });
        findViewById(R.id.txt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        });;
        findViewById(R.id.txt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
        });;
    }


}