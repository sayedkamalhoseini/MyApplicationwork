package com.example.kamal.saatzanhamrah;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {
    String[] arrayList=new String[25];
    Activity activity;
    LayoutInflater layoutInflater;
    public SpinnerAdapter(String[] arrayList, Activity activity) {
        this.activity=activity;
        this.arrayList=arrayList;
        this.layoutInflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return arrayList.length;
    }

    @Override
    public Object getItem(int position) {
        return arrayList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(convertView==null)
            view=layoutInflater.inflate(R.layout.content_custum_spinner,null);
        TextView textView=view.findViewById(R.id.item_spinner);
        textView.setText(arrayList[position]);
        return view;
    }
}
