package com.jni.main.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by admin on 2017/11/20.
 */

public class MultipleLayoutAdapter extends RecyclerView.Adapter<MultipleLayoutAdapter.LayoutViewHoder> {

    private Context mContext;
    private List<String> mList;
    private boolean isHostLayout;
    private GridLayoutManager gridManager;
    private WindowManager manager;
    private DisplayMetrics metrics;

    public MultipleLayoutAdapter(Context context, List<String> list, boolean isHostLayout, GridLayoutManager gridManager) {
        mContext = context;
        mList = list;
        this.isHostLayout = isHostLayout;
        this.gridManager = gridManager;
    }

    @Override
    public LayoutViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LayoutViewHoder(LayoutInflater.from(mContext).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(LayoutViewHoder holder,final int position) {
        manager= (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
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
            Log.i(TAG, "isHostLayout: "+isHostLayout);
            if (isHostLayout){
                if (size == 3) {
                    Log.i(TAG, "onBindViewHolder: 3---------" + position);
                    if (position ==0){
                        holder.itemView.getLayoutParams().width = metrics.widthPixels;
                    }else {
                        holder.itemView.getLayoutParams().width = metrics.widthPixels / 2 ;
                    }
                    holder.itemView.getLayoutParams().height = metrics.heightPixels / 2;
                }else if (size>=4 && size<=5) {
                    Log.i(TAG, "onBindViewHolder: 4~5---------"+position);
                    if (position == 0){
                        holder.itemView.getLayoutParams().width = metrics.widthPixels;
                        holder.itemView.getLayoutParams().height = metrics.heightPixels / 2;
                    }else {
                        holder.itemView.getLayoutParams().width = metrics.widthPixels / 2;
                        holder.itemView.getLayoutParams().height = metrics.heightPixels / 4;
                    }
                }else if (size>=6 && size<=7) {
                    Log.i(TAG, "onBindViewHolder: 6~10---------" + position);
                    if (position == 0){
                        holder.itemView.getLayoutParams().width = metrics.widthPixels;
                        holder.itemView.getLayoutParams().height = metrics.heightPixels / 2;
                    }else {
                        holder.itemView.getLayoutParams().width = metrics.widthPixels / 3;
                        holder.itemView.getLayoutParams().height = metrics.heightPixels / 4;
                    }
                }else if (size>=8 && size<=10) {
                    Log.i(TAG, "onBindViewHolder: 6~10---------" + position);
                    if (position == 0){
                        holder.itemView.getLayoutParams().width = metrics.widthPixels;
                        holder.itemView.getLayoutParams().height = metrics.heightPixels / 2;
                    }else {
                        holder.itemView.getLayoutParams().width = metrics.widthPixels / 3;
                        holder.itemView.getLayoutParams().height = metrics.heightPixels / 6;
                    }
                }
                holder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position !=0) {
                            String a=mList.get(0);
                            String b=mList.get(position);
                            mList.set(position,a);
                            mList.set(0,b);
                            notifyDataSetChanged();
                        }
                    }
                });
            }else {
                holder.itemView.getLayoutParams().width = metrics.widthPixels / 2;
                if (size >= 3 && size <= 4) {
                    Log.i(TAG, "onBindViewHolder: 3~4---------" + position);
                    holder.itemView.getLayoutParams().height = metrics.heightPixels / 2;
                } else if (size >= 5 && size <= 6) {
                    Log.i(TAG, "onBindViewHolder: 5~6---------" + position);
                    holder.itemView.getLayoutParams().height = metrics.heightPixels / 3;
                } else if (size >= 7 && size <= 8) {
                    Log.i(TAG, "onBindViewHolder: 7~8---------" + position);
                    holder.itemView.getLayoutParams().height = metrics.heightPixels / 4;
                } else if (size >= 9 && size <= 10) {
                    Log.i(TAG, "onBindViewHolder: 9~10---------" + position);
                    holder.itemView.getLayoutParams().height = metrics.heightPixels / 5;
                }
            }
        }
        holder.tv.setText(mList.get(position));
    }

    @Override
    public int getItemCount() { return mList.size(); }

    //切换布局
    public void switchLayout(){
        int size = mList.size();
        isHostLayout = !isHostLayout;
        if (isHostLayout) {
            if (size == 2) {
                gridManager.setSpanCount(1);
            } else if (size > 2 && size <= 5) {
                gridManager.setSpanCount(2);
            } else if (size > 5 && size <= 10) {
                gridManager.setSpanCount(3);
            }
            Log.i(TAG, "切换布局: 主讲");
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0) {
                        Log.i(TAG, "getSpanSize: "+gridManager.getSpanCount());
                        return gridManager.getSpanCount();  // 独占一行：表示获取当前行的列数
                    } else {
                        return 1;  //只占一行中的一列
                    }
                }
            });
        } else {
            Log.i(TAG, "切换布局: 均等");
            if (size == 2) {
                gridManager.setSpanCount(1);
            } else if (size > 2 && size <= 10) {
                gridManager.setSpanCount(2);
            }
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 1;  //只占一行中的一列
                }
            });
        }
        notifyDataSetChanged();
    }

    //当前布局状态
    public boolean getLayoutStatus(){ return isHostLayout; }

    //添加item
    public void addItem(String str){
        if (mList.size() < 10) {
            mList.add(str);
            int size = mList.size();
            if (isHostLayout) {
                if (size == 2) {
                    gridManager.setSpanCount(1);
                } else if (size > 2 && size <= 5) {
                    gridManager.setSpanCount(2);
                } else if (size > 5 && size <= 10) {
                    gridManager.setSpanCount(3);
                }
            } else {
                if (size == 2) {
                    gridManager.setSpanCount(1);
                } else if (size > 2 && size <= 10) {
                    gridManager.setSpanCount(2);
                }
            }
            notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "数量够了", Toast.LENGTH_SHORT).show();
        }
    }

    //删除item
    public void deleteItem(int position){
        mList.remove(position);
        int size = mList.size();
        if (isHostLayout) {
            if (size == 2) {
                gridManager.setSpanCount(1);
            } else if (size > 2 && size <= 5) {
                gridManager.setSpanCount(2);
            } else if (size > 5 && size <= 10) {
                gridManager.setSpanCount(3);
            }
        } else {
            if (size == 2) {
                gridManager.setSpanCount(1);
            } else if (size > 2 && size <= 10) {
                gridManager.setSpanCount(2);
            }
        }
        notifyDataSetChanged();
    }



    class LayoutViewHoder extends RecyclerView.ViewHolder{
        FrameLayout container;
        TextView tv;

        public LayoutViewHoder(View itemView) {
            super(itemView);
            container= (FrameLayout) itemView.findViewById(R.id.ll_container);
            tv= (TextView) itemView.findViewById(R.id.title);
        }
    }
}
