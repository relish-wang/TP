package cn.studyjamscn.s1.sj120.r3lish.networks;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import cn.studyjamscn.s1.sj120.r3lish.base.BaseJsonRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.OrderEntity;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;

/**
 * Created by r3lis on 2016/4/4.
 */
public class GetOrderDetailRequest extends BaseJsonRequest<OrderEntity> {

    String id;

    public GetOrderDetailRequest(String id) {
        this.id = id;
    }

    @NonNull
    @Override
    protected JSONObject json() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uid", UserEntity.getUid());
        json.put("id",id);
        return json;
    }

    @NonNull
    @Override
    protected String methodName() {
        return "index.php/Order/OrderDetail";
    }

    @Override
    protected OrderEntity parseResponse(@NonNull JSONObject response) throws JSONException {
        JSONObject json = response.getJSONObject("data");
        OrderEntity order = new OrderEntity();
        order.setId(json.getString("id"));
        order.setUserA(json.getString("userA"));
        order.setUserB(json.getString("userB"));
        order.setRealNameB(json.getString("realName"));
        order.setStatus(json.getInt("state"));
        order.setSource(json.getString("source"));
        order.setTimeGet(json.getString("timeGet"));
        order.setDestination(json.getString("destination"));
        order.setTimeSend(json.getString("timeSend"));
        order.setContent(json.getString("content"));
        order.setCost(json.getInt("cost"));
        return order;
    }
}
