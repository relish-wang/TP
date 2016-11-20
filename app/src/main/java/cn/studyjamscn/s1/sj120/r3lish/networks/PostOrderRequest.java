package cn.studyjamscn.s1.sj120.r3lish.networks;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import cn.studyjamscn.s1.sj120.r3lish.base.BaseJsonRequest;
import cn.studyjamscn.s1.sj120.r3lish.entity.UserEntity;

/**
 * Created by r3lis on 2016/4/4.
 */
public class PostOrderRequest extends BaseJsonRequest<Void> {

    String source;
    String timeGet;
    String destination;
    String timeSend;
    String content;
    Integer cost;

    public PostOrderRequest(String source, String timeGet, String destination,
                            String timeSend, String content, Integer cost) {
        this.source = source;
        this.timeGet = timeGet;
        this.destination = destination;
        this.timeSend = timeSend;
        this.content = content;
        this.cost = cost;
    }

    @NonNull
    @Override
    protected JSONObject json() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uid", UserEntity.getUid());
        json.put("source",source);
        json.put("timeGet",timeGet);
        json.put("destination",destination);
        json.put("timeSend",timeSend);
        json.put("content",content);
        json.put("cost",cost);
        return json;
    }

    @NonNull
    @Override
    protected String methodName() {
        return "index.php/Order/OrderAdd";
    }

    @Override
    protected Void parseResponse(@NonNull JSONObject response) throws JSONException {
        return null;
    }
}
