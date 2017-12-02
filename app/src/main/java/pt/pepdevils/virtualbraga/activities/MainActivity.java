package pt.pepdevils.virtualbraga.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

import pt.pepdevils.virtualbraga.R;
import pt.pepdevils.virtualbraga.adapters.CustomPagerAdapter;
import pt.pepdevils.virtualbraga.model.ARPoint;
import pt.pepdevils.virtualbraga.model.City;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private CustomPagerAdapter mCustomPagerAdapter;
    private TextView city_name;
    private int positionViewPagerOverall;
    private ArrayList<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //todo: instalar retrofit
        // fazer o download dos points aqui

        //Download City and points here
        ArrayList<ARPoint> arPoints = new ArrayList<ARPoint>() {{
            add(new ARPoint("Pep House", 41.559199, -8.403818, 0));
            add(new ARPoint("Ponto de Teste", 41.559222, -8.403888, 0));
            add(new ARPoint("Braga Parque", 41.5580735, -8.4058441, 0));
            add(new ARPoint("Azambujas House", 41.561128, -8.40956, 0));
            add(new ARPoint("Hospital", 41.567838, -8.399068, 0));
        }};


        City braga = new City("braga", arPoints,R.drawable.b);
        City porto = new City("porto", arPoints,R.drawable.p);
        City lisboa = new City("lisboa", arPoints,R.drawable.l);
        cities = new ArrayList<>();
        cities.add(braga);
        cities.add(porto);
        cities.add(lisboa);


        //gravar os mesmos em sharedpreferences
/*        for (ARPoint point: braga.getPoints()) {
            PreferencesHelper.saveObjectInSharedPref(MainActivity.this, point, Constants.SP_POINTS_TAG, braga.getPoints().size());
        }*/


        //todo: utilizar o get na actividade para a camera e para o mapa
        //PreferencesHelper.getObjectInSharedPref(this,Constants.SP_POINTS_TAG);


        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //todo:enviar para a proxima actividade com a cidade certa
                City c = ChooseCityByPageViewPos(positionViewPagerOverall);


                Intent i = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(i);
            }
        });





        city_name = findViewById(R.id.city_name);
        mCustomPagerAdapter = new CustomPagerAdapter(this, cities);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);

        mViewPager.setAdapter(mCustomPagerAdapter);

        //initial state
        positionViewPagerOverall = mViewPager.getCurrentItem();
        ChangeCityName(mViewPager.getCurrentItem());

        final Animation expandIn = AnimationUtils.loadAnimation(this, R.anim.animation_pop);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                city_name.startAnimation(expandIn);
            }

            @Override
            public void onPageSelected(int position) {
                ChangeCityName(position);
                positionViewPagerOverall = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    private City ChooseCityByPageViewPos(int positionViewPagerOverall) {
        return cities.get(positionViewPagerOverall);
    }

    private void ChangeCityName(int position) {
        if (position == 0) {
            city_name.setText("LISBOA");
        } else if (position == 1) {
            city_name.setText("BRAGA");
        } else {
            city_name.setText("PORTO");
        }
    }

}
