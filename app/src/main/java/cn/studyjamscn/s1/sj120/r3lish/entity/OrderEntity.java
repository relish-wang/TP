package cn.studyjamscn.s1.sj120.r3lish.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import cn.studyjamscn.s1.sj120.r3lish.R;

import cn.studyjamscn.s1.sj120.r3lish.App;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseEntity;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppUtil;

/**
 * Created by r3lis on 2016/4/4.
 */
public class OrderEntity extends BaseEntity {

    String id;
    /**
     * 发单人
     */
    String userA;
    boolean isMore = false;
    /**
     * 接单人
     */
    String userB;
    /**
     * 接单人姓名
     */
    String realNameB;
    /**
     * 0:未接单;
     * 1:已接单;
     * 2:已完成;
     */
    Integer status;
    String name;
    String source;
    String timeGet;
    String destination;
    String timeSend;
    String content;
    Integer cost;

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    Bitmap logo;

    public Bitmap getLogo() {
        if (logo != null) {
            return logo;
        }else {
            return BitmapFactory.decodeResource(App.CONTEXT.getResources(),
                    R.mipmap.logo);
        }
    }

    public void setLogo(String logo) {
        if (logo == null) return;
        setLogo(AppUtil.str2Bitmap(logo));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSource() {
        if (source == null) return "未填写";
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTimeGet() {
        if (timeGet == null) return "未填写";
        return timeGet;
    }

    public void setTimeGet(String timeGet) {
        this.timeGet = timeGet;
    }

    public String getDestination() {
        if (destination == null) return "未填写";
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTimeSend() {
        if (timeSend == null) return "未填写";
        return timeSend;
    }

    public void setTimeSend(String timeSend) {
        this.timeSend = timeSend;
    }

    public String getUserA() {
        if (userA == null) {
            return "";
        }
        return userA;
    }

    public void setUserA(String userA) {
        this.userA = userA;
    }

    public String getUserB() {
        if (userB == null) {
            return "";
        }
        return userB;
    }

    public void setUserB(String userB) {
        this.userB = userB;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setIsMore(boolean isMore) {
        this.isMore = isMore;
    }

    public String getRealNameB() {
        if (TextUtils.isEmpty(realNameB)) {
            return "";
        }
        return realNameB;
    }

    public void setRealNameB(String realNameB) {
        this.realNameB = realNameB;
    }
}
