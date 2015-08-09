package com.gmartinsribeiro.weather.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmartinsribeiro.weather.android.R;
import com.gmartinsribeiro.weather.android.domain.Temperature;
import com.gmartinsribeiro.weather.android.utility.WeatherUtils;
import com.gmartinsribeiro.weather.android.entity.Forecast;
import com.gmartinsribeiro.weather.android.utility.CalendarUtils;
import com.gmartinsribeiro.weather.android.utility.Constants;
import com.gmartinsribeiro.weather.android.utility.SharedPreferencesUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public class ForecastAdapter extends ArrayAdapter<Forecast> {

    public ForecastAdapter(Context context, int resource, List<Forecast> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.forecast_list_item, null);
        }

        Forecast p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.firstLine);
            TextView tt2 = (TextView) v.findViewById(R.id.secondLine);
            ImageView iv = (ImageView) v.findViewById(R.id.icon);

            if (tt1 != null) {
                String description = p.getWeather().get(0).getDescription();
                tt1.setText(description.substring(0, 1).toUpperCase() +  description.substring(1) + " on " + CalendarUtils.getNextWeekDay(position));
            }

            if (tt2 != null) {
                String result;
                //Get user preferences for metrics
                String savedUnits = SharedPreferencesUtils.get(getContext(), getContext().getString(R.string.title_temperature), Constants.DEFAULT_SETTINGS_LENGTH);
                String[] possibleUnits = getContext().getResources().getStringArray(R.array.settings_temperature_list);

                //Celsius
                if(savedUnits.equals(possibleUnits[0])){
                    result = Temperature.ºC.toString();
                //Kelvin
                }else if(savedUnits.equals(possibleUnits[1])){
                    result = Temperature.K.toString();
                //Fahrenheit
                }else if(savedUnits.equals(possibleUnits[2])){
                    result = Temperature.ºF.toString();
                //Default
                }else{
                    result = Temperature.ºC.toString();
                }
                tt2.setText((int) Math.round(p.getTemp().getDay())+ " " + result);
            }

            if (iv != null) {
                Picasso.with(getContext()).load(Constants.API_URL_IMAGES + p.getWeather().get(0).getIcon() + Constants.IMAGE_EXTENSION).resize(150, 150)
                        .centerInside().into(iv);
            }
        }

        return v;
    }
}
