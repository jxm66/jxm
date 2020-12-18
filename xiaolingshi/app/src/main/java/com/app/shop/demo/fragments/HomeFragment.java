package com.app.shop.demo.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.shop.demo.R;
import com.app.shop.demo.activitys.SearchActivity;
import com.app.shop.demo.adapters.HomeListAdapter;
import com.app.shop.demo.beans.GoodsBean;
import com.app.shop.mylibrary.MyWebActivity;
import com.app.shop.mylibrary.base.BaseFragment;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.utils.UserManager;
import com.app.shop.mylibrary.widgts.ScrollListView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.lv)
    ScrollListView lv;

    @BindView(R.id.spinner)
    Spinner spinner;
    private List<GoodsBean> list = new ArrayList<>();
    private HomeListAdapter adapter;
    private String selTyle = "全部";

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        registerEventBus();
        lv.setFocusable(false);
        lv.setFocusableInTouchMode(false);
        initBanner();
        initList();
        return view;
    }



    //列表
    private void initList() {

        list = DataSupport.findAll(GoodsBean.class);
        if (list != null && list.size() > 0) {
            Logger.e("-----------数据库取数据--list：" + list.size());
            list = DataSupport.findAll(GoodsBean.class);
        } else {
            initData();
        }

        adapter = new HomeListAdapter(getContext(), (ArrayList) list, R.layout.item_home);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.units));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selTyle = getResources().getStringArray(R.array.units)[position];

                List<GoodsBean> temp = DataSupport.findAll(GoodsBean.class);
                list.clear();
                if (selTyle.equals("全部")) {
                    list.addAll(temp);
                } else
                    for (int i = 0; i < temp.size(); i++) {
                        if (temp.get(i).getKll().equals(selTyle)) {
                            list.add(temp.get(i));
                        }
                    }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }


    public void initData() {

        for (int i = 0; i < 6; i++) {
            GoodsBean bean = new GoodsBean();
            bean.setGoods_id(i);
            bean.setGoods_num(1);
            bean.setGoods_price(10 + (i * 5));

            String name = "";
            int pic = 0;
            if (i == 0) {
                name = "鱼趣";
                pic = R.mipmap.pic_tiandian;
                bean.setZf("海南");
                bean.setKll("海鲜");
                bean.setDbz("网红安小鱼干");
            } else if (i == 1) {
                name = "酸酸妞";
                pic = R.mipmap.pic_liangcai;
                bean.setZf("广东");
                bean.setKll("糖果");
                bean.setDbz("酸酸妞糖块，好吃");
            } else if (i == 2) {
                name = "西瓜糖块";
                pic = R.mipmap.pic_recai;
                bean.setZf("上海");
                bean.setKll("糖果");
                bean.setDbz("西瓜糖块好吃好看");
            } else if (i == 3) {
                name = "乐视薯片";
                pic = R.mipmap.pic_zhushi;
                bean.setZf("北京");
                bean.setKll("饼干");
                bean.setDbz("乐视薯片好吃好吃，好好吃");
            } else if (i == 4) {
                name = "可比克薯片";
                pic = R.mipmap.pic_yinliao;
                bean.setZf("北京");
                bean.setKll("饼干");
                bean.setDbz("可比克，可比克，我们的可比克");
            } else if (i == 5) {
                name = "可乐";
                pic = R.mipmap.pic_kele;
                bean.setZf("中国");
                bean.setKll("饮料");
                bean.setDbz("冰爽酷炫可乐");
            }

            bean.setGoods_name(name);
            bean.setGoods_pic(pic);

            list.add(bean);
        }

        DataSupport.saveAll(list);
    }


    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.MODIFY_SELF) {
        } else if (msg.getMessageType() == EventMessage.ADD_GOODS) {
            GoodsBean bean = (GoodsBean) msg.getmObject();
            list.add(0, bean);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 轮播图
     */
    private void initBanner() {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new FresscoImageLoader());
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置允许手势滑动
        banner.setViewPagerIsScroll(true);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);

        List list_banner = new ArrayList();
        list_banner.add(R.mipmap.pic_liangcai);
        list_banner.add(R.mipmap.pic_recai);
        list_banner.add(R.mipmap.pic_zhushi);
        list_banner.add(R.mipmap.pic_tiandian);
        list_banner.add(R.mipmap.pic_yinliao);

        banner.setImages(list_banner);

        //banner设置方法全部调用完毕时最后调用
        banner.start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {

        skipActivity(getActivity(), SearchActivity.class);
    }


    public class FresscoImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            int url = (int) path;
            imageView.setImageResource(url);
        }

        //提供createImageView 方法，方便fresco自定义ImageView
        @Override
        public ImageView createImageView(Context context) {

            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) getLayoutInflater().inflate(R.layout.layout_banner_imageview, null);
//            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
            return simpleDraweeView;
        }
    }


    public void skipWebActivity(String web_url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("url", web_url);
        bundle.putString("title", title);
        skipActivity(getActivity(), MyWebActivity.class, bundle);
    }
}
