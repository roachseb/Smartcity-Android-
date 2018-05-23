package com.example.samir.accueil.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.samir.accueil.Common.GooglePlacesCommon;
import com.example.samir.accueil.Interface.GooglePlacesService;
import com.example.samir.accueil.Model.GooglePlaceDetailsModel.GPDetailsModel;
import com.example.samir.accueil.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceView extends AppCompatActivity {

    KenBurnsView knb;
    RatingBar ratingBar;
    TextView opening_hours,place_address,place_name;
    Button btnViewOnMap;

    GPDetailsModel mGPDetailModel;


    GooglePlacesService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_view);

        mService = GooglePlacesCommon.getGooglePlacesService();

        knb=(KenBurnsView)findViewById(R.id.photo);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        place_address = (TextView)findViewById(R.id.place_address);
        place_name = (TextView)findViewById(R.id.place_name);

        btnViewOnMap = (Button)findViewById(R.id.btn_show_map);

        btnViewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mGPDetailModel.getResult().getUrl()));
                startActivity(intent);
            }
        });

        if(GooglePlacesCommon.currentResults.getPhotos()!=null && GooglePlacesCommon.currentResults.getPhotos().length>0){
            Picasso.with(this)
                    .load(getPhotoOfPlace(GooglePlacesCommon.currentResults.getPhotos()[0].getPhoto_reference(),1000))
            .placeholder(R.drawable.civic_building_71)
                    .error(R.drawable.ic_airport)
                    .into(knb);
        }

        if(GooglePlacesCommon.currentResults.getRating() !=null && !TextUtils.isEmpty(GooglePlacesCommon.currentResults.getRating())){
            ratingBar.setRating(Float.parseFloat(GooglePlacesCommon.currentResults.getRating()));

        }else{
            ratingBar.setVisibility(View.GONE);
        }


        mService.getDetailsPlaces(getPlaceDetailUrl(GooglePlacesCommon.currentResults.getPlace_id())).enqueue(new Callback<GPDetailsModel>() {
            @Override
            public void onResponse(Call<GPDetailsModel> call, Response<GPDetailsModel> response) {
                mGPDetailModel = response.body();
                place_name.setText(mGPDetailModel.getResult().getName());
                place_address.setText((mGPDetailModel.getResult().getFormatted_address()));
            }

            @Override
            public void onFailure(Call<GPDetailsModel> call, Throwable t) {

            }
        });

    }

    private String getPlaceDetailUrl(String place_id) {
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        stringBuilder.append("placeid="+place_id)
                .append("&key="+GooglePlacesCommon.APIKEY);
        return stringBuilder.toString();
    }

    private String getPhotoOfPlace(String photo_ref,int maxWidth ){
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo");
        stringBuilder.append("?maxwidth="+maxWidth)
                .append("&photoreference="+photo_ref)
                .append("&key="+GooglePlacesCommon.APIKEY);
        return stringBuilder.toString();
    }
}
