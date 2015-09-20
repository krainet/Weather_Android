package com.develjitsu.guedr.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.develjitsu.guedr.R;
import com.develjitsu.guedr.model.Forecast;

public class ForecastActivity extends AppCompatActivity {

    private Forecast mForecast;

    private ImageView mIcon;
    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mHumidity;
    private TextView mDescription;

    private int mCurrentMetrics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mIcon= (ImageView) findViewById(R.id.forecast_image);
        mMaxTemp= (TextView) findViewById(R.id.max_temp);
        mMinTemp= (TextView) findViewById(R.id.min_temp);
        mHumidity= (TextView) findViewById(R.id.humidity);
        mDescription= (TextView) findViewById(R.id.forecast_description);

        setForecast(new Forecast(37, 23, 59, "Algunas nuves", "icon01"));
        mCurrentMetrics=getSelectedMetrics();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forecast,menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_settings){
            Intent settingsIntent = new Intent(this,SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mCurrentMetrics!=getSelectedMetrics()){
            final int prevMetrics = mCurrentMetrics;

            mCurrentMetrics=getSelectedMetrics();
            setForecast(mForecast);

            //Snack bar notification (with undo)
            Snackbar.make(findViewById(android.R.id.content), R.string.updated_preferences,Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Get preferences instade of create instance of SharedPreferences (using static mode)
                            getPreferences(MODE_PRIVATE).edit()
                                    .putString(getString(R.string.metric_selection),String.valueOf(prevMetrics))
                                    .apply(); //Puede usarse commit(), apply() no comprueba error al guardar
                            mCurrentMetrics=prevMetrics;
                            setForecast(mForecast);
                        }
                    }).show();
        }
    }

    private int getSelectedMetrics() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String stringMetrics = pref.getString(getString(R.string.metric_selection), String.valueOf(SettingsActivity.PREF_CELSIUS));
        int metrics = Integer.valueOf(stringMetrics);
        return metrics;
    }

    public static float toFarenheit(float celsius){
        return (celsius*1.8f)+32;
    }
}
