package com.gmartinsribeiro.weather.android.fragment;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmartinsribeiro.weather.android.R;
import com.gmartinsribeiro.weather.android.adapter.ForecastAdapter;
import com.gmartinsribeiro.weather.android.controller.WeatherAPI;
import com.gmartinsribeiro.weather.android.entity.ForecastItem;
import com.gmartinsribeiro.weather.android.utility.Connectivity;
import com.gmartinsribeiro.weather.android.utility.Constants;
import com.gmartinsribeiro.weather.android.utility.Locator;
import com.gmartinsribeiro.weather.android.utility.SharedPreferencesUtils;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ForecastFragment extends Fragment implements Locator.Listener{

    private static final String TAG = "ForecastFragment";

    private OnFragmentInteractionListener mListener;
    private RecyclerView mForecastList;
    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ForecastAdapter mAdapter;

    public static ForecastFragment newInstance() {
        ForecastFragment fragment = new ForecastFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_forecast, container, false);

        mForecastList = (RecyclerView) v.findViewById(R.id.forecastList);

        //Check connection and get location
        if(Connectivity.isConnected(getActivity())){
            getCurrentLocation();
        }else{
            //TODO
            //Show error
        }

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void getCurrentLocation() {

        Locator l = new Locator(getActivity());
        l.getLocation(Locator.Method.NETWORK_THEN_GPS, this);
    }

    private void getForecast(Location location)
    {
        //Retrofit section start from here...
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.API_URL).build();         //creating an adapter for retrofit with base url

        WeatherAPI controller = restAdapter.create(WeatherAPI.class);    //creating a service for adapter with our GET class

        //Get user preferences for metrics
        String units = SharedPreferencesUtils.getSavedUnits(getActivity(), getString(R.string.title_temperature), Constants.DEFAULT_SETTINGS_LENGTH);

        //Retrofit using gson for JSON-POJO conversion
        controller.getForecast(location.getLatitude(), location.getLongitude(), Constants.FORECAST_NUMBER_DAYS, units, new Callback<ForecastItem>() {
            @Override
            public void success(ForecastItem forecast, Response response) {

                mAdapter = new ForecastAdapter(getActivity(), forecast.getList());

                mForecastList.setAdapter(mAdapter);

                // Setup layout manager for items
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                layoutManager.scrollToPosition(0);

                // Attach layout manager to the RecyclerView
                mForecastList.setLayoutManager(layoutManager);

                mForecastList.setHasFixedSize(true);
            }

            @Override
            public void failure(RetrofitError error) {
                //TODO
                //Show error
            }
        });
    }

    @Override
    public void onLocationFound(Location location) {
         getForecast(location);
    }

    @Override
    public void onLocationNotFound() {
        //TODO
        //Show error or default location (Prague?)
        Location l = new Location(Constants.DEFAULT_LOCATION_PROVIDER);
        l.setLatitude(Constants.DEFAULT_LOCATION_LATITUDE);
        l.setLongitude(Constants.DEFAULT_LOCATION_LONGITUDE);
        getForecast(l);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
