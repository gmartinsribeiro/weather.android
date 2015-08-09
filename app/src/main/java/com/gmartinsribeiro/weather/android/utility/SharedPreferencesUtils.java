package com.gmartinsribeiro.weather.android.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.gmartinsribeiro.weather.android.R;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public class SharedPreferencesUtils {

    public static String get(Context applicationContext, String title, String defaultSettingsLength) {
        SharedPreferences prefs = applicationContext.getSharedPreferences(Constants.MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(title, defaultSettingsLength);
    }

    public static void save(Context applicationContext, String title, String selectedSetting) {
        SharedPreferences.Editor editor = applicationContext.getSharedPreferences(Constants.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(title, selectedSetting);
        editor.commit();
    }

    public static String getSavedUnits(Context applicationContext, String title, String defaultSettingsLength) {
        String result;
        SharedPreferences prefs = applicationContext.getSharedPreferences(Constants.MY_PREFS_NAME, Context.MODE_PRIVATE);
        String savedUnits = prefs.getString(title, defaultSettingsLength);
        String[] possibleUnits = applicationContext.getResources().getStringArray(R.array.settings_temperature_list);

        //Celsius
        if(savedUnits.equals(possibleUnits[0])){
            result = Constants.TEMPERATURE_CELSIUS;
        //Kelvin
        }else if(savedUnits.equals(possibleUnits[1])){
            result = Constants.TEMPERATURE_KELVIN;
        //Fahrenheit
        }else if(savedUnits.equals(possibleUnits[2])){
            result = Constants.TEMPERATURE_FAHRENHEIT;
        //Default
        }else{
            result = Constants.TEMPERATURE_CELSIUS;
        }

        return result;
    }

}
