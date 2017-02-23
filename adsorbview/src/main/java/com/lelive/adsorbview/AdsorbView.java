package com.lelive.adsorbview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * 拖动控件 移除拖动后进行吸附侧边
 */

public class AdsorbView extends FrameLayout {
    public AdsorbView(Context context) {
        super(context);
        initView();
    }

    public AdsorbView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AdsorbView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        drapRect = new Rect(0 , 0 , getResources().getDisplayMetrics().widthPixels , getResources().getDisplayMetrics().heightPixels);
        setClickable(true);

        post(new Runnable() {
            @Override
            public void run() {
                if(getParent() instanceof View) {
                    ((View) getParent()).getHitRect(drapRect);
                }
            }
        });

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private Rect drapRect;  // 可以拖动的区域
    private int mTouchSlop;

    public void setLayoutId(int layoutId) {
        if(layoutId > 0) {
            removeAllViews();
            inflate(getContext() ,layoutId , this);
        }
    }

    public void setContentView(View view) {
        if(view == null) {
            return;
        }

        removeAllViews();
        addView(view);
    }

    public void setDrapRect(Rect rect) {
        this.drapRect = rect;
    }

    Rect rect = new Rect();

    int downX,downY;
    int lastX ,lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getHitRect(rect);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getRawX();
                downY = (int) event.getRawY();
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX() - downX;
                int moveY = (int) event.getRawY() - downY;

                rect.offset(moveX ,moveY);

                setX(rect.left);
                setY(rect.top);

                downX = (int) event.getRawX();
                downY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if(Math.abs(lastX - event.getRawX()) < mTouchSlop && Math.abs(lastY - event.getRawY()) < mTouchSlop) {
                    return super.onTouchEvent(event);
                }

                int x = rect.centerX() - ((drapRect.right - drapRect.left) / 2);
                if(x < 0) {
                    rect.offsetTo(0 , rect.top);
                } else {
                    rect.offsetTo(drapRect.right - drapRect.left - getWidth() , rect.top);
                }

                if(rect.top < 0) {
                    rect.offsetTo(rect.left , 0);
                } else if(rect.bottom - (drapRect.bottom - drapRect.top) > 0) {
                    rect.offsetTo(rect.left , drapRect.bottom - drapRect.top - getHeight());
                }

                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(this ,"x" , getX() , rect.left);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(this ,"y" , getY() , rect.top);
                animatorSet.playTogether(objectAnimator1 ,objectAnimator2);
                animatorSet.start();
                requestDisallowInterceptTouchEvent(false);
                return true;
            case MotionEvent.ACTION_CANCEL:
                requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onTouchEvent(event);
    }
}
