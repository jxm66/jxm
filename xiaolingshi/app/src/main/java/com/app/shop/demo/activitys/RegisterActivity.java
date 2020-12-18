package com.app.shop.demo.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.shop.demo.R;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.UserBean;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.inputMobile)
    EditText inputMobile;
    @BindView(R.id.inputpwd)
    EditText inputpwd;
    @BindView(R.id.inputpwd_repeat)
    EditText inputpwdRepeat;
    @BindView(R.id.inputName)
    EditText inputName;
    @BindView(R.id.inputNo)
    EditText inputNo;
    @BindView(R.id.inputbirth)
    EditText inputbirth;
    @BindView(R.id.inputsex)
    EditText inputsex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        tvTitle.setText("注册");
    }

    @OnClick({R.id.imgv_return, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
                break;
            case R.id.tv_register:
                if (StringUtil.isEmpty(inputName.getText().toString())) {
                    ToastUtil.showToast(this, "请输入姓名");
                    return;
                }

                if (StringUtil.isEmpty(inputNo.getText().toString())) {
                    ToastUtil.showToast(this, "请输入用户名");
                    return;
                }


                if (StringUtil.isEmpty(inputbirth.getText().toString())) {
                    ToastUtil.showToast(this, "请输入生日");
                    return;
                }

                if (StringUtil.isEmpty(inputsex.getText().toString())) {
                    ToastUtil.showToast(this, "请输入性别");
                    return;
                }
                if (StringUtil.isEmpty(inputMobile.getText().toString())) {
                    ToastUtil.showToast(this, "请输入手机号");
                    return;
                }

                if (StringUtil.isEmpty(inputpwd.getText().toString())) {
                    ToastUtil.showToast(this, "请输入密码");
                    return;
                }

                if (StringUtil.isEmpty(inputpwdRepeat.getText().toString())) {
                    ToastUtil.showToast(this, "请再次输入密码");
                    return;
                }


                if (!inputpwd.getText().toString().equals(inputpwdRepeat.getText().toString())) {
                    ToastUtil.showToast(this, "两次密码不一致");
                    return;
                }


                UserBean userBean = new UserBean();
                userBean.setMobile(inputMobile.getText().toString());
                userBean.setStudent_num(inputNo.getText().toString());
                userBean.setUser_id(inputNo.getText().toString()); //登录名
                userBean.setBirthday(inputbirth.getText().toString());
                userBean.setSex(inputsex.getText().toString());
                userBean.setName(inputName.getText().toString());
                userBean.setPassword(inputpwd.getText().toString());
                userBean.setType(0);
                userBean.save();

                ToastUtil.showToast(this, "注册成功");

                onBackPressed();

                break;
        }
    }
}
