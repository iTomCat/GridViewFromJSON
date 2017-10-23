package com.tomaszkot.dynamicallyaddedgridlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tomaszkot.dynamicallyaddedgridlayout.common.JSONconfig;
import com.tomaszkot.dynamicallyaddedgridlayout.dialogs.DialogAddItem;
import com.tomaszkot.dynamicallyaddedgridlayout.dialogs.DialogDeleteItem;


public class MainActivity extends AppCompatActivity
        implements DialogAddItem.AddItemDialogListener, DialogDeleteItem.DeleteItemDialListener {

    private JSONconfig JSONconfig;
    private GridViewDisplay appGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        // Hiding the ActionBar
        assert getSupportActionBar() != null;
        getSupportActionBar().hide();
    }

    // --------------------------------------------------------------------------------------------- Initialize
    private void initialize() {
        // ------ creating the JSONconfig instance
        JSONconfig = new JSONconfig(this);

        // ------ creating the GridVIew instance
        appGrid = new GridViewDisplay(this, JSONconfig);
    }

    // This is called when the Dialog Add Item is completed and the results have been passed
    public void onFinishAddItem(String itemName, int selectedColor) {
        // adding data to Json
        JSONconfig.addItemtoJSON(itemName, selectedColor); //color ma byc przesłany
        // adding data and Refresh list
        appGrid.addItemToList(itemName, selectedColor);
    }

    // This is called when the Dialog Delete Item is completed and the results have been passed
    @Override
    public void onFinishDeleteItem(int posOnList) {
        Log.d("Test", "Delete Item: " + posOnList);
        // removing data from Json
        JSONconfig.removeItemFormJSON(posOnList);

        //Removing data and refresh List
        appGrid.removeItemFromList(posOnList);

    }
}
