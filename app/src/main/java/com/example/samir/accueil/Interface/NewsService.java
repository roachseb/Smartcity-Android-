package com.example.samir.accueil.Interface;




import com.example.samir.accueil.Model.NewsModel.News;
import com.example.samir.accueil.Model.NewsModel.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface NewsService {
    @GET
    Call<WebSite> getSources(@Url String url);

    @GET
    Call<News> getNewestArticles(@Url String url);
}
