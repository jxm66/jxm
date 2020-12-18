package com.app.shop.demo.activitys;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.shop.demo.R;
import com.app.shop.demo.adapters.GoodsListAdapter;
import com.app.shop.demo.adapters.UserListAdapter;
import com.app.shop.demo.beans.GoodsBean;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.beans.UserBean;
import com.app.shop.mylibrary.utils.DialogUtil;
import com.app.shop.mylibrary.widgts.CustomDialog;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsManageActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.listview)
    ListView listview;


    List<GoodsBean> list_all = new ArrayList<>();
    GoodsListAdapter adapter;

    CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_manage);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {

        //全部客户
        list_all = DataSupport.findAll(GoodsBean.class);

        adapter = new GoodsListAdapter(this, (ArrayList) list_all, R.layout.item_goods_list);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialog = DialogUtil.showDialog(GoodsManageActivity.this, dialog, 2, "删除", "确认删除吗？", "取消", "是的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delUser(position);
                        dialog.dismiss();
                    }
                });

                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogx) {
                        dialog = null;
                    }
                });
                return false;
            }
        });

    }

    private void delUser(int position) {
        //从数据库删除
        DataSupport.deleteAll(GoodsBean.class, "goods_name=?", list_all.get(position).getGoods_name());
        list_all.remove(position);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);

        if (msg.getMessageType() == EventMessage.ADD_GOODS) {
            GoodsBean bean = (GoodsBean) msg.getmObject();
            list_all.add(0, bean);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.imgv_return, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
                break;
            case R.id.tv_add:
                showActivity(this, GoodsAddActivity.class);
                break;
        }
    }
}
