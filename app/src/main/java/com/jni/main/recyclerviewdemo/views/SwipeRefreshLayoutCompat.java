package com.jni.main.recyclerviewdemo.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by admin on 2017/12/20.
 */

public class SwipeRefreshLayoutCompat extends SwipeRefreshLayout {

    //是否存在左右滑动事件
    private boolean mDragger;
    //记录手指按下的位置
    private float mStartX,mStartY;
    //出发事件的最短距离
    private int mTouchSlop;


    public SwipeRefreshLayoutCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        int action=event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:{
                //记录手指按下的位置
                mStartX=event.getX();
                mStartY=event.getY();
                //初始化左右滑动事件为false
                mDragger=false;
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                //如果左右滑动事件为true,直接返回false不拦截事件
                if (mDragger) {
                    return false;
                }

                //获取当前手指的位置
                float endX=event.getX();
                float endY=event.getY();

                //获取X、Y滑动的距离的绝对值
                float distanceX=Math.abs(endX-mStartX);
                float distanceY=Math.abs(endY-mStartY);

                //如果X州的位移大于Y轴的距离,那么将事件交给其他控件
                if (distanceX > mTouchSlop && distanceX > distanceY){
                    mDragger=true;
                    return false;
                }

                break;
            }
            case MotionEvent.ACTION_UP:{
                break;
            }
            case MotionEvent.ACTION_CANCEL:{
                //初始化左右滑动事件为false
                mDragger=false;
                break;
            }
        }


        return super.onInterceptHoverEvent(event);
    }
}
