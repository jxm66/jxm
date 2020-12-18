package com.app.shop.demo.activitys;

import android.os.Bundle;
import android.service.autofill.Dataset;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.shop.demo.MyApplication;
import com.app.shop.demo.R;
import com.app.shop.demo.adapters.OrderListAdapter;
import com.app.shop.demo.beans.OrderBean;
import com.app.shop.demo.beans.OrderItemBean;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.utils.UserManager;
import com.app.shop.mylibrary.widgts.ScrollListView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderListActivity extends BaseActivity {

    @BindView(R.id.lv)
    ScrollListView lv;
    @BindView(R.id.rela_empty)
    RelativeLayout relaEmpty;

    OrderListAdapter adapter;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    List<OrderBean> list_all = new ArrayList<>();
    List<OrderBean> list = new ArrayList<>();
    List<OrderItemBean> list_item_all = new ArrayList<>();
    List<OrderItemBean> list_item = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        tvTitle.setText("订单列表");
        initView();
    }

    private void initView() {

        int usertype = UserManager.getUserType(this);
        list_all = DataSupport.findAll(OrderBean.class);
        list_item_all = DataSupport.findAll(OrderItemBean.class);
        list.clear();
        list_item.clear();

        if (usertype == 0) { //普通

            for (int i = 0; i < list_all.size(); i++) {
                if (list_all.get(i).getUser_id().equals(UserManager.getUserId(this))) {
                    list.add(list_all.get(i));
                }
            }

            for (int i = 0; i < list_item_all.size(); i++) {
                if (list_item_all.get(i).getUser_id().equals(UserManager.getUserId(this))) {
                    list_item.add(list_item_all.get(i));
                }
            }

        } else { //管理员
            if (list_all != null) {
                list.addAll(list_all);

            }
            if (list_item_all != null) {
                list_item.addAll(list_item_all);
            }
        }


        for (int i = 0; i < list.size(); i++) {
            List<OrderItemBean> list_real = new ArrayList<>();
            for (int j = 0; j < list_item.size(); j++) {
                if (list.get(i).getOrder_time().equals(list_item.get(j).getTime())) {
                    list_real.add(list_item.get(j));
                }
            }
            list.get(i).setOrderItemBeans(list_real);
        }

        Collections.reverse(list);

        if (list.size() > 0) {
            adapter = new OrderListAdapter(this, (ArrayList) list, R.layout.item_order_list);
            lv.setAdapter(adapter);
            lv.setVisibility(View.VISIBLE);
            relaEmpty.setVisibility(View.GONE);
        } else {
            lv.setVisibility(View.GONE);
            relaEmpty.setVisibility(View.VISIBLE);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //进入订单详情
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderBean", list.get(position));
                showActivity(OrderListActivity.this, OrderDetailActivity.class, bundle);
            }
        });
    }

    @OnClick(R.id.imgv_return)
    public void onViewClicked() {
        onBackPressed();
    }

}
