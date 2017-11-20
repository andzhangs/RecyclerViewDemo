package com.jni.main.recyclerviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AverageActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GridLayoutManager gridManager;
    private List<String> mList;
    private HostAdapter mAdapter;
    private Button btnAdditem,btnDeleteitem;
    private TextView etInputId;
    private int i=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
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
                        gridManager.setSpanCount(1);
                    } else if (size > 2 && size <= 10) {
                        gridManager.setSpanCount(2);
                    }
                    mAdapter.notifyDataSetChanged();
                    i++;
                }else {
                    Toast.makeText(AverageActivity.this, "数量够了", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDeleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= Integer.parseInt(etInputId.getText().toString());;
                mList.remove(position);
                int size=mList.size();
                if (size==2){
                    gridManager.setSpanCount(1);
                }else if (size>2 && size<=10){
                    gridManager.setSpanCount(2);
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
            mList.add("第"+(i+1)+"条数据");
        }
    }

    class HostAdapter extends RecyclerView.Adapter<HostAdapter.HostViewHoder>{
        private static final String TAG = "HostAdapter";
        private WindowManager manager;
        private DisplayMetrics metrics;
        @Override
        public HostViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
            HostViewHoder holder=new HostViewHoder(LayoutInflater.from(AverageActivity.this).inflate(R.layout.item,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(HostViewHoder holder, int position) {
            manager= (WindowManager) AverageActivity.this.getSystemService(Context.WINDOW_SERVICE);
            metrics=new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(metrics);

            int size=mList.size();
            if (size==1){
                Log.i(TAG, "onCreateViewHolder: "+1);
                holder.itemView.getLayoutParams().width=metrics.widthPixels;
                holder.itemView.getLayoutParams().height=metrics.heightPixels;
            }else if (size == 2) {
                Log.i(TAG, "onBindViewHolder: 2---------" + position);
                holder.itemView.getLayoutParams().width = metrics.widthPixels;
                holder.itemView.getLayoutParams().height = metrics.heightPixels / 2;
            }else {
                holder.itemView.getLayoutParams().width = metrics.widthPixels / 2;
                if (size >= 3 && size <= 4 ) {
                    Log.i(TAG, "onBindViewHolder: 3~4---------" + position);
                    holder.itemView.getLayoutParams().height = metrics.heightPixels / 2;
                }else if (size >= 5 && size <= 6 ) {
                    Log.i(TAG, "onBindViewHolder: 5~6---------" + position);
                    holder.itemView.getLayoutParams().height = metrics.heightPixels / 3;
                }else if (size >= 7 && size <= 8 ) {
                    Log.i(TAG, "onBindViewHolder: 7~8---------" + position);
                    holder.itemView.getLayoutParams().height = metrics.heightPixels / 4;
                }else if (size >= 9 && size <= 10 ) {
                    Log.i(TAG, "onBindViewHolder: 9~10---------" + position);
                    holder.itemView.getLayoutParams().height = metrics.heightPixels / 5;
                }
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
    }
}
