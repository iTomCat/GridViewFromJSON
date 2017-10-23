package com.tomaszkot.dynamicallyaddedgridlayout;

public class ListItem {
    private String title;
    private int color;

    // Constructor
    public ListItem(String title, int color)
    {
        this.title = title;
        this.color = color;
    }

    String getTitle()
    {
        return title;
    }

    int getColor()
    {
        return color;
    }
}
