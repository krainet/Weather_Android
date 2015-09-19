package com.develjitsu.guedr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = (ImageView) findViewById(R.id.weather_image);
        Button changeButton = (Button) findViewById(R.id.change_system_button);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("RAMON1", "Change Image using TAG");
                Log.v("RAMON2", String.valueOf(imageView.getTag()));
                if(imageView.getTag()==(Integer) 1){
                    Log.v("RAMON3", String.valueOf(imageView.getTag()));
                    imageView.setImageResource(R.drawable.offline_weather2);
                    imageView.setTag(2);
                }else{
                    Log.v("RAMON4", String.valueOf(imageView.getTag()));
                    imageView.setImageResource(R.drawable.offline_weather);
                    imageView.setTag(1);
                }
                Log.v("RAMON5", String.valueOf(imageView.getTag()));


            }
        });


    }



    //quitar menu
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
