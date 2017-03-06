package cn.studyjamscn.s1.sj120.r3lish.networks;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import cn.studyjamscn.s1.sj120.r3lish.base.BaseJsonRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;

/**
 * 修改用户名
 * Created by r3lis on 2016/4/4.
 */
public class ModifyNameRequest extends BaseJsonRequest<Void> {

    String name;

    public ModifyNameRequest(final String name) {
        this.name = name;
    }

    @NonNull
    @Override
    protected JSONObject json() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uid", UserEntity.getUid());
        json.put("name",name);
        return json;
    }

    @NonNull
    @Override
    protected String methodName() {
        return "index.php/PersonalCenter/NameUpdate";
    }

    @Override
    protected Void parseResponse(@NonNull JSONObject response) throws JSONException {
        return null;
    }
}