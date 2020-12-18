package com.app.shop.mylibrary.utils;

import android.content.Context;

import com.app.shop.mylibrary.beans.UserBean;
import com.orhanobut.logger.Logger;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor : 大海
 * 每天进步一点点
 * @time :   2019/12/26
 * @description :
 */


public class UserManager {


    /**
     * 判断用户是否登录
     *
     * @return
     */
    public static boolean isLogin(Context context) {

        return !StringUtil.isEmpty(SharedPreferencesUtil.getData(context, "user", "user_id", ""));

    }


    public static String getUserName(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "name", "");

    }


    public static int getUserType(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "type", 0);

    }


    public static String getUserId(Context context) {
        String id = SharedPreferencesUtil.getData(context, "user", "user_id", "");
        Logger.e("-------id" + id);
        return SharedPreferencesUtil.getData(context, "user", "user_id", "");

    }

    public static String getUserSex(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "sex", "");

    }

    public static String getBirthday(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "birthday", "");

    }

    public static String getMobile(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "mobile", "");

    }

    public static String getPro(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "professional", "");

    }

    public static String getClass(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "grade", "");

    }


    public static String getPassword(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "password", "");

    }


    public static boolean isHaveUser(String id) {

        List<UserBean> list = DataSupport.findAll(UserBean.class);
        List<String> list_id = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                list_id.add(list.get(i).getUser_id());
            }
        }
        return list_id.contains(id);
    }


    public static UserBean getUser(String id) {
        List<UserBean> list = DataSupport.findAll(UserBean.class);

        UserBean bean = new UserBean();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUser_id().equals(id)) {
                bean = list.get(i);
            }
        }
        return bean;
    }

    public static boolean isOk(String name, String pwd) {
        List<UserBean> list = DataSupport.findAll(UserBean.class);
        String password = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUser_id().equals(name)) ;
            password = list.get(i).getPassword();
        }
        return password.equals(pwd);
    }

    public void getUser() {
        List<UserBean> list = DataSupport.findAll(UserBean.class);
    }
}
