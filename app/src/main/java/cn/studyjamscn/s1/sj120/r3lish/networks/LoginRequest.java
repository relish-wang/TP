package cn.studyjamscn.s1.sj120.r3lish.networks;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import cn.studyjamscn.s1.sj120.r3lish.base.BaseJsonRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppLog;

/**
 * Created by r3lis on 2016/3/14.
 */
public class LoginRequest extends BaseJsonRequest<Void> {

    String name;
    String pwd;

    public LoginRequest(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    @NonNull
    @Override
    protected JSONObject json() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", name);
        jsonObject.put("password", pwd);
        return jsonObject;
    }

    @NonNull
    @Override
    protected String methodName() {
        return "";
    }

    @Override
    protected Void parseResponse(@NonNull JSONObject response) throws JSONException {
        AppLog.d("LoginRequest", "parseResponse", "response = " + response);
        JSONObject json = response.getJSONObject("data");
        UserEntity.setUid(json.getString("uid"));
        UserEntity.setPhone(json.getString("phone"));
        UserEntity.setName(json.getString("name"));
        UserEntity.setRealName(json.getString("realName"));
        UserEntity.setAddress(json.getString("address"));
        UserEntity.setCost(json.getInt("cost"));
        UserEntity.setLogo(json.getString("logo"));
        return null;
    }
}
