package cn.studyjamscn.s1.sj120.r3lish.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不可手动滑动的ViewPager
 *
 * @author 王鑫
 *         Created by 王鑫 on 2015/10/13.
 */
public class NonSlidePager extends ViewPager {
    public NonSlidePager(Context context) {
        super(context);
    }

    public NonSlidePager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
