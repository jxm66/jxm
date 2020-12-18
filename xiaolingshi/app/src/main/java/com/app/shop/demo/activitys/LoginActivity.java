package com.app.shop.demo.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedDispatcherOwner;

import com.app.shop.demo.MainActivity;
import com.app.shop.demo.R;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.beans.UserBean;
import com.app.shop.mylibrary.utils.SharedPreferencesUtil;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.utils.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.inputName)
    EditText inputName;
    @BindView(R.id.inputpwd)
    EditText inputpwd;
    @BindView(R.id.imgv_return)
    ImageView imgvReturn;
    @BindView(R.id.cb_student)
    ImageView cbStudent;
    @BindView(R.id.ll_student)
    LinearLayout llStudent;
    @BindView(R.id.cb_manager)
    ImageView cbManager;
    @BindView(R.id.ll_manager)
    LinearLayout llManager;
    @BindView(R.id.toLogin)
    TextView toLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    private int isManager = 0;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        imgvReturn.setVisibility(View.GONE);
        tvTitle.setText("登录");
    }

    @OnClick({R.id.imgv_return, R.id.toLogin, R.id.tv_register, R.id.ll_student, R.id.ll_manager})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_student:
                isManager = 0;
                cbStudent.setImageResource(R.mipmap.icon_checkbox_checked_haibao);
                cbManager.setImageResource(R.mipmap.icon_checkbox_uncheck_haibao);
                tvRegister.setVisibility(View.VISIBLE);

                break;

            case R.id.ll_manager:
                isManager = 1;
                cbStudent.setImageResource(R.mipmap.icon_checkbox_uncheck_haibao);
                cbManager.setImageResource(R.mipmap.icon_checkbox_checked_haibao);
                tvRegister.setVisibility(View.GONE);

                break;

            case R.id.toLogin:

                if (StringUtil.isEmpty(inputName.getText().toString())) {
                    ToastUtil.showToast(this, "请输入用户名");
                    return;
                }

                if (StringUtil.isEmpty(inputpwd.getText().toString())) {
                    ToastUtil.showToast(this, "请输入密码");
                    return;
                }


                if (isManager == 0) { //普通学生
                    boolean isHaveUser = UserManager.isHaveUser(inputName.getText().toString());
                    if (isHaveUser) {//有该用户
                        if (UserManager.isOk(inputName.getText().toString(), inputpwd.getText().toString())) { //密码正确
                            UserBean userBean = UserManager.getUser(inputName.getText().toString());
                            userBean.setType(0);
                            SharedPreferencesUtil.saveDataBean(this, userBean, "user");
                            EventBus.getDefault().post(new EventMessage(EventMessage.LOGIN));
                            showActivity(this, MainActivity.class);
                            finish();
                        } else {
                            ToastUtil.showToast(this, "密码不匹配");
                        }

                    } else {
                        ToastUtil.showToast(this, "无此用户，请先注册");
                    }
                } else { //管理员

                    //这里的账号密码判断你自己来完善
                    UserBean userBean = new UserBean();
                    userBean.setName("管理员");
                    userBean.setId(1);
                    userBean.setUser_id("1");
                    userBean.setType(1);
                    SharedPreferencesUtil.saveDataBean(this, userBean, "user");
                    EventBus.getDefault().post(new EventMessage(EventMessage.LOGIN));
                    showActivity(this, MainActivity.class);
                    finish();
                }


                break;

            case R.id.tv_register:
                showActivity(this, RegisterActivity.class);
                break;
        }
    }

}
