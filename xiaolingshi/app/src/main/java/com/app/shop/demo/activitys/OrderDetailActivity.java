package com.app.shop.demo.activitys;

import android.os.Bundle;
import android.widget.TextView;

import com.app.shop.demo.R;
import com.app.shop.demo.adapters.OrderDetailListAdapter;
import com.app.shop.demo.beans.OrderBean;
import com.app.shop.demo.beans.OrderItemBean;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.widgts.ScrollListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.lv)
    ScrollListView lv;

    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    double price_total;
    OrderDetailListAdapter adapter;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        tvTitle.setText("订单详情");
        initView();
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        OrderBean orderBean = (OrderBean) bundle.getSerializable("orderBean");
        List<OrderItemBean> list = new ArrayList();
        if (orderBean != null && orderBean.getOrderItemBeans() != null) {
            list.addAll(orderBean.getOrderItemBeans());
        }

        adapter = new OrderDetailListAdapter(this, (ArrayList) list, R.layout.item_order);
        lv.setAdapter(adapter);

        price_total = 0;
        for (int i = 0; i < list.size(); i++) {
            price_total += (list.get(i).getGoods_price() * list.get(i).getGoods_num());
        }
        tvUserName.setText("下单人：" + orderBean.getUser_name());
        tvTotal.setText("订单总额：￥" + price_total);
        orderTime.setText("下单时间：" + StringUtil.getContent(orderBean.getOrder_time()));


    }

    @OnClick(R.id.imgv_return)
    public void onViewClicked() {
        onBackPressed();

    }
}
