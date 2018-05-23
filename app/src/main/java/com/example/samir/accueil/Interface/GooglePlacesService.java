package com.example.samir.accueil.Interface;


import com.example.samir.accueil.Model.GooglePlaceDetailsModel.GPDetailsModel;
import com.example.samir.accueil.Model.GooglePlacesModel.GPModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GooglePlacesService {

    @GET
    Call<GPModel>GetPlaces(@Url String url);

    @GET
    Call<GPDetailsModel>getDetailsPlaces(@Url String url);
}
