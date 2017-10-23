package com.tomaszkot.dynamicallyaddedgridlayout.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.tomaszkot.dynamicallyaddedgridlayout.R;

public class DialogAddItem extends DialogFragment{
    private View view;
    private ImageView selectColorButton;
    private EditText itemName;
    public DialogSelectColor myNewDialog;

    // --------------------------------------------------------------------------------------------- Defines the listener interface
    public interface AddItemDialogListener {
        void onFinishAddItem(String inputText, int selectedColor);
    }

    // --------------------------------------------------------------------------------------------- Create Dialog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Dialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add item to list");
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        ViewGroup container = (ViewGroup) getView();
        view = inflater.inflate(R.layout.dialog_add_item, container, false);

        initialization();

        // ----------------------------------------------------------------------------------------- Buttons Cancel / AddItem
        builder.setView(view)
                .setPositiveButton(R.string.add_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        AddItemDialogListener listener = (AddItemDialogListener) getActivity();
                        String addItemName = checkingItemName(itemName.getText().toString());
                        int addSelectedColor = DialogSelectColor.selectedColor;
                        listener.onFinishAddItem(addItemName, addSelectedColor);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAddItem.this.getDialog().cancel();
                    }
                });

        // ----------------------------------------------------------------------------------------- Button Select Color
        selectColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myNewDialog = new DialogSelectColor();

                // Add Dismiss Litener to Dialog Select Color
                myNewDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // It's performed after dismiss Dialog Select Color
                        setCircleColor(DialogSelectColor.selectedColor);
                    }
                });
                myNewDialog.show(getFragmentManager(), "dialog_select_color");
            }
        });
        return builder.create();
    }

    // --------------------------------------------------------------------------------------------- Initialize
    private void initialization(){
        itemName = (EditText) view.findViewById(R.id.item_name);
        selectColorButton = (ImageView) view.findViewById(R.id.color_circle);
    }

    // --------------------------------------------------------------------------------------------- Change the color of the round button
    private void setCircleColor(int color){
        GradientDrawable circleButton = (GradientDrawable) selectColorButton.getBackground();
        circleButton.setColor(ContextCompat.getColor(view.getContext(),color));
    }

    // --------------------------------------------------------------------------------------------- If the Item Name input field is empty, add the default name
    private String checkingItemName(String name){
        if (TextUtils.isEmpty(name)) {
            return "Item";
        }else{
            return name;
        }
    }


}
