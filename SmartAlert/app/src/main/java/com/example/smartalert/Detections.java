package com.example.smartalert;

import java.sql.Timestamp;

public class Detections {

    String typeOfEmergency;
    double latitude;
    double longitude;
    Timestamp timestamp;

    public Detections() {

    }

    public String getTypeOfEmergency() {
        return typeOfEmergency;
    }

    public void setTypeOfEmergency(String typeOfEmergency) {
        this.typeOfEmergency = typeOfEmergency;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
