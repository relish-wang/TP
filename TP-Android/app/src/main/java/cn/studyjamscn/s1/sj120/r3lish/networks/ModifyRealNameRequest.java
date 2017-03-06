package cn.studyjamscn.s1.sj120.r3lish.networks;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import cn.studyjamscn.s1.sj120.r3lish.base.BaseJsonRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;

/**
 * Created by r3lis on 2016/4/4.
 */
public class ModifyRealNameRequest extends BaseJsonRequest<Void> {

    String realName;

    public ModifyRealNameRequest(final String realName) {
        this.realName = realName;

    }

    @NonNull
    @Override
    protected JSONObject json() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uid", UserEntity.getUid());
        json.put("realName", realName);
        return json;
    }

    @NonNull
    @Override
    protected String methodName() {
        return "index.php/PersonalCenter/RealNameUpdate";
    }

    @Override
    protected Void parseResponse(@NonNull JSONObject response) throws JSONException {
        return null;
    }
}
