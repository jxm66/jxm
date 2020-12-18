package com.app.shop.demo.adapters;

import android.content.Context;
import android.widget.TextView;

import com.app.shop.demo.R;
import com.app.shop.demo.beans.GoodsBean;
import com.app.shop.mylibrary.base.CommonAdapter;
import com.app.shop.mylibrary.base.ViewHolder;
import com.app.shop.mylibrary.beans.UserBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;




public class GoodsListAdapter extends CommonAdapter {

    public GoodsListAdapter(Context context, ArrayList datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void setView(ViewHolder holder, Object o, int position) {
        GoodsBean bean = (GoodsBean) o;
        SimpleDraweeView imgv = holder.getView(R.id.imgv_list);
        TextView textView = holder.getView(R.id.tv_list_item);
        TextView tv_price = holder.getView(R.id.tv_price);
        TextView tv_type = holder.getView(R.id.tv_list_type);
        TextView tv_1 = holder.getView(R.id.tv_1);
        TextView tv_2 = holder.getView(R.id.tv_2);
        TextView tv_3 = holder.getView(R.id.tv_3);

        textView.setText(bean.getGoods_name());
        imgv.setActualImageResource(bean.getGoods_pic());
        tv_price.setText("￥" + bean.getGoods_price());
        tv_type.setText(bean.getGoods_type());
        tv_1.setText("广告：" +bean.getDbz());
        tv_2.setText("分类：" +bean.getKll());
        tv_3.setText("产地：" +bean.getZf());
    }
}
