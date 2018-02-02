package com.example.cash.sdp;

/**
 * Created by redsage23 on 3/27/2017.
 */

public class EventLoactions {
    private String TITLE="";
    private String data="";
    private int participants;
    public double latitude;
    public double logitude;
    private String TIME;
    private String Building;


    public EventLoactions(String title, String date, String time, int part, double lat,double longi, String building ){
      setTITLE(title);
        setData(date);
        setParticipants(part);
        setLatitude(lat);
        setLogitude(longi);
        setTIME(time);
        setBuilding(building);




    }
    public EventLoactions(Double lat, Double longi, String Building){
        latitude=lat;
        logitude=longi;
        this.setBuilding(Building);

    }
    public EventLoactions(Double lat, Double longi) {
        latitude=lat;
        logitude=longi;
        this.setBuilding("");

    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLogitude() {
        return logitude;
    }

    public void setLogitude(double logitude) {
        this.logitude = logitude;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getBuilding() {
        return Building;
    }

    public void setBuilding(String building) {
        Building = building;
    }
}
