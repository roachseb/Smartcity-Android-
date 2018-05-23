package com.example.samir.accueil.Common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherCommon {
    public static String BASE_URL ="http://api.openweathermap.org/data/2.5/weather?";
    public static String API_KEY = "5c51ced207188772616e9d6b901dfce3";


    public static String apiRequest(String lat,String lon){
        StringBuilder sb = new StringBuilder(BASE_URL);
        sb.append(String.format("lat=%s&lon=%s&&appid=%s&units=metric",lat,lon,API_KEY));
        return sb.toString();
    }

    public static String DateTimeConverter(double apiTime){
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        d.setTime((long)apiTime*1000);
        return df.format(d);
    }

    public static String getIcon(String icon){
        return String.format("http://openweathermap.org/img/w/%s.png",icon);
    }

    public static String getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
