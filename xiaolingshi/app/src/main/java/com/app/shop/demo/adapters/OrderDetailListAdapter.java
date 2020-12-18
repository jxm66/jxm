package com.app.shop.demo.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.app.shop.demo.MyApplication;
import com.app.shop.demo.R;
import com.app.shop.demo.beans.GoodsBean;
import com.app.shop.demo.beans.OrderItemBean;
import com.app.shop.mylibrary.base.CommonAdapter;
import com.app.shop.mylibrary.base.ViewHolder;
import com.app.shop.mylibrary.beans.EventMessage;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;



public class OrderDetailListAdapter extends CommonAdapter {
    public OrderDetailListAdapter(Context context, ArrayList datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void setView(ViewHolder holder, Object o, int position) {
        OrderItemBean bean = (OrderItemBean) o;
        SimpleDraweeView simpleDraweeView = holder.getView(R.id.imgv_list);
        TextView tv = holder.getView(R.id.tv_list_item);
        TextView tv_price = holder.getView(R.id.tv_price);
        TextView tv_num = holder.getView(R.id.tv_num);
        TextView tv_total = holder.getView(R.id.tv_price_total);

        simpleDraweeView.setImageResource(bean.getGoods_pic());
        tv.setText(bean.getGoods_name());
        tv_price.setText("单价￥ " + bean.getGoods_price());
        tv_num.setText(bean.getGoods_num() + "个");
        tv_total.setText("共计￥ " + bean.getGoods_price() * bean.getGoods_num());
    }
}
