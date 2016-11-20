package cn.studyjamscn.s1.sj120.r3lish.networks;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import cn.studyjamscn.s1.sj120.r3lish.base.BaseJsonRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;

/**
 * Created by r3lis on 2016/4/3.
 */
public class UserDataRequest extends BaseJsonRequest<Void> {

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
        return "index.php/PersonalCenter/UserData";
    }

    @Override
    protected Void parseResponse(@NonNull JSONObject response) throws JSONException {
        JSONObject json = response.getJSONObject("data");
        UserEntity.setPhone(json.getString("phone"));
        UserEntity.setPassword(json.getString("password"));
        UserEntity.setName(json.getString("name"));
        UserEntity.setRealName(json.getString("realName"));
        UserEntity.setAddress(json.getString("address"));
        UserEntity.setLogo(json.getString("logo"));
        UserEntity.setCost(json.getInt("cost"));
        return null;
    }
}
