package com.gmartinsribeiro.weather.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmartinsribeiro.weather.android.R;
import com.gmartinsribeiro.weather.android.domain.Temperature;
import com.gmartinsribeiro.weather.android.entity.Forecast;
import com.gmartinsribeiro.weather.android.utility.CalendarUtils;
import com.gmartinsribeiro.weather.android.utility.Constants;
import com.gmartinsribeiro.weather.android.utility.SharedPreferencesUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private Context mContext;
    private final List<Forecast> mItems;

    public ForecastAdapter(Context context, List<Forecast> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.forecast_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Forecast item = mItems.get(i);
        if (viewHolder.tt1 != null) {
            String description = item.getWeather().get(0).getDescription();
            viewHolder.tt1.setText(description.substring(0, 1).toUpperCase() +  description.substring(1) + " on " + CalendarUtils.getNextWeekDay(i));
        }

        if (viewHolder.tt2 != null) {
            String result;
            //Get user preferences for metrics
            String savedUnits = SharedPreferencesUtils.get(mContext, mContext.getString(R.string.title_temperature), Constants.DEFAULT_SETTINGS_LENGTH);
            String[] possibleUnits = mContext.getResources().getStringArray(R.array.settings_temperature_list);

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
            viewHolder.tt2.setText((int) Math.round(item.getTemp().getDay())+ " " + result);
        }

        if (viewHolder.iv != null) {
            /*((ShapeDrawable)viewHolder.iv.getBackground()).getPaint().setColor(
                    mContext.getResources().getColor(R.color.blue500));*/
            Picasso.with(mContext).load(Constants.API_URL_IMAGES + item.getWeather().get(0).getIcon() + Constants.IMAGE_EXTENSION).resize(150, 150)
                    .centerInside().into(viewHolder.iv);
        }
        viewHolder.currentItem = item;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tt1;
        public TextView tt2;
        public ImageView iv;
        public Forecast currentItem;

        public ViewHolder(View v) {
            super(v);
            tt1 = (TextView) v.findViewById(R.id.firstLine);
            tt2 = (TextView) v.findViewById(R.id.secondLine);
            iv = (ImageView) v.findViewById(R.id.icon);
        }
    }

}