package com.jni.main.recyclerviewdemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    private String[] mTitles = new String[]{
            AverageActivity.class.getSimpleName(),
            HostActivity.class.getSimpleName(),
            MixActivity.class.getSimpleName()
    };

    private Class[] mActivities = new Class[]{
            AverageActivity.class,
            HostActivity.class,
            MixActivity.class,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mTitles));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        startActivity(new Intent(this, mActivities[position]));
    }
}
