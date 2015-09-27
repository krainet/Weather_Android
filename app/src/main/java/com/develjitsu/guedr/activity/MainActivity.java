/**
 * Created by hadock on 20/09/15.
 */

package com.develjitsu.guedr.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.develjitsu.guedr.R;
import com.develjitsu.guedr.fragment.CityPagerFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        if(fm.findFragmentById(R.id.fragment_citypager)==null) {
            fm.beginTransaction()
                    .add(R.id.fragment_citypager,CityPagerFragment.newInstance())
                    .commit();
        }
    }
}
