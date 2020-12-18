package com.app.shop.demo;

import android.app.Application;

import com.app.shop.demo.beans.GoodsBean;
import com.app.shop.demo.beans.OrderBean;
import com.app.shop.mylibrary.utils.AppDir;
import com.app.shop.mylibrary.utils.FrescoUtil;
import com.app.shop.mylibrary.utils.Xutils;

import org.litepal.LitePal;
import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;



public class MyApplication extends Application {

    private static MyApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        if (instance != null) {
            //数据库初始化
            LitePal.initialize(instance);

            //创建应用缓存路径
            AppDir.getInstance(this);

            //fresco初始化
            FrescoUtil.init(instance);
            x.Ext.init(this);
            //是否输出debug日志
            x.Ext.setDebug(BuildConfig.DEBUG);
            //数据库配置
            Xutils.initDbConfiginit();
        }
    }


    public static MyApplication getInstance() {
        return instance;
    }
}
