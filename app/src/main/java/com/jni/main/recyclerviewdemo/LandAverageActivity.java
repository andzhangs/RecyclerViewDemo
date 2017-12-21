package com.jni.main.recyclerviewdemo;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LandAverageActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GridLayoutManager gridManager;
    private List<String> mList;
    private HostAdapter mAdapter;
    private Button btnAdditem,btnDeleteitem;
    private TextView etInputId;
    private int i=0;

    private View decorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         /*set it to be no title*/
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*set it to be full screen*/
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_land_average);

        //获取顶层视图
        decorView = getWindow().getDecorView();

    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();

        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        gridManager =new GridLayoutManager(this,1, RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(gridManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new HostAdapter();
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);

        btnAdditem= (Button) findViewById(R.id.btn_additem);
        btnDeleteitem= (Button) findViewById(R.id.btn_deleteitem);
        etInputId= (TextView) findViewById(R.id.et_inputId);

        btnAdditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size()<10) {
                    mList.add("添加的数据" + i);
                    int size = mList.size();
                    if (size == 2) {
                        gridManager.setSpanCount(2);
                    } else if (size >= 5 && size <=6) {
                        gridManager.setSpanCount(3);
                    }else if (size >= 7 && size <= 8) {
                        gridManager.setSpanCount(4);
                    }else if (size >= 9 && size <= 10) {
                        gridManager.setSpanCount(5);
                    }
                    mAdapter.notifyDataSetChanged();
                    i++;
                }else {
                    Toast.makeText(LandAverageActivity.this, "数量够了", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDeleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= Integer.parseInt(etInputId.getText().toString());;
                mList.remove(position);
                int size=mList.size();
                if (size == 2) {
                    gridManager.setSpanCount(2);
                } else if (size >= 5 && size <=6) {
                    gridManager.setSpanCount(3);
                }else if (size >= 7 && size <= 8) {
                    gridManager.setSpanCount(4);
                }else if (size >= 9 && size <= 10) {
                    gridManager.setSpanCount(5);
                }
                mAdapter.notifyDataSetChanged();
                etInputId.setText("");
            }
        });

        initData();
    }

    void initData(){
        mList=new ArrayList<String>();
        for (i = 0; i < 1; i++) {
            mList.add("添加的数据" + i);
        }
    }

    class HostAdapter extends RecyclerView.Adapter<HostAdapter.HostViewHoder>{
        private static final String TAG = "HostAdapter";
        private WindowManager manager;
        private DisplayMetrics metrics;
        @Override
        public HostViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
            HostViewHoder holder=new HostViewHoder(LayoutInflater.from(LandAverageActivity.this).inflate(R.layout.item,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(HostViewHoder holder, int position) {
            manager= (WindowManager) LandAverageActivity.this.getSystemService(Context.WINDOW_SERVICE);
            metrics=new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(metrics);

            int size=mList.size();
            if (size==1){
                Log.i(TAG, "onCreateViewHolder: "+1);
                holder.itemView.getLayoutParams().width=getDpi(LandAverageActivity.this);
                holder.itemView.getLayoutParams().height=metrics.heightPixels;
            }else if (size == 2) {
                Log.i(TAG, "onBindViewHolder: 2---------" + position);
                holder.itemView.getLayoutParams().width = getDpi(LandAverageActivity.this)/ 2;
                holder.itemView.getLayoutParams().height = metrics.heightPixels ;
            }else {
                if (size >= 3 && size <= 4 ) {
                    Log.i(TAG, "onBindViewHolder: 3~4---------" + position);
                    holder.itemView.getLayoutParams().width = getDpi(LandAverageActivity.this) / 2;
                }else if (size >= 5 && size <= 6 ) {
                    Log.i(TAG, "onBindViewHolder: 5~6---------" + position);
                    holder.itemView.getLayoutParams().width = getDpi(LandAverageActivity.this) / 3;
                }else if (size >= 7 && size <= 8 ) {
                    Log.i(TAG, "onBindViewHolder: 7~8---------" + position);
                    holder.itemView.getLayoutParams().width = getDpi(LandAverageActivity.this) / 4;
                }else if (size >= 9 && size <= 10 ) {
                    Log.i(TAG, "onBindViewHolder: 9~10---------" + position);
                    holder.itemView.getLayoutParams().width = getDpi(LandAverageActivity.this) / 5;
                }
                holder.itemView.getLayoutParams().height = metrics.heightPixels / 2;
            }
            holder.tv.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class HostViewHoder extends RecyclerView.ViewHolder{
            TextView tv;

            public HostViewHoder(View itemView) {
                super(itemView);
                tv= (TextView) itemView.findViewById(R.id.title);
            }
        }

        //获取屏幕原始尺寸高度，包括虚拟功能键高度
        public  int getDpi(Context context){
            int dpi = 0;
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
                method.invoke(display, displayMetrics);
                dpi=displayMetrics.heightPixels;
            }catch(Exception e){
                e.printStackTrace();
            }
            return dpi*2;
        }

    }


    @Override
    protected void onStart() {
        //调用配置
        init();
        super.onStart();
    }

    private void init(){
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        //判断当前版本在4.0以上并且存在虚拟按键，否则不做操作
        if (Build.VERSION.SDK_INT < 19 || !checkDeviceHasNavigationBar()) {
            //一定要判断是否存在按键，否则在没有按键的手机调用会影响别的功能。如之前没有考虑到，导致图传全屏变成小屏显示。
            return;
        } else {
            // 获取属性
            decorView.setSystemUiVisibility(flag);
        }
    }

    /**
     * 判断是否存在虚拟按键
     * @return
     */
    public boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        return false;
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
