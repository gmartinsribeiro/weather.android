package com.gmartinsribeiro.weather.android.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public class Clouds {

    @Expose
    private Integer all;

    /**
     *
     * @return
     * The all
     */
    public Integer getAll() {
        return all;
    }

    /**
     *
     * @param all
     * The all
     */
    public void setAll(Integer all) {
        this.all = all;
    }
}
