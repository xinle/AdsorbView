# AdsorbView

[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)

Android 靠边停靠控件 ,可以随意传入希望停靠的View和范围

## Screenshot

![](https://github.com/xinle/AdsorbView/blob/master/Screenshot/screen.gif)

## 使用
- 方式 1

```java

```

- 方式 2. 拷贝Libs工程里面的AdsorbView到自己的工程里面

## 范例

- 方式一 xml创建

```java
mAdsorbView1 = (AdsorbView) findViewById(R.id.adsorb1);

ImageView imageView = new ImageView(this);
imageView.setImageResource(R.drawable.icon);
mAdsorbView1.setContentView(imageView);
```

- 方式二 通过代码直接addView

```java
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
```

- 常用方法

| 方法名                      | 方法作用                           |
|:---------------------------|:----------------------------------|
| setContentView(View view)  | 设置要停靠的View                    |
| setLayoutId(int layoutId)  | 设置要停靠的View的布局Id             |
| setDrapRect(Rect rect)     | 设置停靠的区域 (如果不设置默认选择的是父布局的区域)  |
