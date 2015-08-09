package com.gmartinsribeiro.weather.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmartinsribeiro.weather.android.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    public NavigationDrawerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.navigation_drawer_item, null);
        }

        String p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.description);
            ImageView iv = (ImageView) v.findViewById(R.id.icon);

            if (tt1 != null) {
                tt1.setText(p);
                if (iv != null) {
                    if(getContext().getString(R.string.title_today).equals(p)){
                        Picasso.with(getContext()).load(R.drawable.ic_today).into(iv);
                    }else if(getContext().getString(R.string.title_forecast).equals(p)){
                        Picasso.with(getContext()).load(R.drawable.ic_forecast).into(iv);
                    }
                }
            }
        }

        return v;
    }
}
