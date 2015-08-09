package com.gmartinsribeiro.weather.android.activity;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gmartinsribeiro.weather.android.R;
import com.gmartinsribeiro.weather.android.adapter.SettingsAdapter;
import com.gmartinsribeiro.weather.android.entity.SettingsItem;
import com.gmartinsribeiro.weather.android.utility.Constants;
import com.gmartinsribeiro.weather.android.utility.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */

public class SettingsActivity extends AppCompatActivity {

    private SettingsAdapter mAdapter;
    private List<SettingsItem> mSettings;
    private AlertDialog mLevelDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue500)));

        if(Build.VERSION.SDK_INT>=21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.blue700));

        }

        final ListView settingsList = (ListView) findViewById(R.id.settingsList);
        if(mSettings == null){
            mSettings = populateSettings();
        }

        mAdapter = new SettingsAdapter(this,
                R.layout.settings_list_item, mSettings);
        settingsList.setAdapter(mAdapter);

        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                SettingsItem item = (SettingsItem) parent.getItemAtPosition(position);
                showSettingsDialog(item);
            }
        });
    }

    private void showSettingsDialog(final SettingsItem settingsItem) {
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(settingsItem.getTitle());
        builder.setSingleChoiceItems(settingsItem.getSettings(), -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                updateView(settingsItem, settingsItem.getSettings()[item]);
                mLevelDialog.dismiss();
            }
        });
        mLevelDialog = builder.create();
        mLevelDialog.show();

    }

    private void updateView(SettingsItem settingsItem, String selectedSetting) {
        settingsItem.setSelectedSetting(selectedSetting);
        //Save on phone
        SharedPreferencesUtils.save(getApplicationContext(), settingsItem.getTitle(), selectedSetting);
        mAdapter.notifyDataSetChanged();
    }

    private List<SettingsItem> populateSettings() {
        List<SettingsItem> settings = new ArrayList<>();
        SettingsItem length = new SettingsItem();
        length.setTitle(getString(R.string.title_length));
        length.setSelectedSetting(SharedPreferencesUtils.get(getApplicationContext(), length.getTitle(), Constants.DEFAULT_SETTINGS_LENGTH));
        length.setSettings(getResources().getStringArray(R.array.settings_length_list));
        settings.add(length);

        SettingsItem temperature = new SettingsItem();
        temperature.setTitle(getString(R.string.title_temperature));
        temperature.setSelectedSetting(SharedPreferencesUtils.get(getApplicationContext(), temperature.getTitle(), Constants.DEFAULT_SETTINGS_TEMPERATURE));
        temperature.setSettings(getResources().getStringArray(R.array.settings_temperature_list));
        settings.add(temperature);

        return settings;
    }
}
