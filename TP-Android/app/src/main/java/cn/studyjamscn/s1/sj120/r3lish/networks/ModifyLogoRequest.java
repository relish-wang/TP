package cn.studyjamscn.s1.sj120.r3lish.networks;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import cn.studyjamscn.s1.sj120.r3lish.base.BaseJsonRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppUtil;

/**
 * Created by r3lis on 2016/4/4.
 */
public class ModifyLogoRequest extends BaseJsonRequest<Void> {

    String logo;

    public ModifyLogoRequest(Bitmap bitmap) {
        this.logo = AppUtil.bitmap2Str(bitmap);

    }

    @NonNull
    @Override
    protected JSONObject json() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uid",UserEntity.getUid());
        json.put("logo", logo);
        return json;
    }

    @NonNull
    @Override
    protected String methodName() {
        return "index.php/PersonalCenter/LogoUpdate";
    }

    @Override
    protected Void parseResponse(@NonNull JSONObject response) throws JSONException {
        return null;
    }
}
