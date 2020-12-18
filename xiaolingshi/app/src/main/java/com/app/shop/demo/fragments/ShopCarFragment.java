package com.app.shop.demo.fragments;


import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.provider.BlockedNumberContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.shop.demo.MyApplication;
import com.app.shop.demo.R;
import com.app.shop.demo.activitys.OrderDetailActivity;
import com.app.shop.demo.beans.GoodsBean;
import com.app.shop.demo.beans.OrderBean;
import com.app.shop.demo.beans.OrderItemBean;
import com.app.shop.demo.beans.ShopCarBean;
import com.app.shop.mylibrary.base.BaseFragment;
import com.app.shop.mylibrary.base.CommonAdapter;
import com.app.shop.mylibrary.base.ViewHolder;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.interfaces.I_NumChangeListener;
import com.app.shop.mylibrary.utils.DialogUtil;
import com.app.shop.mylibrary.utils.SharedPreferencesUtil;
import com.app.shop.mylibrary.utils.TimeUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.utils.UserManager;
import com.app.shop.mylibrary.widgts.AddPlusView;
import com.app.shop.mylibrary.widgts.CustomDialog;
import com.app.shop.mylibrary.widgts.ScrollListView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.litepal.crud.DataSupport;

import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCarFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv)
    ScrollListView lv;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.rela_empty)
    RelativeLayout relaEmpty;

    ShopCarListAdapter adapter;
    private List<ShopCarBean> list = new ArrayList<>();
    private double price_total;

    private String dialog_title = "提交订单";
    private String dialog_content = "是否确定提交订单？";
    private CustomDialog customDialog;

    public ShopCarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_car, container, false);
        ButterKnife.bind(this, view);
        registerEventBus();

        initView();
        return view;
    }


    private void initView() {

        getData();

        adapter = new ShopCarListAdapter(getContext(), (ArrayList) list, R.layout.item_shop_car);
        lv.setAdapter(adapter);

        if (list.size() > 0) {
            lv.setVisibility(View.VISIBLE);
            relaEmpty.setVisibility(View.GONE);
        } else {
            lv.setVisibility(View.GONE);
            relaEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void getData() {
        list.clear();
        List<ShopCarBean> list_all = DataSupport.findAll(ShopCarBean.class);
        for (int i = 0; i < list_all.size(); i++) {
            if (list_all.get(i).getUser_id().equals(UserManager.getUserId(getContext()))) {
                list.add(list_all.get(i));
            }
        }

        setPrice(list);
    }

    private void setPrice(List<ShopCarBean> beanList) {
        price_total = 0;
        for (int i = 0; i < beanList.size(); i++) {
            price_total += (beanList.get(i).getGoods_price() * beanList.get(i).getGoods_num());
        }

        tvTotalPrice.setText("￥" + price_total);
    }

    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.REFRESH) {
            getData();
            adapter.notifyDataSetChanged();
            if (list.size() > 0) {
                lv.setVisibility(View.VISIBLE);
                relaEmpty.setVisibility(View.GONE);
            } else {
                lv.setVisibility(View.GONE);
                relaEmpty.setVisibility(View.VISIBLE);
            }
        } else if (msg.getMessageType() == EventMessage.REMOVE) {
            getData();
            adapter.notifyDataSetChanged();
        } else if (msg.getMessageType() == EventMessage.ADD) {
            getData();
            adapter.notifyDataSetChanged();
            if (list.size() > 0) {
                lv.setVisibility(View.VISIBLE);
                relaEmpty.setVisibility(View.GONE);
            } else {
                lv.setVisibility(View.GONE);
                relaEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {

        if (!UserManager.isLogin(getContext())) {
            ToastUtil.showToast(getContext(), "请先登录");
            return;
        }

        if (list.size() == 0) {
            ToastUtil.showToast(getContext(), "您还没有添加商品");
            return;
        }
        customDialog = DialogUtil.showDialog(getActivity(), customDialog, 2, dialog_title, dialog_content, "取消", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                OrderBean orderBean = new OrderBean();
                orderBean.setOrder_time(TimeUtil.getTodayData("yyyy-MM-dd HH-mm-ss"));
                orderBean.setUser_id(SharedPreferencesUtil.getData(getContext(), "user", "user_id", ""));
                orderBean.setUser_name(SharedPreferencesUtil.getData(getContext(), "user", "name", ""));
                orderBean.setGrade(SharedPreferencesUtil.getData(getContext(), "user", "grade", ""));

                List<OrderItemBean> list_item = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    OrderItemBean bean = new OrderItemBean();
                    bean.setUser_id(SharedPreferencesUtil.getData(getContext(), "user", "user_id", ""));
                    bean.setGoods_name(list.get(i).getGoods_name());
                    bean.setGoods_pic(list.get(i).getGoods_pic());
                    bean.setGoods_num(list.get(i).getGoods_num());
                    bean.setGoods_price(list.get(i).getGoods_price());
                    bean.setGoods_type(list.get(i).getGoods_type());
                    bean.setTime(TimeUtil.getTodayData("yyyy-MM-dd HH-mm-ss"));
                    bean.save();

                    list_item.add(bean);
                }

                orderBean.save();

                orderBean.setOrderItemBeans(list_item);
                //进入订单详情

                Bundle bundle = new Bundle();
                bundle.putSerializable("orderBean", orderBean);
                skipActivity(getActivity(), OrderDetailActivity.class, bundle);


                list.clear();
                adapter.notifyDataSetChanged();
                DataSupport.deleteAll(ShopCarBean.class, "user_id=?", SharedPreferencesUtil.getData(getContext(), "user", "user_id", ""));
                EventBus.getDefault().post(new EventMessage(EventMessage.REFRESH));
            }
        });

        if (customDialog != null && !customDialog.isShowing()) {
            customDialog.show();
        }


        customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                customDialog = null;
            }
        });
    }

    class ShopCarListAdapter extends CommonAdapter {
        public ShopCarListAdapter(Context context, ArrayList datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void setView(ViewHolder holder, Object o, int position) {
            ShopCarBean bean = (ShopCarBean) o;
            SimpleDraweeView simpleDraweeView = holder.getView(R.id.imgv_list);
            TextView tv = holder.getView(R.id.tv_list_item);
            TextView tv_price = holder.getView(R.id.tv_price);
            TextView tv_remove = holder.getView(R.id.tv_remove);
            AddPlusView plusView = holder.getView(R.id.add_plus_num);

            plusView.setDefultNum(bean.getGoods_num());
            plusView.setChoiceNum(bean.getGoods_num());
            simpleDraweeView.setImageResource(bean.getGoods_pic());
            tv.setText(bean.getGoods_name());
            tv_price.setText("￥" + bean.getGoods_price());


            tv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataSupport.deleteAll(ShopCarBean.class, "goods_name=?", bean.getGoods_name());
                    EventBus.getDefault().post(new EventMessage(EventMessage.REMOVE, bean));
                }
            });

            plusView.setOnEditListener(new I_NumChangeListener() {
                @Override
                public void onNumAddListener(int num) {
                    bean.setGoods_num(num);
                    ContentValues values = new ContentValues();
                    values.put("goods_num", num);
                    DataSupport.updateAll(ShopCarBean.class, values, "goods_id = ?", bean.getGoods_id()+"");
                    new Handler().postDelayed(new Runnable(){
                        public void run(){
                            //execute the task
                            EventBus.getDefault().post(new EventMessage(EventMessage.REFRESH, bean, num));
                        }
                    },300);

                }

                @Override
                public void onNumSubtractListener(int num) {
                    bean.setGoods_num(num);
                    ContentValues values = new ContentValues();
                    values.put("goods_num", num);
                    DataSupport.updateAll(ShopCarBean.class, values, "goods_id = ?", bean.getGoods_id()+"");
                    new Handler().postDelayed(new Runnable(){
                        public void run(){
                            //execute the task
                            EventBus.getDefault().post(new EventMessage(EventMessage.REFRESH, bean, num));
                        }
                    },300);

                }
            });

        }
    }

}
