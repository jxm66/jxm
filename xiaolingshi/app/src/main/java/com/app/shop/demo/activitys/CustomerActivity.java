package com.app.shop.demo.activitys;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.shop.demo.R;
import com.app.shop.demo.adapters.UserListAdapter;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.UserBean;
import com.app.shop.mylibrary.utils.DialogUtil;
import com.app.shop.mylibrary.widgts.CustomDialog;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.listview)
    ListView listview;

    List<UserBean> list_all = new ArrayList<>();
    UserListAdapter adapter;

    CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {

        //全部客户
        list_all = DataSupport.findAll(UserBean.class);

        adapter = new UserListAdapter(this, (ArrayList) list_all, R.layout.item_user_list);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", list_all.get(position));
                showActivity(CustomerActivity.this, MyCenterActivity.class, bundle);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialog = DialogUtil.showDialog(CustomerActivity.this, dialog, 2, "删除该用户", "确认删除吗？", "取消", "是的", new DialogInterface.OnClickListener() {
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
        DataSupport.deleteAll(UserBean.class, "user_id=?", list_all.get(position).getUser_id());
        list_all.remove(position);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.imgv_return)
    public void onViewClicked() {

        onBackPressed();

    }
}
