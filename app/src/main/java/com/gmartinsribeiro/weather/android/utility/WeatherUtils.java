package com.gmartinsribeiro.weather.android.utility;

import com.gmartinsribeiro.weather.android.domain.Compass;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public class WeatherUtils {

    public static Compass getRoseByDegree(double degree){
        return Compass.values()[(int)(degree % 8)];
    }
}
