package com.tomaszkot.dynamicallyaddedgridlayout;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.tomaszkot.dynamicallyaddedgridlayout.common.JSONconfig;
import com.tomaszkot.dynamicallyaddedgridlayout.dialogs.DialogAddItem;
import com.tomaszkot.dynamicallyaddedgridlayout.dialogs.DialogDeleteItem;

import java.util.ArrayList;

class GridViewDisplay implements AdapterView.OnItemClickListener{
    private android.widget.GridView gridview;
    private ArrayList<ListItem> data = new ArrayList<>();
    private AppCompatActivity mainActivity;
    private JSONconfig jsonConfig;

    // --------------------------------------------------------------------------------------------- Constructor
    GridViewDisplay(AppCompatActivity mainActivity, JSONconfig jsonConfig){
        this.mainActivity = mainActivity;
        this.jsonConfig = jsonConfig;

        initView(); // Initialize GridViewDisplay Components
        makeList(); // Add Data from Json to ArrayList
        setDataAdapter(); // Set Data to Adapter
    }

    // --------------------------------------------------------------------------------------------- Initialize GridViewDisplay Components
    private void initView(){
        gridview = (android.widget.GridView) mainActivity.findViewById(R.id.gridView);
        assert gridview != null;
        gridview.setOnItemClickListener(this);
    }

    // --------------------------------------------------------------------------------------------- Adding Data from Json to ArrayList
    private void makeList(){
        //Add saved items from json file
        for (int i = 0; i < jsonConfig.getJSONlistLenght(); i++) {
            String name =  jsonConfig.getItemFromJSON(i).getTitle();
            int color = jsonConfig.getItemFromJSON(i).getColor();
            data.add(new ListItem(name, color));
        }
        data.add(new ListItem("", 0));
    }

    private int getListSize(){
        return data.size();
    }

    // --------------------------------------------------------------------------------------------- After adding new item - Add it to list and refresh list
    void addItemToList(String name, int selectedColor){
        //Add first item with plus icon
        data.remove(getListSize()-1);
        data.add(new ListItem(name, selectedColor));
        //Add to list new added item
        data.add(new ListItem("", 0));
        gridview.invalidateViews();
    }

    // --------------------------------------------------------------------------------------------- After removing item - Remove it from list and refresh list
    void removeItemFromList(int posOnList){
        data.remove(posOnList);
        gridview.invalidateViews();
    }

    // --------------------------------------------------------------------------------------------- Set Data to Adapter
    private void setDataAdapter(){
        GridViewAdapter gridviewAdapter = new GridViewAdapter(mainActivity.getApplicationContext(), R.layout.grid_view_layout, data);
        gridview.setAdapter(gridviewAdapter);

        // ----------------------------------------------------------------------------------------- Long Click Listener
        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                if (pos != (getListSize() - 1)) deleteItemDialog(pos);
                return true;
            }
        });
    }

    // --------------------------------------------------------------------------------------------- Click Listener
    @Override
    public void onItemClick(final AdapterView<?> arg0, final View view, final int position, final long id){
        String message = "Clicked : " + data.get(position).getTitle();

        if (position == (getListSize() - 1)){
            addItemDialog();
        } else {
            Toast.makeText(mainActivity.getApplicationContext(), message , Toast.LENGTH_SHORT).show();
        }
    }

    // --------------------------------------------------------------------------------------------- Create Dialog Add Item
    private void addItemDialog(){
        DialogFragment dialogAddItem = new DialogAddItem();
        dialogAddItem.show(mainActivity.getFragmentManager(), "dialog_add_item");
    }

    // --------------------------------------------------------------------------------------------- Create Dialog and sending the position on list
    private void deleteItemDialog(int position){
        Log.d("Test", "Delete: " + position);
        DialogDeleteItem dialogDeleteItem = new DialogDeleteItem();
        Bundle bundleSendData = new Bundle();
        bundleSendData.putInt("pos_list", position);
        dialogDeleteItem.setArguments(bundleSendData);
        dialogDeleteItem.show(mainActivity.getFragmentManager(), "dialog_delete_item");
    }
}
