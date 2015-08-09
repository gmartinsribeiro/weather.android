package com.gmartinsribeiro.weather.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gmartinsribeiro.weather.android.R;
import com.gmartinsribeiro.weather.android.entity.SettingsItem;

import java.util.List;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public class SettingsAdapter extends ArrayAdapter<SettingsItem> {

    public SettingsAdapter(Context context, int resource, List<SettingsItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.settings_list_item, null);
        }

        SettingsItem p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.firstLine);
            TextView tt2 = (TextView) v.findViewById(R.id.secondLine);

            if (tt1 != null) {
                tt1.setText(p.getTitle());
            }

            if (tt2 != null) {
                tt2.setText(p.getSelectedSetting());
            }
        }

        return v;
    }
}


