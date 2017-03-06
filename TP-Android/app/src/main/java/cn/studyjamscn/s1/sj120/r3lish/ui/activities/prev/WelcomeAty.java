package cn.studyjamscn.s1.sj120.r3lish.ui.activities.prev;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.text.TextUtils;

import cn.studyjamscn.s1.sj120.r3lish.R;
import cn.studyjamscn.s1.sj120.r3lish.base.AppActionBar;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseAty;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;
import cn.studyjamscn.s1.sj120.r3lish.networks.LoginRequest;
import cn.studyjamscn.s1.sj120.r3lish.ui.activities.MainAty;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppToast;

/**
 * Created by r3lis on 2016/3/16.
 */
public class WelcomeAty extends BaseAty {
    @Override
    protected int layoutResId() {
        return R.layout.aty_welcome;
    }

    @Override
    protected void initActionBar(AppActionBar appActionBar) {
        appActionBar.hide();
    }

    @Override
    protected void initViews() {
        autoLogin();
    }

    private void autoLogin() {
        if ((!TextUtils.isEmpty(UserEntity.getPhone())) && (!TextUtils.isEmpty(UserEntity.getPassword()))) {
            LoginRequest request = new LoginRequest(UserEntity.getPhone(), UserEntity.getPassword());
            request.setOnResponseListener(new BaseRequest.OnResponseListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    goActivity(MainAty.class);
                    finish();
                }

                @Override
                public void onFail(String message) {
                    goActivity(LoginAty.class);
                    AppToast.showShort("用户信息失效，请重新登陆！");
                    finish();
                }
            });
            request.request();
        } else {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    SystemClock.sleep(2000);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    goActivity(LoginAty.class);
                    finish();
                }
            }.execute();
        }
    }
}
