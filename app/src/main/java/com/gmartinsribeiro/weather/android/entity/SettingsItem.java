package com.gmartinsribeiro.weather.android.entity;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public class SettingsItem {

    private String title;
    private String selectedSetting;
    private String[] settings;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSelectedSetting() {
        return selectedSetting;
    }

    public void setSelectedSetting(String selectedSetting) {
        this.selectedSetting = selectedSetting;
    }

    public String[] getSettings() {
        return settings;
    }

    public void setSettings(String[] settings) {
        this.settings = settings;
    }
}
