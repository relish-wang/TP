package cn.studyjamscn.s1.sj120.r3lish.base;

/**
 * Created by r3lis on 2016/3/10.
 */
public interface BaseView {

    void showLoading(boolean shouldLoading);

    void showMessage(String text);

    void update();
}
