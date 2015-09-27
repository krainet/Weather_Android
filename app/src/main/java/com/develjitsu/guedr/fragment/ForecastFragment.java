/**
 * Created by hadock on 20/09/15.
 */

package com.develjitsu.guedr.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.develjitsu.guedr.R;
import com.develjitsu.guedr.activity.SettingsActivity;
import com.develjitsu.guedr.model.Forecast;


public class ForecastFragment extends Fragment {

    private Forecast mForecast;

    private ImageView mIcon;
    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mHumidity;
    private TextView mDescription;

    private int mCurrentMetrics;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_forecast,container,false);

        mIcon= (ImageView) root.findViewById(R.id.forecast_image);
        mMaxTemp= (TextView) root.findViewById(R.id.max_temp);
        mMinTemp= (TextView) root.findViewById(R.id.min_temp);
        mHumidity= (TextView) root.findViewById(R.id.humidity);
        mDescription= (TextView) root.findViewById(R.id.forecast_description);

        mCurrentMetrics=getSelectedMetrics();

        setForecast(new Forecast(37, 23, 59, "Algunas nuves", "icon01"));

        return root;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.forecast, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_settings){
            Intent settingsIntent = new Intent(getActivity(),SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getSelectedMetrics() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String stringMetrics = pref.getString(getString(R.string.metric_selection), String.valueOf(SettingsActivity.PREF_CELSIUS));
        return Integer.valueOf(stringMetrics);
    }

    public static float toFarenheit(float celsius){
        return (celsius*1.8f)+32;
    }

    public void setForecast(Forecast forecast){
        mForecast = forecast;

        float maxTmp = mCurrentMetrics == SettingsActivity.PREF_CELSIUS?forecast.getMaxTemp():toFarenheit(forecast.getMaxTemp());
        float minTmp = mCurrentMetrics == SettingsActivity.PREF_CELSIUS?forecast.getMinTemp():toFarenheit(forecast.getMinTemp());
        String tmpSuffix = mCurrentMetrics == SettingsActivity.PREF_CELSIUS?"ºC":"ºF";

        mMaxTemp.setText(String.format(getString(R.string.max_temp_parameter),maxTmp,tmpSuffix));
        mMinTemp.setText(String.format(getString(R.string.min_temp_parameter),minTmp,tmpSuffix));
        mHumidity.setText(String.format(getString(R.string.humidity_parameter), forecast.getHumidity()));
        mDescription.setText(forecast.getDescription());

    }

    @Override
    public void onResume() {
        super.onResume();

        if(mCurrentMetrics!=getSelectedMetrics()){
            final int prevMetrics = mCurrentMetrics;

            mCurrentMetrics=getSelectedMetrics();
            setForecast(mForecast);

            //Snack bar notification (with undo)
            Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.updated_preferences, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Get preferences instade of create instance of SharedPreferences (using static mode)
                            getActivity().getPreferences(1).edit()
                                    .putString(getString(R.string.metric_selection), String.valueOf(prevMetrics))
                                    .apply(); //Puede usarse commit(), apply() no comprueba error al guardar
                            mCurrentMetrics = prevMetrics;
                            setForecast(mForecast);
                        }
                    }).show();
        }
    }

    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }
}
