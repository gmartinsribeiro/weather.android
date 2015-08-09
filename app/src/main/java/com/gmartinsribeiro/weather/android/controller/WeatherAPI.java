package com.gmartinsribeiro.weather.android.controller;

import com.gmartinsribeiro.weather.android.entity.ForecastItem;
import com.gmartinsribeiro.weather.android.entity.WeatherItem;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public interface WeatherAPI {
    @GET("/weather")
    public void getTodayWeather(@Query("lat") double lat, @Query("lon") double lon, @Query("units") String units, Callback<WeatherItem> response);

    @GET("/forecast/daily")
    public void getForecast(@Query("lat") double lat, @Query("lon") double lon, @Query("cnt") int cnt, @Query("units") String units, Callback<ForecastItem> response);
}
