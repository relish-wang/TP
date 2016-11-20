package cn.studyjamscn.s1.sj120.r3lish.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.studyjamscn.s1.sj120.r3lish.utils.AppLog;
import cn.studyjamscn.s1.sj120.r3lish.utils.OkHttpInvoker;

public abstract class BaseFileRequest<T> extends BaseRequest<T> {

    @NonNull
    protected abstract JSONObject json() throws JSONException;

    @Nullable
    protected abstract byte[] voice();

    @NonNull
    @Override
    public Response<T> getResponse() {
        Response<T> response = new Response<>();

        // 检查url
        String url = BASE_URL + methodName();
        if (TextUtils.isEmpty(url)) {
            AppLog.e("BaseFileRequest", "RequestTask", "InValid url!! url=null or url=\"\"");
            response.errorMessage = "URL错误";
            return response;
        }

        // 检查json
        JSONObject json = null;
        try {
            json = json();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json == null) {
            AppLog.e("BaseFileRequest", "RequestTask", "InValid json!! json=null or package json error");
            response.errorMessage = "发送数据封装错误";
            return response;
        }

        // 发送请求
        String strResponse;
        try {
            strResponse = OkHttpInvoker.postFiles(url, json.toString(), voice());
        } catch (IOException e) {
            e.printStackTrace();
            response.errorMessage = "网络连接错误";
            return response;
        }

        // 解析返回数据
        JSONObject jsonResponse;
        try {
            jsonResponse = new JSONObject(strResponse);

            int resultCode = jsonResponse.getInt("resultCode");
            response.isSuccess = resultCode == 0;
            if (response.isSuccess) { // 获取信息成功
                response.data = parseResponse(jsonResponse);
                return response;
            } else if (resultCode == 3) { // 获取信息失败，session过期

//                // 重新登录
//                if (LoginRequest.reLogin(User.getUsername(), User.getPassword())) { // 重新登录成功
//                    // 重新连接服务
//                    strResponse = OkHttpInvoker.postFiles(url, json.toString(), voice());
//                    jsonResponse = new JSONObject(strResponse);
//                    resultCode = jsonResponse.getInt("resultCode");
//                    response.isSuccess = resultCode == 0;
//                    if (response.isSuccess) { // 重新连接服务并获取信息成功
//                        response.data = parseResponse(jsonResponse);
//                        return response;
//                    } else { // 重新连接服务，但获取信息失败
//                        response.errorMessage = parseErrorMessage(resultCode);
//                        if (response.errorMessage == null) {
//                            response.errorMessage = jsonResponse.getString("resultMessage");
//                        }
//                        return response;
//                    }
//                } else { // 重新登录失败，清空activity堆栈，跳转到登录界面
//                    AppToast.showShort("用户信息失效，请重新登录");
//                    AppContext.clearActivitiesWithout(LoginAty.class.getSimpleName());
//                    response.errorMessage = "用户信息失效，请重新登录";
                return response;
//                }

            } else { // 获取信息失败，其他原因
                response.errorMessage = parseErrorMessage(resultCode);
                if (response.errorMessage == null) {
                    response.errorMessage = jsonResponse.getString("resultMessage");
                }
                return response;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            response.isSuccess = false;
            response.errorMessage = "返回数据解析错误";
            return response;
        } //catch (IOException e) {
//            e.printStackTrace();
//            response.isSuccess = false;
//            response.errorMessage = "网络连接错误";
//            return response;
//        }
    }
}
