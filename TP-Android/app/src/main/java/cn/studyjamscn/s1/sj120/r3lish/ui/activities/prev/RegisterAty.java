package cn.studyjamscn.s1.sj120.r3lish.ui.activities.prev;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import cn.studyjamscn.s1.sj120.r3lish.R;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import cn.studyjamscn.s1.sj120.r3lish.base.AppActionBar;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseAty;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;
import cn.studyjamscn.s1.sj120.r3lish.networks.RegisterRequest;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppToast;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppUtil;

/**
 * Created by r3lis on 2016/3/14.
 */
public class RegisterAty extends BaseAty implements View.OnFocusChangeListener {


    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etRepeatPwd)
    EditText etRepeatPwd;
    @Bind(R.id.etRealName)
    EditText etRealName;
    String name, phone, realName, password;


    @Override
    protected int layoutResId() {
        return R.layout.aty_register;
    }

    @Override
    protected void initActionBar(AppActionBar appActionBar) {
        appActionBar.setTitleText("注册");
    }

    @Override
    protected void initViews() {
    }

    @OnFocusChange({R.id.etPhone, R.id.etPassword, R.id.etRepeatPwd, R.id.etRealName, R.id.etName})
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        ((EditText) findViewById(v.getId())).setTextColor(ContextCompat.getColor(this,
                hasFocus ? R.color.et_unfocus_color : R.color.et_focus_color));
    }

    /**
     * 注册
     */
    @OnClick(R.id.btnRegister)
    public void register() {
        phone = etPhone.getText().toString();
        realName = etRealName.getText().toString();
        name = etName.getText().toString();
        password = etPassword.getText().toString();
        String repeatPwd = etRepeatPwd.getText().toString();
        if (!AppUtil.isPhoneFormatFixed(phone)) {
            etPhone.setError("手机号不合法！");
            return;
        }
        if (!AppUtil.isNameChinese(realName)) {
            etRealName.setError("请输入真实姓名！");
            return;
        }
        if (password.length() < 6) {
            etPassword.setError("密码长度不得少于6位！");
            return;
        }
        if (password.length() > 20) {
            etPassword.setError("密码长度不得超过20位！");
            return;
        }
        if (!TextUtils.equals(password, repeatPwd)) {
            etRepeatPwd.setError("两次密码输入不一致！");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            name = "用户" + System.currentTimeMillis();
        }
        showLoading(true);
        RegisterRequest registerRequest = new RegisterRequest(phone, password, name, realName);
        registerRequest.setOnResponseListener(new BaseRequest.OnResponseListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                UserEntity.setPhone(phone);
                UserEntity.setName(name);
                UserEntity.setRealName(realName);
//                UserEntity.setPassword(password);
                AppToast.showShort("注册成功");
                goActivity(LoginAty.class);
                finish();
                showLoading(false);
            }

            @Override
            public void onFail(String message) {
                AppToast.showShort(message);
                showLoading(false);
            }
        });
        registerRequest.request();
    }
}
