package com.gmartinsribeiro.weather.android.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.gmartinsribeiro.weather.android.R;
import com.gmartinsribeiro.weather.android.fragment.ForecastFragment;
import com.gmartinsribeiro.weather.android.fragment.NavigationDrawerFragment;
import com.gmartinsribeiro.weather.android.fragment.TodayFragment;
import com.gmartinsribeiro.weather.android.utility.Connectivity;
import com.gmartinsribeiro.weather.android.utility.DialogUtils;
import com.gmartinsribeiro.weather.android.utility.Locator;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, TodayFragment.OnFragmentInteractionListener,
        ForecastFragment.OnFragmentInteractionListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private Toolbar mToolbar;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if(Build.VERSION.SDK_INT>=21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.blue700));

        }

        //Check connection and get location
        if (!Connectivity.isConnected(this)) {
            DialogUtils.createNetErrorDialog(this);
        } else {
            Locator l = new Locator(this);
            if (!l.hasGpsEnabled()) {
                DialogUtils.createGpsErrorDialog(this);
            }
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position) {
            case 0:
                mTitle = getString(R.string.title_today);
                fragment = TodayFragment.newInstance();
                break;
            case 1:
                mTitle = getString(R.string.title_forecast);
                fragment = ForecastFragment.newInstance();
                break;
        }
        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();

            setTitle(mTitle);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_about) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle(getString(R.string.title_about));
            alert.setMessage(getString(R.string.description_about));
            alert.setPositiveButton(getString(R.string.ok), null);
            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Toolbar getmToolbar() {
        return mToolbar;
    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
