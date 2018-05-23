package com.example.samir.accueil.Activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.samir.accueil.Common.WeatherCommon;
import com.example.samir.accueil.Helper.Helper;
import com.example.samir.accueil.Model.WeatherModel.OpenWeatherClass;
import com.example.samir.accueil.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;


public class WeatherActivity extends AppCompatActivity {

    TextView txtCity,txtLastUpdate,txtDescription,txtHumidity,txtTime,txtCelsius;
    ImageView imageView;
    LinearLayout LL ;
    /*
        LocationManager locationManager;
        String provider;*/
    static double lat, lng;
    OpenWeatherClass openWeatherClass =new OpenWeatherClass();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_weather);

        txtCity = (TextView) findViewById(R.id.txtCity);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        txtCelsius = (TextView) findViewById(R.id.txtCelsius);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtLastUpdate = (TextView) findViewById(R.id.txtLastUpdate);
        txtTime = (TextView) findViewById(R.id.txtTime);
        imageView = (ImageView) findViewById(R.id.imageView);
        LL = (LinearLayout)findViewById(R.id.weather_activity);

        //Get Coordinates
        Bundle bundle = getIntent().getExtras();
        lat = bundle.getDouble("lat");
        lng = bundle.getDouble("lng");

        /* print
        Toast.makeText(getApplicationContext(), lat+"", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), lng+"", Toast.LENGTH_SHORT).show();
        */

        new GetWeather().execute(WeatherCommon.apiRequest(String.valueOf(lat),String.valueOf(lng)));
        //setWeatherData(WeatherCommon.apiRequest(String.valueOf(lat),String.valueOf(lng)));
    }


    public void ChangeBackground(String IconCode){
        Drawable bkgclearsky= ResourcesCompat.getDrawable(getResources(), R.drawable.background_clear_sky, null);
        Drawable bkgfewclouds= ResourcesCompat.getDrawable(getResources(), R.drawable.backgound_few_clouds, null);
        Drawable bkgclouds= ResourcesCompat.getDrawable(getResources(), R.drawable.background_clouds, null);
        Drawable bkgsnow= ResourcesCompat.getDrawable(getResources(), R.drawable.background_snow, null);
        Drawable bkgshowerrain= ResourcesCompat.getDrawable(getResources(), R.drawable.background_shower_rain, null);
        Drawable bkglightrain= ResourcesCompat.getDrawable(getResources(), R.drawable.background_light_rain, null);
        Drawable bkgheavyrain= ResourcesCompat.getDrawable(getResources(), R.drawable.background_heavy_rain, null);

        switch (openWeatherClass.getWeather().get(0).getIcon()) {
            case "01d":
                LL.setBackground(bkgclearsky);
                break;
            case "01n":
                LL.setBackground(bkgclearsky);
                break;
            case "02d":
                LL.setBackground(bkgfewclouds);
                break;
            case "02n":
                LL.setBackground(bkgfewclouds);
                break;
            case "03d":
                LL.setBackground(bkgclouds);
                break;
            case "03n":
                LL.setBackground(bkgclouds);
                break;
            case "04d":
                LL.setBackground(bkgclouds);
                break;
            case "04n":
                LL.setBackground(bkgclouds);
                break;
            case "09d":
                LL.setBackground(bkgshowerrain);
                break;
            case "09n":
                LL.setBackground(bkgshowerrain);
                break;
            case "10d":
                LL.setBackground(bkglightrain);
                break;
            case "10n":
                LL.setBackground(bkglightrain);
                break;
            case "11d":
                LL.setBackground(bkgheavyrain);
                break;
            case "11n":
                LL.setBackground(bkgheavyrain);
                break;
            case "13d":
                LL.setBackground(bkgsnow);
                txtHumidity.setTextColor(Color.GRAY);
                txtCity.setTextColor(Color.GRAY);
                txtLastUpdate.setTextColor(Color.GRAY);
                txtDescription.setTextColor(Color.GRAY);
                txtTime.setTextColor(Color.GRAY);
                txtCelsius.setTextColor(Color.GRAY);

                break;
            case "13n":
                LL.setBackground(bkgsnow);
                txtHumidity.setTextColor(Color.GRAY);
                txtCity.setTextColor(Color.GRAY);
                txtLastUpdate.setTextColor(Color.GRAY);
                txtDescription.setTextColor(Color.GRAY);
                txtTime.setTextColor(Color.GRAY);
                txtCelsius.setTextColor(Color.GRAY);
                break;
            case "50d":
                LL.setBackground(bkgsnow);
                txtHumidity.setTextColor(Color.GRAY);
                txtCity.setTextColor(Color.GRAY);
                txtLastUpdate.setTextColor(Color.GRAY);
                txtDescription.setTextColor(Color.GRAY);
                txtTime.setTextColor(Color.GRAY);
                txtCelsius.setTextColor(Color.GRAY);
                break;
            case "50n":
                LL.setBackground(bkgsnow);
                txtHumidity.setTextColor(Color.GRAY);
                txtCity.setTextColor(Color.GRAY);
                txtLastUpdate.setTextColor(Color.GRAY);
                txtDescription.setTextColor(Color.GRAY);
                txtTime.setTextColor(Color.GRAY);
                txtCelsius.setTextColor(Color.GRAY);
                break;
            default:
                LL.setBackground(bkgfewclouds);
                break;
        }
    }
    public void setWeatherData(String s){
        Gson gson = new Gson();
        Type mType = new TypeToken<OpenWeatherClass>() {
        }.getType();
        openWeatherClass = gson.fromJson(s, mType);

        ChangeBackground(openWeatherClass.getWeather().get(0).getIcon());

        txtCity.setText(String.format("%s,%s", openWeatherClass.getName(), openWeatherClass.getSys().getCountry()));
        txtLastUpdate.setText(String.format("Last Updated: %s", WeatherCommon.getDateNow()));
        txtDescription.setText(String.format("%s", openWeatherClass.getWeather().get(0).getDescription()));
        txtHumidity.setText(String.format("%f%%", openWeatherClass.getMain().getHumidity()));
        txtTime.setText(String.format("%s/%s", WeatherCommon.DateTimeConverter(openWeatherClass.getSys().getSunrise()), WeatherCommon.DateTimeConverter(openWeatherClass.getSys().getSunset())));
        txtCelsius.setText(String.format("%.2f °C", openWeatherClass.getMain().getTemp()));
        Picasso.with(WeatherActivity.this)
                .load(WeatherCommon.getIcon(openWeatherClass.getWeather().get(0).getIcon()))
                .into(imageView);

    }


    private class GetWeather extends AsyncTask<String,Void,String> {
        ProgressDialog pd = new ProgressDialog(WeatherActivity.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            pd.show();

        }


        @Override
        protected String doInBackground(String... params) {
            String stream = null;
            String urlString = params[0];

            Helper http = new Helper();
            stream = http.getHttpData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("Error: Not found city")) {
                pd.dismiss();
                return;
            }
            Gson gson = new Gson();
            Type mType = new TypeToken<OpenWeatherClass>() {
            }.getType();
            openWeatherClass = gson.fromJson(s, mType);


            pd.dismiss();

            ChangeBackground(openWeatherClass.getWeather().get(0).getIcon());

            txtCity.setText(String.format("%s,%s", openWeatherClass.getName(), openWeatherClass.getSys().getCountry()));
            txtLastUpdate.setText(String.format("Last Updated: %s", WeatherCommon.getDateNow()));
            txtDescription.setText(String.format("%s", openWeatherClass.getWeather().get(0).getDescription()));
            txtHumidity.setText(String.format("%f%%", openWeatherClass.getMain().getHumidity()));
            txtTime.setText(String.format("%s/%s", WeatherCommon.DateTimeConverter(openWeatherClass.getSys().getSunrise()), WeatherCommon.DateTimeConverter(openWeatherClass.getSys().getSunset())));
            txtCelsius.setText(String.format("%.2f °C", openWeatherClass.getMain().getTemp()));
            Picasso.with(WeatherActivity.this)
                    .load(WeatherCommon.getIcon(openWeatherClass.getWeather().get(0).getIcon()))
                    .into(imageView);

        }
    }

}

