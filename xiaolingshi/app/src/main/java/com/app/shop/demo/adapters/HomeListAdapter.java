package com.app.shop.demo.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.shop.demo.MyApplication;
import com.app.shop.demo.R;
import com.app.shop.demo.activitys.GoodsinfoActivity;
import com.app.shop.demo.beans.GoodsBean;
import com.app.shop.demo.beans.ShopCarBean;
import com.app.shop.mylibrary.base.CommonAdapter;
import com.app.shop.mylibrary.base.ViewHolder;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.utils.SharedPreferencesUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.utils.UserManager;
import com.facebook.drawee.view.SimpleDraweeView;

import org.litepal.crud.DataSupport;

import java.io.PipedInputStream;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;



public class HomeListAdapter extends CommonAdapter {
    public HomeListAdapter(Context context, ArrayList datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void setView(ViewHolder holder, Object o, int position) {
        GoodsBean bean = (GoodsBean) o;
        SimpleDraweeView simpleDraweeView = holder.getView(R.id.imgv_list);
        TextView tv = holder.getView(R.id.tv_list_item);
        TextView tv_price = holder.getView(R.id.tv_price);
        TextView tv_add = holder.getView(R.id.tv_add);
        TextView tv_1 = holder.getView(R.id.tv_1);
        TextView tv_2 = holder.getView(R.id.tv_2);
        TextView tv_3 = holder.getView(R.id.tv_3);

        tv_1.setText("广告：" +bean.getDbz());
        tv_2.setText("分类：" +bean.getKll());
        tv_3.setText("产地：" +bean.getZf());
        tv.setText(bean.getGoods_name());
        tv_price.setText(bean.getGoods_price() + "");
        simpleDraweeView.setActualImageResource(bean.getGoods_pic());

        if (UserManager.getUserType(getAdapterContext()) == 1) {
            tv_add.setVisibility(View.GONE);
        } else {
            tv_add.setVisibility(View.VISIBLE);
        }
    holder.getView(R.id.layout).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, GoodsinfoActivity.class);
                    intent.putExtra("bean",bean);
            context.startActivity(intent);

        }
    });



        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果购物车有该商品，则增加一条，如果没有则添加商品

                //添加到数据库
                ShopCarBean shopCarBean = new ShopCarBean();
                shopCarBean.setGoods_id(bean.getGoods_id());
                shopCarBean.setGoods_name(bean.getGoods_name());
                shopCarBean.setUser_id(UserManager.getUserId(getAdapterContext()));
                shopCarBean.setGoods_pic(bean.getGoods_pic());
                shopCarBean.setGoods_price(bean.getGoods_price());
                List<ShopCarBean> list_car = DataSupport.findAll(ShopCarBean.class);
                if (list_car.size() == 0) {
                    shopCarBean.setGoods_num(1);
                    shopCarBean.save();
                } else {
                    int position = -1;

                    for (int i = 0; i < list_car.size(); i++) {
                        if (list_car.get(i).getGoods_name().equals(bean.getGoods_name())) {
                            position = i;
                        }
                    }
                    //包含   更新
                    if (position > -1) {
                        ContentValues values = new ContentValues();
                        values.put("goods_num", list_car.get(position).getGoods_num() + 1);
                        DataSupport.updateAll(ShopCarBean.class, values, "goods_name = ?", list_car.get(position).getGoods_name());
                    } else {
                        //不包含  添加
                        shopCarBean.setGoods_num(1);
                        shopCarBean.save();
                    }
                }

                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        //execute the task
                        EventBus.getDefault().post(new EventMessage(EventMessage.ADD));
                        ToastUtil.showToast(context,"已加入购物车");
                    }
                },300);

            }
        });
    }
}
