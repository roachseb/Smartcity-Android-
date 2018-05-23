package com.example.samir.accueil.Model.WeatherModel;

public class Main {

    private double temp;
    private double pressure;
    private double humidity;
    private double temp_min;
    private double temp_max;
    private double sea_level;
    private double ground_level;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getSea_level() {
        return sea_level;
    }

    public void setSea_level(double sea_level) {
        this.sea_level = sea_level;
    }

    public double getGround_level() {
        return ground_level;
    }

    public Main(double temp, double pressure, double humidity, double temp_min, double temp_max, double sea_level, double ground_level) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.sea_level = sea_level;
        this.ground_level = ground_level;
    }

    public void setGround_level(double ground_level) {
        this.ground_level = ground_level;
    }

    @Override
    public String toString() {
        return "Main{" +
                "temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                ", sea_level=" + sea_level +
                ", ground_level=" + ground_level +
                '}';
    }
}
