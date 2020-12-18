package com.app.shop.demo.adapters;

import android.content.Context;
import android.widget.TextView;

import com.app.shop.demo.R;
import com.app.shop.demo.beans.GoodsBean;
import com.app.shop.demo.beans.OrderBean;
import com.app.shop.mylibrary.base.CommonAdapter;
import com.app.shop.mylibrary.base.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;



public class OrderListAdapter extends CommonAdapter {
    public OrderListAdapter(Context context, ArrayList datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void setView(ViewHolder holder, Object o, int position) {
        OrderBean bean = (OrderBean) o;
        TextView tv_name = holder.getView(R.id.tv_name_order_list);
        TextView tv_num = holder.getView(R.id.tv_num);
        TextView tv_price = holder.getView(R.id.tv_price_total);


        tv_name.setText(bean.getUser_name()+" - "+bean.getOrder_time());
        if (bean.getOrderItemBeans() != null && bean.getOrderItemBeans().size() > 0) {
            tv_num.setText(bean.getOrderItemBeans().size() + "种商品");

            double price = 0;
            for (int i = 0; i < bean.getOrderItemBeans().size(); i++) {
                price += (bean.getOrderItemBeans().get(i).getGoods_price() * bean.getOrderItemBeans().get(i).getGoods_num());
            }
            tv_price.setText("订单总价：￥ " + price);
        }
    }
}
