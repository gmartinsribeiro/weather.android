package com.gmartinsribeiro.weather.android.utility;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

/**
 * Created by Gonçalo Martins Ribeiro on 16-08-2015.
 */
public class DialogUtils {

    static AlertDialog.Builder builder;

    public static void createGpsErrorDialog(final Activity activity) {

        builder = new AlertDialog.Builder(activity);
        builder.setMessage("Your GPS seems to be disabled. Please turn it on in Settings.")
                .setTitle("Unable to get location")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                activity.startActivity(i);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void createNetErrorDialog(final Activity activity) {

        builder = new AlertDialog.Builder(activity);
        builder.setMessage("You need a network connection to use this application. Please turn on mobile network or Wi-Fi in Settings.")
                .setTitle("Unable to connect")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(Settings.ACTION_SETTINGS);
                                activity.startActivity(i);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }
}
