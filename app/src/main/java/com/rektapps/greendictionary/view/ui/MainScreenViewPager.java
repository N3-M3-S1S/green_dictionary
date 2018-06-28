package com.rektapps.greendictionary.view.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Viewpager with ability to disable swipe page changing
 */
public class MainScreenViewPager extends ViewPager {
    private boolean isPageChangingEnabled;


    public MainScreenViewPager(Context context) {
        super(context);
    }

    public MainScreenViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        isPageChangingEnabled = true;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isPageChangingEnabled && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isPageChangingEnabled && super.onTouchEvent(ev);
    }


    public void setPageChangingEnabled(boolean isPageChangingEnabled) {
        this.isPageChangingEnabled = isPageChangingEnabled;
    }
}
