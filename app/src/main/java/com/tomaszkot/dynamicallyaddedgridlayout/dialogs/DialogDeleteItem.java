package com.tomaszkot.dynamicallyaddedgridlayout.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.tomaszkot.dynamicallyaddedgridlayout.R;

public class DialogDeleteItem extends DialogFragment{
    AlertDialog.Builder dialogBuilder;

    // Defines the listener interface
    public interface DeleteItemDialListener {
        void onFinishDeleteItem(int posOnList);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(R.string.delete_item);

        // ----------------------------------------------------------------------------------------- Buttons Cancel / Delete item
        dialogBuilder.setPositiveButton(R.string.delete_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Bundle bundleSendData = getArguments();
                        int posOnList = bundleSendData.getInt("pos_list");
                        Log.d("Test", "From Bundle: " + posOnList);
                        DeleteItemDialListener listener = (DeleteItemDialListener) getActivity();
                        listener.onFinishDeleteItem(posOnList);

                    }
                })

        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Closing Dialog
                        DialogDeleteItem.this.getDialog().cancel();
            }
        });
        return dialogBuilder.create();
    }
}
