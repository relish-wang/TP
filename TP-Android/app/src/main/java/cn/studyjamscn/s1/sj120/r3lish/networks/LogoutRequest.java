package cn.studyjamscn.s1.sj120.r3lish.networks;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import cn.studyjamscn.s1.sj120.r3lish.base.BaseJsonRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;

/**
 * 登出
 * Created by r3lis on 2016/4/3.
 */
public class LogoutRequest extends BaseJsonRequest<Void> {
    @NonNull
    @Override
    protected JSONObject json() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uid", UserEntity.uid);
        return json;
    }

    @NonNull
    @Override
    protected String methodName() {
        return "index.php/Welcome/Logout";
    }

    @Override
    protected Void parseResponse(@NonNull JSONObject response) throws JSONException {
        return null;
    }
}
