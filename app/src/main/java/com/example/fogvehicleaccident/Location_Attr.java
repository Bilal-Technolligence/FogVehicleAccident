package com.example.fogvehicleaccident;

/**
 * Created by Hanzalah on 3/2/2019.
 */

public class Location_Attr {
    private String Id ;
    private String Name;
    private String Contact;
    private String Longitude;
    private String Latitude;
    private String Type;
    private String From;
    private String To;
    private String Speed;
    private String Vehicle;


    public Location_Attr() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getType() {
        return Type;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public String getVehicle() {
        return Vehicle;
    }

    public void setVehicle(String vehicle) {
        Vehicle = vehicle;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public Location_Attr(String id, String name, String contact, String longitude, String latitude, String type, String from, String to, String speed, String vehicle) {
        Id = id;
        Name = name;
        Contact = contact;
        Longitude = longitude;
        Latitude = latitude;
        Type = type;
        From = from;
        To = to;
        Speed = speed;
        Vehicle = vehicle;
    }
}
