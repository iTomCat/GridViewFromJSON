package com.tomaszkot.dynamicallyaddedgridlayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class GridViewAdapter extends ArrayAdapter<ListItem> {
    private Context mContext;
    private int resourceId;

    GridViewAdapter(Context context, int layoutResourceId, ArrayList<ListItem> data){
        super(context, layoutResourceId, data);
        this.mContext = context;
        this.resourceId = layoutResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View itemView = convertView;
        ViewHolder holder;
        int lastItem = (getCount()-1);

        if (itemView == null){
            final LayoutInflater layoutInflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(resourceId, parent, false);

            holder = new ViewHolder();
            holder.imgItem = (ImageView) itemView.findViewById(R.id.Icon);
            holder.txtItem = (TextView) itemView.findViewById(R.id.appName);
            itemView.setTag(holder);
        }else{
            holder = (ViewHolder) itemView.getTag();
        }

        // Adding item with plus icon
        String itemName;
        Drawable itemIcon;
        if (position == lastItem){
            itemName = " ";
            itemIcon = getFirstItemIcon();
        }else{ // Adding the remaining components of the list
            ListItem item = getItem(position);
            assert item != null;
            itemName = item.getTitle();
            int itemColor = item.getColor();
            itemIcon = getItemIcon(itemColor);
        }

        holder.txtItem.setText(itemName);
        holder.imgItem.setImageDrawable(itemIcon);
        return itemView;
    }

    private Drawable getFirstItemIcon() {
        return ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.plus_icon, null);
    }

    // --------------------------------------------------------------------------------------------- get the circle drawable and change the color
    private Drawable getItemIcon(int color){
        Drawable circle = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.circle, null);
        GradientDrawable coloredCircle = (GradientDrawable) circle;

        assert coloredCircle != null;
        coloredCircle.setColor(ContextCompat.getColor(mContext, color));
        return coloredCircle;
    }

    private static class ViewHolder{
        ImageView imgItem;
        TextView txtItem;
    }
}
