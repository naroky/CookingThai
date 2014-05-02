package com.naroky.cookingthai;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

public class ListFoodAdapter extends BaseAdapter{

	ArrayList<Object> itemList;

    public Activity context;
    public LayoutInflater inflater;

    public ListFoodAdapter(Activity context,ArrayList<Object> itemList) {
        super();

        this.context = context;
        this.itemList = itemList;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder
    {
        ImageView imgViewLogo;
        TextView txtViewTitle;
        //TextView txtViewDescription;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;
        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listmenu , null);

            //holder.imgViewLogo = (ImageView) convertView.findViewById(R.id.thumb_image);
            holder.txtViewTitle = (TextView) convertView.findViewById(R.id.menutitle_textView);
            //holder.txtViewDescription = (TextView) convertView.findViewById(R.id.detail_textView);
            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();

        itemBean bean = (itemBean) itemList.get(position);
/*
        holder.imgViewLogo.setImageResource(bean.getImage());
 
        ImageLoader imgLoader = new ImageLoader(this.context);
        int loader = R.drawable.thumb_default_2; 
        imgLoader.DisplayImage(bean.getImage(), loader, holder.imgViewLogo);
*/
        holder.txtViewTitle.setText(bean.getTitle());
        //holder.txtViewDescription.setText(bean.getDescription());

        return convertView;
    }

}