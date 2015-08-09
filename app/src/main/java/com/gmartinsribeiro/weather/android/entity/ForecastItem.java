package com.gmartinsribeiro.weather.android.entity;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public class ForecastItem {

    @Expose
    private City city;
    @Expose
    private String cod;
    @Expose
    private Double message;
    @Expose
    private Integer cnt;
    @Expose
    private List<Forecast> list = new ArrayList<Forecast>();

    /**
     *
     * @return
     * The city
     */
    public City getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The cod
     */
    public String getCod() {
        return cod;
    }

    /**
     *
     * @param cod
     * The cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     *
     * @return
     * The message
     */
    public Double getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(Double message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The cnt
     */
    public Integer getCnt() {
        return cnt;
    }

    /**
     *
     * @param cnt
     * The cnt
     */
    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    /**
     *
     * @return
     * The list
     */
    public List<Forecast> getList() {
        return list;
    }

    /**
     *
     * @param list
     * The list
     */
    public void setList(List<Forecast> list) {
        this.list = list;
    }
}
