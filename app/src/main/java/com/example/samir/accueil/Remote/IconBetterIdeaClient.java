package com.example.samir.accueil.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class IconBetterIdeaClient {
    private static Retrofit retrofit=null;
    public static Retrofit getClient()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://i.olsh.me/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
