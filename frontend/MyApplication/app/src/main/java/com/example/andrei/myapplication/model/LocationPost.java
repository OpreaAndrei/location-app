package com.example.andrei.myapplication.model;

public class LocationPost {
    private String email;
    private String longitude;
    private String latitude;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }



    public LocationPost(String latitude, String longitude, String email) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
    }
}
