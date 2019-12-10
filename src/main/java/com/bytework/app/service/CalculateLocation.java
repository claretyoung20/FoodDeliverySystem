package com.bytework.app.service;

import org.springframework.stereotype.Service;

@Service
public class CalculateLocation {

    private double vendorLat;
    private double vendorLong;


    public double getVendorLat() {
        return vendorLat;
    }

    public void setVendorLat(double vendorLat) {
        this.vendorLat = vendorLat;
    }

    public double getVendorLong() {
        return vendorLong;
    }

    public void setVendorLong(double vendorLong) {
        this.vendorLong = vendorLong;
    }

    public float distanceInMeter() {
        double earthRadiusInMeter = 6371000;

        double BYTEWORK_LAT = 9.072311;
        double lat = Math.toRadians(BYTEWORK_LAT - this.getVendorLat());

        double BYTEWORK_LONG = 7.441013;
        double lng = Math.toRadians(BYTEWORK_LONG - this.getVendorLong());

        double calculateLAT = Math.sin(lat/2) * Math.sin(lat/2) +
                              Math.cos(Math.toRadians(BYTEWORK_LAT)) *
                                  Math.cos(Math.toRadians(this.getVendorLat())) *
                                  Math.sin(lng/2) * Math.sin(lng/2);

        double cal = 2 * Math.atan2(Math.sqrt(calculateLAT), Math.sqrt(1-calculateLAT));
        return  (float) (earthRadiusInMeter * cal);

    }
}
