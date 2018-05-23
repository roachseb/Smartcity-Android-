package com.example.samir.accueil.Common;


import com.example.samir.accueil.Interface.IconBetterIdeaService;
import com.example.samir.accueil.Interface.NewsService;
import com.example.samir.accueil.Remote.IconBetterIdeaClient;
import com.example.samir.accueil.Remote.RetrofitClient;

public class Common {
    private static final String BASE_URL="https://newsapi.org/";

    public  static final String API_KEY="2e12c8e5a79b4338a577ccfb1446c5e2";

    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService()
    {
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }


    public static String getSourcesUrl(String category, String language,String country){
        StringBuilder url = new StringBuilder(BASE_URL+"/v2/sources?");
        return url.append("apiKey="+API_KEY)
                .append("&country=")
                .append(country)
                .append("&language=")
                .append(language)
                .append("&category=")
                .append(category)
                .toString();
    }

    public static String getAPIUrl(String source,String sortBy,String apiKEY)
    {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/everything?sources=");
        return apiUrl.append(source)
                .append("&q=montpellier")
                //.append(city)
                .append("&apiKey=")
                .append(apiKEY)
                .append("&sortBy=")
                .append(sortBy)
                .toString();
    }


}
