package cn.studyjamscn.s1.sj120.r3lish.ui.activities.prev;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.studyjamscn.s1.sj120.r3lish.R;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import cn.studyjamscn.s1.sj120.r3lish.base.AppActionBar;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseAty;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;
import cn.studyjamscn.s1.sj120.r3lish.networks.LoginRequest;
import cn.studyjamscn.s1.sj120.r3lish.ui.activities.MainAty;

/**
 * 登陆
 * Created by r3lis on 2016/3/11.
 */
public class LoginAty extends BaseAty {

    @Bind(R.id.etPhone)
    AutoCompleteTextView etPhone;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.tvNewUser)
    TextView tvNewUser;

    @Override
    protected int layoutResId() {
        return R.layout.aty_login;
    }

    @Override
    protected void initActionBar(AppActionBar appActionBar) {
        appActionBar.hide();
    }


    @Override
    protected void initViews() {
        btnLogin.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        tvNewUser.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        etPhone.setText(UserEntity.getPhone());
        etPassword.setText(UserEntity.getPassword());

    }

    @OnFocusChange({R.id.etPhone, R.id.etPassword})
    public void textColorChanged(View v, boolean hasFocus) {
        ((TextView) findViewById(v.getId())).setTextColor(ContextCompat.getColor(this,
                hasFocus ? R.color.et_unfocus_color : R.color.et_focus_color));
    }

    @OnClick(R.id.tvNewUser)
    public void go2Register() {
        goActivity(RegisterAty.class);
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        final String phone = etPhone.getText().toString();
        final String password = etPassword.getText().toString();
        btnLogin.setEnabled(false);
        showLoading(true);
        LoginRequest loginRequest = new LoginRequest(phone, password);
        loginRequest.setOnResponseListener(new BaseRequest.OnResponseListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                UserEntity.setPassword(password);
                UserEntity.setPhone(phone);
                goActivity(MainAty.class);
                showLoading(false);
                btnLogin.setEnabled(true);
                finish();
            }

            @Override
            public void onFail(String message) {
                showMessage(message);
                btnLogin.setEnabled(true);
                showLoading(false);
            }
        });
        loginRequest.request();
    }
}
