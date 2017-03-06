package cn.studyjamscn.s1.sj120.r3lish.networks;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import cn.studyjamscn.s1.sj120.r3lish.base.BaseJsonRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppToast;

/**
 * Created by r3lis on 2016/4/4.
 */
public class ModifyAddressRequest extends BaseJsonRequest<Void> {

    String address;

    public ModifyAddressRequest(final String address) {
        this.address = address;
        this.setOnResponseListener(new OnResponseListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                UserEntity.setAddress(address);
            }

            @Override
            public void onFail(String message) {
                AppToast.showShort(message);
            }
        });
    }

    @NonNull
    @Override
    protected JSONObject json() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uid", UserEntity.getUid());
        json.put("address", address);
        return json;
    }

    @NonNull
    @Override
    protected String methodName() {
        return "index.php/PersonalCenter/AddressUpdate";
    }

    @Override
    protected Void parseResponse(@NonNull JSONObject response) throws JSONException {
        return null;
    }
}
