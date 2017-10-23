package com.tomaszkot.dynamicallyaddedgridlayout.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.GridView;

import com.tomaszkot.dynamicallyaddedgridlayout.R;

import java.util.ArrayList;

public class DialogSelectColor extends DialogFragment {
    private View view;
    public ArrayList<ColorItem> colorList = new ArrayList<>();
    private DialogInterface.OnDismissListener onDismissListener;
    public static int selectedColor = R.color.color_3; // default color;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Dialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_color);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        ViewGroup container = (ViewGroup) getView();
        view = inflater.inflate(R.layout.dialog_select_color, container, false);

        makeColorList();
        initializeGridView();

        // ----------------------------------------------------------------------------------------- Add action buttons
        builder.setView(view);
        return builder.create();
    }

    // --------------------------------------------------------------------------------------------- Initialize GridView Components
    private void initializeGridView(){
        android.widget.GridView gridview = (GridView) view.findViewById(R.id.colors_grid);
        gridview.setAdapter(new ColorAdapter(getActivity()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                selectedColor = (colorList.get(position).getColor());

                // Close this Dialog
                DialogSelectColor.this.getDialog().cancel();
            }
        });
    }

    // --------------------------------------------------------------------------------------------- List of colors
    class ColorItem {
        private int color;

        // Constructor
        ColorItem(int color) {
            this.color = color;
        }

        int getColor() {
            return color;
        }

        void setColor(int color) {
            this.color = color;
        }
    }

    private void makeColorList(){
        colorList.add(new ColorItem(R.color.color_1));
        colorList.add(new ColorItem(R.color.color_2));
        colorList.add(new ColorItem(R.color.color_3));
        colorList.add(new ColorItem(R.color.color_4));
        colorList.add(new ColorItem(R.color.color_5));
        colorList.add(new ColorItem(R.color.color_6));
        colorList.add(new ColorItem(R.color.color_7));
        colorList.add(new ColorItem(R.color.color_8));
        colorList.add(new ColorItem(R.color.color_9));
    }

    // --------------------------------------------------------------------------------------------- List Adapter Class
    public class ColorAdapter extends BaseAdapter {
        private Context context;

        ColorAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return colorList.size();
        }

        int getColorName(int pos){
            return colorList.get(pos).getColor();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView circle;
            Drawable colorCircle = ResourcesCompat.getDrawable(getResources(), R.drawable.circle, null);
            int circleDiameter = convertDpToPixel(60);

            if (convertView == null) {
                circle = new ImageView(context);
                circle.setLayoutParams(new android.widget.GridView.LayoutParams(circleDiameter, circleDiameter));
                circle.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                circle = (ImageView) convertView;
            }

            GradientDrawable drawable = (GradientDrawable) colorCircle;
            assert drawable != null;
            drawable.setColor(ContextCompat.getColor(context, getColorName(position)));
            circle.setImageDrawable(colorCircle);
            return circle;
        }
    }

    // --------------------------------------------------------------------------------------------- Dismiss Dialog Listener
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    // --------------------------------------------------------------------------------------------- Convert Dp to Pixels
    private int convertDpToPixel(float dp){
        Resources resources = getActivity().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return Math.round(px);
    }
}