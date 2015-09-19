package com.develjitsu.guedr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mIcon= (ImageView) findViewById(R.id.forecast_image);
        mMaxTemp= (TextView) findViewById(R.id.max_temp);
        mMinTemp= (TextView) findViewById(R.id.min_temp);
        mHumidity= (TextView) findViewById(R.id.humidity);
        mDescription= (TextView) findViewById(R.id.forecast_description);

        setForecast(new Forecast(37,23,59,"Algunas nuves","icon01"));
    }

    public void setForecast(Forecast forecast){
        mForecast = forecast;

        mMaxTemp.setText(String.format(getString(R.string.max_temp_parameter),forecast.getMaxTemp()));
        mMinTemp.setText(String.format(getString(R.string.min_temp_parameter),forecast.getMinTemp()));
        mHumidity.setText(String.format(getString(R.string.humidity_parameter), forecast.getMaxTemp()));
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
}
