package com.app.shop.demo.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.app.shop.demo.R;
import com.app.shop.demo.adapters.GridAdapter;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ChoicePicActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.gridview)
    GridView gridview;

    GridAdapter adapter;
    List<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_pic);
        ButterKnife.bind(this);
        tvTitle.setText("图片");
        initData();
    }

    private void initData() {

        list.add(R.mipmap.pic_liangcai);
        list.add(R.mipmap.pic_recai);
        list.add(R.mipmap.pic_zhushi);
        list.add(R.mipmap.pic_tiandian);
        list.add(R.mipmap.pic_yinliao);

        adapter = new GridAdapter(this, (ArrayList) list, R.layout.item_gridview);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new EventMessage(EventMessage.CHOICE_PIC, list.get(position)));
                finish();
            }
        });

    }

    @OnClick(R.id.imgv_return)
    public void onViewClicked() {
        onBackPressed();
    }
}
