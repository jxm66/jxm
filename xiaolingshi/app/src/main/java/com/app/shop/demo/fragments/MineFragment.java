package com.app.shop.demo.fragments;


import android.content.DialogInterface;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.shop.demo.R;
import com.app.shop.demo.activitys.CustomerActivity;
import com.app.shop.demo.activitys.GoodsManageActivity;
import com.app.shop.demo.activitys.LoginActivity;
import com.app.shop.demo.activitys.MyCenterActivity;
import com.app.shop.demo.activitys.OrderListActivity;
import com.app.shop.demo.activitys.PassWordResetActivity;
import com.app.shop.mylibrary.base.BaseFragment;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.utils.DialogUtil;
import com.app.shop.mylibrary.utils.SharedPreferencesUtil;
import com.app.shop.mylibrary.utils.UserManager;
import com.app.shop.mylibrary.widgts.CustomDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {


    @BindView(R.id.headImg)
    SimpleDraweeView headImg;
    @BindView(R.id.tv_username_mine)
    TextView tvUsernameMine;
    @BindView(R.id.rela_logout)
    RelativeLayout relaLogout;
    @BindView(R.id.rela_customer)
    RelativeLayout relaCustomer;
    @BindView(R.id.rela_order_manager)
    RelativeLayout relaOrderManager;
    @BindView(R.id.rela_order)
    RelativeLayout relaOrder;
    @BindView(R.id.rela_pwd_reset)
    RelativeLayout relaPwdReset;
    @BindView(R.id.rela_menu)
    RelativeLayout relaMenu;
    private String dialog_title = "退出登录";
    private String dialog_content = "是否确定退出登录？";
    private CustomDialog customDialog;
    int user_type;

    public MineFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, view);
        registerEventBus();
        setSelfView();
        return view;
    }

    private void setSelfView() {
        user_type = UserManager.getUserType(getActivity());
        tvUsernameMine.setText(UserManager.getUserName(getActivity()));
        if (user_type == 0) {
            relaCustomer.setVisibility(View.GONE);
            relaOrderManager.setVisibility(View.GONE);
            relaMenu.setVisibility(View.GONE);
            relaOrder.setVisibility(View.VISIBLE);
        } else {
            relaCustomer.setVisibility(View.VISIBLE);
            relaOrderManager.setVisibility(View.VISIBLE);
            relaMenu.setVisibility(View.VISIBLE);
            relaOrder.setVisibility(View.GONE);
        }
    }


    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.MODIFY_SELF) {
            setSelfView();
        }
    }

    @OnClick({R.id.rela_menu, R.id.rela_customer, R.id.rela_order_manager, R.id.rela_message, R.id.rela_order, R.id.rela_pwd_reset, R.id.rela_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.rela_menu:
                skipActivity(getActivity(), GoodsManageActivity.class);
                break;

            case R.id.rela_customer:
                skipActivity(getActivity(), CustomerActivity.class);
                break;

            case R.id.rela_order_manager:
                skipActivity(getActivity(), OrderListActivity.class);
                break;

            case R.id.rela_message:
                skipActivity(getActivity(), MyCenterActivity.class);
                break;

            case R.id.rela_order:
                skipActivity(getActivity(), OrderListActivity.class);

                break;
            case R.id.rela_pwd_reset:
                skipActivity(getActivity(), PassWordResetActivity.class);
                break;

            case R.id.rela_logout:
                Logout();
                break;
        }
    }

    private void Logout() {
        customDialog = DialogUtil.showDialog(getActivity(), customDialog, 2, dialog_title, dialog_content, "取消", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SharedPreferencesUtil.removeAll(getContext(),"user");
                skipActivity(getActivity(), LoginActivity.class);
                getActivity().finish();
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

    @OnClick()
    public void onViewClicked() {
    }
}
