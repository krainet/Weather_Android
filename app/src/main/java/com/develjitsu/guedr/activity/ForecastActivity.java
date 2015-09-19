package com.develjitsu.guedr.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.develjitsu.guedr.R;

public class ForecastActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mImageView= (ImageView) findViewById(R.id.weather_image);
        final ToggleButton button = (ToggleButton) findViewById(R.id.system_button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.setImageResource(button.isChecked()?R.drawable.offline_weather:R.drawable.offline_weather2);
            }
        });
    }



/*    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.system_button1:
                mImageView.setImageResource(R.drawable.offline_weather2);
                break;
            case R.id.system_button2:
                mImageView.setImageResource(R.drawable.offline_weather);
                break;
        }
    }*/
}
