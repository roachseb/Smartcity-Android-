package com.example.samir.accueil.Common;


import com.example.samir.accueil.Interface.GooglePlacesService;
import com.example.samir.accueil.Model.GooglePlacesModel.Results;
import com.example.samir.accueil.Remote.RetrofitClient;

public class GooglePlacesCommon {
    public static final String BASEURL = "https://maps.googleapis.com";
    public static final String APIKEY = "AIzaSyDUpvyJgg1C-fnClOUO9EibncOaKK4-yJY";

    public static Results currentResults;

    public static GooglePlacesService getGooglePlacesService() {
        return RetrofitClient.getClient(BASEURL).create(GooglePlacesService.class);
    }

    public static String getApiUrl(Double lat, Double lon, int radius ){
        StringBuilder apiUrl = new StringBuilder(BASEURL);

        return apiUrl.append("/maps/api/place/nearbysearch/json?key="+ GooglePlacesCommon.APIKEY)
                .append("&sensor=true")
                .append("&location="+lat+","+lon)
                .append("&radius="+radius)
                .toString();
    }

}
