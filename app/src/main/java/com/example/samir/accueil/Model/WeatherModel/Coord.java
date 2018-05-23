package com.example.samir.accueil.Model.WeatherModel;

public class Coord {

    private double lat;
    private double lon;


    public Coord(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
