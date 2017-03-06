package cn.studyjamscn.s1.sj120.r3lish.networks;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.studyjamscn.s1.sj120.r3lish.base.BaseJsonRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.OrderEntity;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;

/**
 * Created by r3lis on 2016/4/4.
 */
public class GetOrderListRequest extends BaseJsonRequest<ArrayList<OrderEntity>> {

    Integer currentPage;
    Integer pageSize;

    public GetOrderListRequest(Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    @NonNull
    @Override
    protected JSONObject json() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uid", UserEntity.getUid());
        json.put("currentPage",currentPage);
        json.put("pageSize",pageSize);
        return json;
    }

    @NonNull
    @Override
    protected String methodName() {
        return "index.php/Order/OrderListForIndex";
    }

    @Override
    protected ArrayList<OrderEntity> parseResponse(@NonNull JSONObject response)
            throws JSONException {
        ArrayList<OrderEntity> orders = new ArrayList<>();
        JSONArray jsonArray = response.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            OrderEntity order = new OrderEntity();
            order.setId(json.getString("id"));
            order.setStatus(json.getInt("state"));
            order.setLogo(json.getString("logo"));
            order.setName(json.getString("name"));
            order.setSource(json.getString("source"));
            order.setTimeGet(json.getString("timeGet"));
            order.setDestination(json.getString("destination"));
            order.setTimeSend(json.getString("timeSend"));
            order.setCost(json.getInt("cost"));
            orders.add(order);
        }
        return orders;
    }
}
