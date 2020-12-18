package com.app.shop.mylibrary;

import android.app.Activity;
import android.app.Application;

import com.app.shop.mylibrary.utils.Xutils;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.LinkedList;
import java.util.List;

/**
 * @anthor : 大海
 * 每天进步一点点
 * @time :   2019/12/25
 * @description :
 */


public class MyApplication extends Application {

    private List<Activity> activityList = new LinkedList<Activity>();
    private static volatile MyApplication instance;


    //私有构造方法
    private MyApplication() {

    }

    //单例模式获取唯一的MyApplication
    public static MyApplication getInstance() {
        if (null == instance) {
            synchronized (MyApplication.class) {
                if (null == instance) {
                    instance = new MyApplication();
                }
            }
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //是否输出debug日志
        x.Ext.setDebug(BuildConfig.DEBUG);
        //数据库配置
        Xutils.initDbConfiginit();


    }

    //添加activity到容器中
    public void addActivity(Activity aty) {
        activityList.add(aty);
    }


    //退出时关闭所有的activity
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }


    public void deleteActivity(Activity aty) {
        activityList.remove(aty);
    }
}
