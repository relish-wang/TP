package cn.studyjamscn.s1.sj120.r3lish.ui.activities;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.studyjamscn.s1.sj120.r3lish.R;

import butterknife.Bind;
import butterknife.OnClick;
import cn.studyjamscn.s1.sj120.r3lish.App;
import cn.studyjamscn.s1.sj120.r3lish.base.AppActionBar;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseAty;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;
import cn.studyjamscn.s1.sj120.r3lish.networks.LogoutRequest;
import cn.studyjamscn.s1.sj120.r3lish.ui.activities.prev.LoginAty;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppUtil;

/**
 * 个人中心
 * Created by r3lis on 2016/3/31.
 */
public class MineAty extends BaseAty {

    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.ivHead)
    ImageView ivHead;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvSignature)
    TextView tvSignature;

    @Bind(R.id.tvWarmHeartedValue)
    TextView tvWarmHeartedValue;

    @Bind(R.id.tvPhone)
    TextView tvPhone;
    @Bind(R.id.tvRealName)
    TextView tvRealName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;

    @Bind(R.id.btnLogout)
    Button btnLogout;
    @Bind(R.id.btnModify)
    Button btnModify;

    @Override
    protected int layoutResId() {
        return R.layout.aty_mine;
    }

    @Override
    protected void initActionBar(AppActionBar appActionBar) {
        appActionBar.hide();
    }

    @Override
    protected void initViews() {
        tvTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
        tvName.setTextColor(ContextCompat.getColor(this, R.color.white));
        tvSignature.setTextColor(ContextCompat.getColor(this, R.color.et_focus_color));
        btnLogout.setTextColor(ContextCompat.getColor(this, R.color.white));
        btnModify.setTextColor(ContextCompat.getColor(this, R.color.white));
        updateData();
    }

    private void updateData() {
        ivHead.setImageBitmap(AppUtil.toRoundBitmap(UserEntity.getLogo()));
        tvName.setText(UserEntity.getName());
        tvPhone.setText(UserEntity.getPhone());
        tvRealName.setText(UserEntity.getRealName());
        tvAddress.setText(UserEntity.getAddress());
        tvWarmHeartedValue.setText(String.valueOf(UserEntity.getCost()));
    }

    @OnClick({R.id.btnLogout, R.id.btnModify, R.id.rlBack})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlBack:
                finish();
                break;
            case R.id.btnLogout:
                LogoutRequest request = new LogoutRequest();
                request.setOnResponseListener(new BaseRequest.OnResponseListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        App.clearActivities();
                        finish();
                        UserEntity.clear();
                        goActivity(LoginAty.class);
                    }

                    @Override
                    public void onFail(String message) {
                        App.clearActivities();
                        finish();
                        UserEntity.clear();
                        goActivity(LoginAty.class);
                    }
                });
                request.request();
                break;
            case R.id.btnModify:
                goActivityForResult(ModifyMineAty.class, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateData();
    }

    @Override
    public void finish() {
        setResult(0);
        super.finish();
    }
}
