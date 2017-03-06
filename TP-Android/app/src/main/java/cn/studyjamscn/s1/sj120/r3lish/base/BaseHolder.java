package cn.studyjamscn.s1.sj120.r3lish.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * viewHolder基础类
 *
 * @author 王鑫
 *         Created by 鑫 on 2015/11/9.
 */
public abstract class BaseHolder extends RecyclerView.ViewHolder {

    public BaseHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
