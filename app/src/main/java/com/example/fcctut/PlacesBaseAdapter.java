package com.example.fcctut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.annotation.Inherited;

//public class PlacesBaseAdapter extends BaseAdapter {
//    Context context;
//    String listFruit[];
//    //int listImage[];
//    LayoutInflater inflater;
//
//    public PlacesBaseAdapter(Context ctx, String[] fruitList) {
//        this.context = ctx;
//        this.listFruit = fruitList;
//        inflater = LayoutInflater.from(ctx);
//    }
//
//    @Override
//    public int getCount() {
//        return listFruit.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        convertView = inflater.inflate(R.layout.activity_custom_iternary_view, null);
//        TextView txtView = (TextView) convertView.findViewById(R.id.displayPlacesTextview);
//        //ImageView imageView = (ImageView) convertView.findViewById(R.id.);
//        txtView.setText(listFruit[position]);
//        return convertView;
//    }
//
//}
