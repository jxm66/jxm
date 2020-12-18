package com.app.shop.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.shop.demo.activitys.LoginActivity;
import com.app.shop.demo.beans.ShopCarBean;
import com.app.shop.demo.fragments.HomeFragment;
import com.app.shop.demo.fragments.MineFragment;
import com.app.shop.demo.fragments.ShopCarFragment;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.utils.UserManager;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.txt_1)
    TextView txt1;
    @BindView(R.id.txt_2)
    TextView txt2;
    @BindView(R.id.txt_3)
    TextView txt3;
    @BindView(R.id.tv_num_shop)
    TextView tvNumShop;

    Fragment mFragment;
    HomeFragment fragment1;
    ShopCarFragment fragment2;
    MineFragment fragment3;
    @BindView(R.id.rela_shopcar)
    RelativeLayout relaShopcar;

    private int user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeEnabled(false); //主页面取消右划返回功能
        initData();
    }

    private void initData() {

        if (UserManager.isLogin(this)) {
            user_type = UserManager.getUserType(this);  //0学生  1管理员
            if (user_type == 0) {
                relaShopcar.setVisibility(View.VISIBLE);
                initShopCarNum();
            } else {
                relaShopcar.setVisibility(View.GONE);
            }

            initFragment();

        } else {
            showActivity(this, LoginActivity.class);
            finish();
        }
    }

    private void initShopCarNum() {
        List<ShopCarBean> list_all = DataSupport.findAll(ShopCarBean.class);
        List<ShopCarBean> list = new ArrayList<>();
        list.clear();
        for (int i = 0; i < list_all.size(); i++) {
            if (list_all.get(i).getUser_id().equals(UserManager.getUserId(this))) {
                list.add(list_all.get(i));
            }
        }
        if (list.size() > 0) {
            tvNumShop.setVisibility(View.GONE);
            tvNumShop.setText(list.size() + "");
        } else {
            tvNumShop.setVisibility(View.GONE);
        }
    }

    private void initFragment() {
        mFragment = new Fragment();
        if (fragment1 == null) {
            fragment1 = new HomeFragment();
        }
        txt1.setSelected(true);
        txt2.setSelected(false);
        txt3.setSelected(false);
        switchContent(mFragment, fragment1);
    }


    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);

        if (msg.getMessageType() == EventMessage.ADD || msg.getMessageType() == EventMessage.REFRESH || msg.getMessageType() == EventMessage.REMOVE) {
            initShopCarNum();
        }
    }

    /**
     * 更换fragment
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to) {
        if (mFragment != to) {
            mFragment = to;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.fragment_container, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @OnClick({R.id.txt_1, R.id.txt_2, R.id.txt_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_1:
                checkPosition(1);
                break;
            case R.id.txt_2:
                checkPosition(2);

                break;
            case R.id.txt_3:
                checkPosition(3);

                break;
        }
    }


    private void checkPosition(int position) {
        if (position == 1) {
            if (fragment1 == null) {
                fragment1 = new HomeFragment();
            }
            txt1.setSelected(true);
            txt2.setSelected(false);
            txt3.setSelected(false);
            switchContent(mFragment, fragment1);
        } else if (position == 2) {
            if (fragment2 == null) {
                fragment2 = new ShopCarFragment();
            }
            txt1.setSelected(false);
            txt2.setSelected(true);
            txt3.setSelected(false);
            switchContent(mFragment, fragment2);
        } else if (position == 3) {
            if (fragment3 == null) {
                fragment3 = new MineFragment();
            }
            txt1.setSelected(false);
            txt2.setSelected(false);
            txt3.setSelected(true);
            switchContent(mFragment, fragment3);
        }

    }
}
