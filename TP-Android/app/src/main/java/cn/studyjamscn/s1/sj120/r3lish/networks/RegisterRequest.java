package cn.studyjamscn.s1.sj120.r3lish.networks;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import cn.studyjamscn.s1.sj120.r3lish.base.BaseJsonRequest;

/**
 * Created by r3lis on 2016/4/3.
 */
public class RegisterRequest extends BaseJsonRequest<Void> {

    String phone;
    String password;
    String name;
    String realName;

    public RegisterRequest(String phone, String password, String name, String realName) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.realName = realName;
    }

    @NonNull
    @Override
    protected JSONObject json() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("phone", phone);
        json.put("password", password);
        json.put("name", name);
        json.put("realName", realName);
        return json;
    }

    @NonNull
    @Override
    protected String methodName() {
        return "index.php/Register/Register";
    }

    @Override
    protected Void parseResponse(@NonNull JSONObject response) throws JSONException {
        return null;
    }
}
