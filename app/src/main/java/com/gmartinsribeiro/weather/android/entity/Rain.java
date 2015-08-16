package com.gmartinsribeiro.weather.android.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public class Rain {

    @SerializedName("3h")
    @Expose
    private Double _3h;

    /**
     *
     * @return
     * The _3h
     */
    public Double get3h() {
        return _3h;
    }

    /**
     *
     * @param _3h
     * The 3h
     */
    public void set3h(Double _3h) {
        this._3h = _3h;
    }
}
