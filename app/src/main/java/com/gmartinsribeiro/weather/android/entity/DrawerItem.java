package com.gmartinsribeiro.weather.android.entity;

import android.graphics.drawable.Drawable;
import android.media.Image;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public class DrawerItem {

    private String description;
    private Drawable icon;

    public DrawerItem(String description, Drawable icon) {
        this.description = description;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
