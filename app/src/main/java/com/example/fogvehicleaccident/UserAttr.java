package com.example.fogvehicleaccident;

import android.net.Uri;

public class UserAttr {
    String Id;
    String Name;
    String Email;
    String Contact;
    String Address;
    String Latitude;
    String Longitude;
    String ImageUrl;


    public UserAttr() {
    }

    public UserAttr(String id, String name, String email, String contact, String address, String latitude, String longitude, String imageUrl) {
        Id = id;
        Name = name;
        Email = email;
        Contact = contact;
        Address = address;
        Latitude = latitude;
        Longitude = longitude;
        ImageUrl = imageUrl;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
