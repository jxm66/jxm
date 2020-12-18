package com.app.shop.demo.adapters;

import android.content.Context;
import android.widget.TextView;

import com.app.shop.demo.R;
import com.app.shop.mylibrary.base.CommonAdapter;
import com.app.shop.mylibrary.base.ViewHolder;
import com.app.shop.mylibrary.beans.UserBean;

import java.util.ArrayList;



public class UserListAdapter extends CommonAdapter {

    public UserListAdapter(Context context, ArrayList datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void setView(ViewHolder holder, Object o, int position) {
        UserBean bean = (UserBean) o;
        TextView textView = holder.getView(R.id.tv_list_item);
        textView.setText(bean.getName());
    }
}
