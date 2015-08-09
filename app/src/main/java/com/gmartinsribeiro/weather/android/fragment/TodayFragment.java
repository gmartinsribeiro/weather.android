package com.gmartinsribeiro.weather.android.fragment;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmartinsribeiro.weather.android.R;
import com.gmartinsribeiro.weather.android.controller.WeatherAPI;
import com.gmartinsribeiro.weather.android.domain.Temperature;
import com.gmartinsribeiro.weather.android.entity.WeatherItem;
import com.gmartinsribeiro.weather.android.utility.Connectivity;
import com.gmartinsribeiro.weather.android.utility.Constants;
import com.gmartinsribeiro.weather.android.utility.Locator;
import com.gmartinsribeiro.weather.android.utility.SharedPreferencesUtils;
import com.gmartinsribeiro.weather.android.utility.WeatherUtils;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TodayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends Fragment implements Locator.Listener{

    private static final String TAG = "TodayFragment";

    private OnFragmentInteractionListener mListener;

    private ImageView backgroundPicture;

    private TextView city;
    private ImageView weatherIcon;
    private TextView weatherDescription;
    private TextView temperature;

    private TextView humidity;
    private TextView precipitation;
    private TextView pressure;
    private TextView wind;
    private TextView direction;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TodayFragment.
     */
    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }

    public TodayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_today, container, false);

        backgroundPicture = (ImageView) v.findViewById(R.id.backgroundPicture);

        city = (TextView) v.findViewById(R.id.city);
        weatherIcon = (ImageView) v.findViewById(R.id.weatherIcon);
        weatherDescription = (TextView) v.findViewById(R.id.weatherDescription);
        temperature = (TextView) v.findViewById(R.id.temperature);

        humidity = (TextView) v.findViewById(R.id.humidityValue);
        precipitation = (TextView) v.findViewById(R.id.precipitationValue);
        pressure = (TextView) v.findViewById(R.id.pressureValue);
        wind = (TextView) v.findViewById(R.id.windValue);
        direction = (TextView) v.findViewById(R.id.directionValue);

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

    private void getTodayWeather(Location location)
    {
        //Retrofit section start from here...
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.API_URL).build();                                        //create an adapter for retrofit with base url

        WeatherAPI controller = restAdapter.create(WeatherAPI.class);                            //creating a service for adapter with our GET class

        //Get user preferences for metrics
        String units = SharedPreferencesUtils.getSavedUnits(getActivity(), getString(R.string.title_temperature), Constants.DEFAULT_SETTINGS_LENGTH);

        //Now ,we need to call for response
        //Retrofit using gson for JSON-POJO conversion
        controller.getTodayWeather(location.getLatitude(), location.getLongitude(), units, new Callback<WeatherItem>() {
            @Override
            public void success(WeatherItem weather, Response response) {
                //we get json object from github server to our POJO or model class
                populateView(weather);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    private void populateView(WeatherItem weather) {
        city.setText(weather.getName());
        if(weather.getWeather() != null && weather.getWeather().get(0) != null){
            Picasso.with(getActivity()).load(Constants.API_URL_IMAGES + weather.getWeather().get(0).getIcon() + Constants.IMAGE_EXTENSION).into(weatherIcon);
            weatherDescription.setText(weather.getWeather().get(0).getDescription().substring(0, 1).toUpperCase() +  weather.getWeather().get(0).getDescription().substring(1));

        }
        if(weather.getMain() != null){
            String result;
            //Get user preferences for metrics
            String savedUnits = SharedPreferencesUtils.get(getActivity(), getActivity().getString(R.string.title_temperature), Constants.DEFAULT_SETTINGS_LENGTH);
            String[] possibleUnits = getActivity().getResources().getStringArray(R.array.settings_temperature_list);

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
            temperature.setText((int) Math.round(weather.getMain().getTemp()) + " " + result);
            humidity.setText(weather.getMain().getHumidity() + Constants.HUMIDITY);
            pressure.setText(weather.getMain().getPressure() + Constants.PRESSURE);
        }
        if(weather.getRain() != null){
            precipitation.setText(weather.getRain().get3h() + Constants.PRECIPITATION);
        }else{
            precipitation.setText("0" + Constants.PRECIPITATION);
        }
        if(weather.getWind() != null){
            wind.setText(weather.getWind().getSpeed() + Constants.WIND);
            direction.setText(WeatherUtils.getRoseByDegree(weather.getWind().getDeg()).toString());
        }
    }

    @Override
    public void onLocationFound(Location location) {

        getTodayWeather(location);

        Picasso.with(getActivity()).load(Constants.API_GMAPS_STREETVIEW.replace("{latitude}", String.valueOf(location.getLatitude())).replace("{longitude}", String.valueOf(location.getLongitude()))).into(backgroundPicture);
    }

    @Override
    public void onLocationNotFound() {
        Log.e(TAG, "No location found.");
        //Show error or default location (Prague?)
        Location l = new Location(Constants.DEFAULT_LOCATION_PROVIDER);
        l.setLatitude(Constants.DEFAULT_LOCATION_LATITUDE);
        l.setLongitude(Constants.DEFAULT_LOCATION_LONGITUDE);
        getTodayWeather(l);
        Picasso.with(getActivity()).load(Constants.API_GMAPS_STREETVIEW.replace("{latitude}", String.valueOf(Constants.DEFAULT_LOCATION_LATITUDE)).replace("{longitude}", String.valueOf(Constants.DEFAULT_LOCATION_LONGITUDE))).into(backgroundPicture);
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
        public void onFragmentInteraction(String uri);
    }

}
