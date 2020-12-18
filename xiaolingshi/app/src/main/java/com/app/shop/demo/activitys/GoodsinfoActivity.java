package com.app.shop.demo.activitys;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.app.shop.demo.R;
import com.app.shop.demo.adapters.OrderDetailListAdapter;
import com.app.shop.demo.beans.GoodsBean;
import com.app.shop.demo.beans.OrderBean;
import com.app.shop.demo.beans.OrderItemBean;
import com.app.shop.demo.beans.ShopCarBean;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.utils.UserManager;
import com.app.shop.mylibrary.widgts.ScrollListView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class GoodsinfoActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsinfo);
        ButterKnife.bind(this);
        tvTitle.setText("详情");



        GoodsBean bean = (GoodsBean) getIntent().getSerializableExtra("bean");
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.imgv_list);
        TextView tv = (TextView) findViewById(R.id.tv_list_item);
        TextView tv_price =(TextView) findViewById(R.id.tv_price);
        TextView tv_add =(TextView) findViewById(R.id.tv_add);
        TextView tv_1 =(TextView) findViewById(R.id.tv_1);
        TextView tv_2 = (TextView)findViewById(R.id.tv_2);
        TextView tv_3 = (TextView)findViewById(R.id.tv_3);

        tv_1.setText("广告：" +bean.getDbz());
        tv_2.setText("分类：" +bean.getKll());
        tv_3.setText("产地：" +bean.getZf());
        tv.setText(bean.getGoods_name());
        tv_price.setText(bean.getGoods_price() + "");
        simpleDraweeView.setActualImageResource(bean.getGoods_pic());

        if (UserManager.getUserType(GoodsinfoActivity.this) == 1) {
            tv_add.setVisibility(View.GONE);
        } else {
            tv_add.setVisibility(View.VISIBLE);
        }

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果购物车有该商品，则增加一条，如果没有则添加商品

                //添加到数据库
                ShopCarBean shopCarBean = new ShopCarBean();
                shopCarBean.setGoods_id(bean.getGoods_id());
                shopCarBean.setGoods_name(bean.getGoods_name());
                shopCarBean.setUser_id(UserManager.getUserId(GoodsinfoActivity.this));
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
                        ToastUtil.showToast(GoodsinfoActivity.this,"已加入购物车");
                    }
                },300);

            }
        });
    }



}
