package com.lelive.main;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lelive.adsorbview.AdsorbView;
import com.lelive.adsorbview.main.R;

/**
 * Created by xinle on 17/2/13.
 */

public class MainActivity extends Activity {

    private FrameLayout layout1;
    private RelativeLayout layout2;

    private AdsorbView mAdsorbView1;
    private AdsorbView mAdsorbView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        layout1 = (FrameLayout) findViewById(R.id.layout1);
        layout2 = (RelativeLayout) findViewById(R.id.layout2);

        mAdsorbView1 = (AdsorbView) findViewById(R.id.adsorb1);

        // 方式一通过xml
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.icon);
        mAdsorbView1.setContentView(imageView);

        // 方式二 通过代码直接addView
        mAdsorbView2 = new AdsorbView(this);
        mAdsorbView2.setLayoutId(R.layout.item);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.bottomMargin = 30;
        mAdsorbView2.setLayoutParams(params);
        layout2.addView(mAdsorbView2);
        layout2.post(new Runnable() {
            @Override
            public void run() {
                Rect rect = new Rect();
                layout2.getHitRect(rect);
                mAdsorbView2.setDrapRect(rect);
            }
        });
    }
}
