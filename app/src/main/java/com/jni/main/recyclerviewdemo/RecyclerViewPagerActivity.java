package com.jni.main.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.gw.swipeback.SwipeBackLayout;

import java.util.HashMap;

public class RecyclerViewPagerActivity extends AppCompatActivity {

//    private SwipeRefreshLayoutCompat mRefreshLayout;
    private SliderLayout mDemoSlider ;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
//        mRefreshLayout= (SwipeRefreshLayoutCompat) findViewById(R.id.swipeRefresh);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_pager);

        SwipeBackLayout mSwipeBackLayout = (SwipeBackLayout) findViewById(R.id.swipeBackLayout);
        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_LEFT);
        mSwipeBackLayout.setMaskAlpha(125);
        mSwipeBackLayout.setSwipeBackFactor(0.5f);
        mSwipeBackLayout.setSwipeBackListener(new SwipeBackLayout.OnSwipeBackListener() {
            @Override
            public void onViewPositionChanged(View mView, float swipeBackFraction, float SWIPE_BACK_FACTOR) {

            }

            @Override
            public void onViewSwipeFinished(View mView, boolean isEnd) {

            }
        });

        HashMap<String,String> urlMaps = new HashMap<>();
        urlMaps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        urlMaps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-1 0.png");
        urlMaps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        urlMaps.put("福利1","http://lorempixel.com/400/100");
        urlMaps.put("福利2","http://lorempixel.com/400/200");
        urlMaps.put("福利3","http://lorempixel.com/400/300");

        for(String name : urlMaps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(name)//描述
                    .image(urlMaps.get(name))//image方法可以传入图片url、资源id、File
                    .setScaleType(BaseSliderView.ScaleType.Fit)//图片缩放类型
                    .setOnSliderClickListener(onSliderClickListener);//图片点击
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);//传入参数
            mDemoSlider.addSlider(textSliderView);//添加一个滑动页面
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);//滑动动画
//        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//默认指示器样式
        mDemoSlider.setCustomIndicator((PagerIndicator)findViewById(R.id.custom_indicator2));//自定义指示器
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());//设置图片描述显示动画
        mDemoSlider.setDuration(4000);//设置滚动时间，也是计时器时间
        mDemoSlider.addOnPageChangeListener(onPageChangeListener);
        mDemoSlider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
//                        mRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
//                        mRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
    }

    //Banner的Item点击监听，也就是ViewPager的某一页点击监听。
    private BaseSliderView.OnSliderClickListener onSliderClickListener=new BaseSliderView.OnSliderClickListener() {
        @Override
        public void onSliderClick(BaseSliderView slider) {
            Toast.makeText(RecyclerViewPagerActivity.this,slider.getBundle().get("extra") + "",
                    Toast.LENGTH_SHORT).show();
        }
    };



    //页面改变监听
    private ViewPagerEx.OnPageChangeListener onPageChangeListener=new ViewPagerEx.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            Log.d("ansen", "Page Changed: " + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };


}
