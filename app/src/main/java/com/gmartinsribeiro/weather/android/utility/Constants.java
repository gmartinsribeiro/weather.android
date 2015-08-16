package com.gmartinsribeiro.weather.android.utility;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public class Constants {

    public static final String API_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String API_URL_IMAGES = "http://openweathermap.org/img/w/";
    public static final String API_GMAPS_STREETVIEW = "https://maps.googleapis.com/maps/api/streetview?size=1080x1920&location={latitude},{longitude}&fov=90&heading=235&pitch=10";
    public static final String IMAGE_EXTENSION = ".png";

    public static final String DEFAULT_SETTINGS_LENGTH = "Meter";
    public static final String DEFAULT_SETTINGS_TEMPERATURE = "Celsius";

    public static final String MY_PREFS_NAME = "STRVWeather";
    public static final int FORECAST_NUMBER_DAYS = 10;

    /*Temperature is available in Fahrenheit, Celsius and Kelvin units.

    For temperature in Fahrenheit use units=imperial
    For temperature in Celsius use units=metric
    Temperature in Kelvin is used by default, no need to use units parameter in API call*/
    public static final String TEMPERATURE_KELVIN = "";
    public static final String TEMPERATURE_CELSIUS = "metric";
    public static final String TEMPERATURE_FAHRENHEIT = "imperial";

    //Terminology
    public static final String HUMIDITY = " %";
    public static final String PRECIPITATION = " mm";
    public static final String PRESSURE = " hPa";
    public static final String WIND = " km/h";
}
