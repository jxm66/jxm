package com.app.shop.demo.activitys;


import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.shop.demo.R;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.beans.UserBean;
import com.app.shop.mylibrary.interfaces.I_itemSelectedListener;
import com.app.shop.mylibrary.utils.ItemChooseUtil;
import com.app.shop.mylibrary.utils.SharedPreferencesUtil;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.widgts.MyItemView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MyCenterActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_id)
    TextView tv_id;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_mobile)
    EditText edt_mobile;
    @BindView(R.id.item_sex)
    MyItemView item_sex;

    String name;
    String mobile;
    String user_id;
    String birthday;
    String sex;

    private List<String> list_sex;  //性别
    private String[] str_sex = new String[]{"男", "女"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_center);

        ButterKnife.bind(this);


        initData();

        initListener();

    }

    private void initListener() {


    }

    private void initData() {
        UserBean userBean = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userBean = (UserBean) bundle.getSerializable("user");
        }
        //性别
        list_sex = new ArrayList();
        list_sex = Arrays.asList(str_sex);

        if (userBean != null) {
            name = userBean.getName();
            mobile = userBean.getMobile();
            user_id = userBean.getUser_id();
            birthday = userBean.getBirthday();
            sex = userBean.getSex();
        } else {
            name = SharedPreferencesUtil.getData(this, "user", "name", "");
            mobile = SharedPreferencesUtil.getData(this, "user", "mobile", "");
            user_id = SharedPreferencesUtil.getData(this, "user", "user_id", "");
            birthday = SharedPreferencesUtil.getData(this, "user", "birthday", "");
            sex = SharedPreferencesUtil.getData(this, "user", "sex", "");
        }


        tv_id.setText(user_id);
        edt_mobile.setText(mobile);
        edt_name.setText(name);
        item_sex.setRightText(sex);

    }


    @OnClick({R.id.imgv_return, R.id.tv_save, R.id.item_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:

                onBackPressed();
                break;
            case R.id.item_sex:
                ItemChooseUtil.showItemWheel(MyCenterActivity.this, list_sex, "性别", 0, new I_itemSelectedListener() {
                    @Override
                    public void onItemSelected(int currentPosition) {
                        item_sex.setRightText(list_sex.get(currentPosition));
                    }
                });

                break;

            case R.id.tv_save:

                if (StringUtil.isEmpty(edt_name.getText().toString())) {
                    ToastUtil.showToast(this, "请输入姓名");
                    return;
                }


                if (StringUtil.isEmpty(edt_mobile.getText().toString())) {
                    ToastUtil.showToast(this, "请输入手机号");
                    return;
                }

                if (StringUtil.isEmpty(item_sex.getRightText())) {
                    ToastUtil.showToast(this, "请选择性别");
                    return;
                }

                SharedPreferencesUtil.saveData(this, "user", "name", edt_name.getText().toString());
                SharedPreferencesUtil.saveData(this, "user", "mobile", edt_mobile.getText().toString());
                SharedPreferencesUtil.saveData(this, "user", "sex", item_sex.getRightText());

                ContentValues values = new ContentValues();
                values.put("name", edt_name.getText().toString());
                values.put("mobile", edt_mobile.getText().toString());
                values.put("sex", item_sex.getRightText());
                DataSupport.updateAll(UserBean.class, values, "user_id=?", user_id);

                EventBus.getDefault().post(new EventMessage(EventMessage.MODIFY_SELF));
                finish();

                break;
        }

    }
}
