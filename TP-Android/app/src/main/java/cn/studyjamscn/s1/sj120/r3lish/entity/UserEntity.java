package cn.studyjamscn.s1.sj120.r3lish.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import cn.studyjamscn.s1.sj120.r3lish.R;

import cn.studyjamscn.s1.sj120.r3lish.App;
import cn.studyjamscn.s1.sj120.r3lish.base.BaseEntity;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppPreference;
import cn.studyjamscn.s1.sj120.r3lish.utils.AppUtil;

/**
 * 用户实体
 * Created by r3lis on 2016/3/15.
 */
public class UserEntity extends BaseEntity {


    public static String uid;
    public static String phone;
    public static String name;
    public static String realName;
    public static String password;
    public static String address;
    public static Integer cost;
    public static Bitmap logo;

    public static String getName() {
        if (TextUtils.isEmpty(name)) {
            return AppPreference.getString("name", "");
        }
        return name;
    }

    public static void setName(String name) {
        AppPreference.put("name", name);
        UserEntity.name = name;
    }

    public static String getPassword() {
        if (TextUtils.isEmpty(password)) {
            return AppPreference.getString("pwd", "");
        }
        return password;
    }

    public static void setPassword(String password) {
        AppPreference.put("pwd", password);
        UserEntity.password = password;
    }

    public static String getAddress() {
        if (TextUtils.isEmpty(address)) {
            return "未填写";
        }
        return address;
    }

    public static void setAddress(String address) {
        if(TextUtils.equals(address,"null"))return;
        UserEntity.address = address;
    }

    public static Integer getCost() {
        if (null == cost) {
            return AppPreference.getInt("cost", 0);
        }
        return cost;
    }

    public static void setCost(Integer cost) {
        AppPreference.put("cost", cost);
        UserEntity.cost = cost;
    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        UserEntity.uid = uid;
    }

    public static String getRealName() {
        if (realName == null) {
            return AppPreference.getString("realName", "");
        }
        return realName;
    }

    public static void setRealName(String realName) {
        AppPreference.put("realName", realName);
        UserEntity.realName = realName;
    }

    public static String getPhone() {
        if (phone == null) {
            return AppPreference.getString("phone", "");
        }
        return phone;
    }

    public static void setPhone(String phone) {
        AppPreference.put("phone", phone);
        UserEntity.phone = phone;
    }

    public static Bitmap getLogo() {
        if (logo == null) {
            return BitmapFactory.decodeResource(
                    App.CONTEXT.getResources(),
                    R.mipmap.logo);
        }
        return logo;
    }

    public static void setLogo(Bitmap logo) {
        if (logo == null) return;
        UserEntity.logo = logo;
    }

    public static void setLogo(String head) {
        if (head == null||TextUtils.equals(head,"null")) {
            return;
        }
        setLogo(AppUtil.str2Bitmap(head));
    }

    public static void clear() {
        uid = null;
        phone = null;
        name = null;
        AppPreference.put("name", "");
        realName = null;
        AppPreference.put("realName", "");
        password = null;
        AppPreference.put("pwd", "");
        address = null;
        AppPreference.put("address", "");
        cost = null;
        AppPreference.put("cost", "");
        logo = null;

    }

}
