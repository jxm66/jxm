package com.app.shop.demo.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.shop.demo.R;
import com.app.shop.demo.beans.GoodsBean;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.interfaces.I_itemSelectedListener;
import com.app.shop.mylibrary.utils.ItemChooseUtil;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.widgts.MyItemView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class GoodsAddActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_price)
    EditText edtPrice;


    @BindView(R.id.edt_remark)
    EditText edtRemark;
    @BindView(R.id.imgv_pic)
    SimpleDraweeView imgvPic;
    @BindView(R.id.tv_save)
    TextView tvSave;

    @BindView(R.id.edt_kll)
    EditText edt_kll;
    @BindView(R.id.edt_dbz)
    EditText edt_dbz;
    @BindView(R.id.edt_zf)
    EditText edt_zf;

    private int pic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_add);
        ButterKnife.bind(this);
        initData();
        tvTitle.setText("添加产品");
    }

    private void initData() {





    }

    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.CHOICE_PIC) {
            pic = (int) msg.getmObject();
            imgvPic.setActualImageResource(pic);
        }
    }

    @OnClick({R.id.imgv_return, R.id.imgv_pic, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
                break;

            case R.id.imgv_pic:
                showActivity(this, ChoicePicActivity.class);
                break;
            case R.id.tv_save:

                if (StringUtil.isEmpty(edtName.getText().toString())) {
                    ToastUtil.showToast(this, "请输入名称");
                    return;
                }

                if (StringUtil.isEmpty(edtPrice.getText().toString())) {
                    ToastUtil.showToast(this, "请输入价格");
                    return;
                }

                if (StringUtil.isEmpty(edtRemark.getText().toString())) {
                    ToastUtil.showToast(this, "请输入备注");
                    return;
                }

                if (pic == 0) {
                    ToastUtil.showToast(this, "请选择图片");
                    return;
                }
                if (StringUtil.isEmpty(edt_kll.getText().toString())) {
                    ToastUtil.showToast(this, "请输入分类");
                    return;
                }
                if (StringUtil.isEmpty(edt_dbz.getText().toString())) {
                    ToastUtil.showToast(this, "请输入广告");
                    return;
                }
                if (StringUtil.isEmpty(edt_zf.getText().toString())) {
                    ToastUtil.showToast(this, "请输入产地");
                    return;
                }
                GoodsBean bean = new GoodsBean();
                bean.setGoods_pic(pic);
                bean.setGoods_name(edtName.getText().toString());

                bean.setRemark(edtRemark.getText().toString());
                bean.setDbz(edt_dbz.getText().toString());
                bean.setKll(edt_kll.getText().toString());
                bean.setZf(edt_zf.getText().toString());
                bean.save();

                EventBus.getDefault().post(new EventMessage(EventMessage.ADD_GOODS, bean));
                finish();
                break;
        }
    }
}
