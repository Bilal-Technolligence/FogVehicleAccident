package com.example.fogvehicleaccident;

public class UserAttr {
    String Id;
    String Name;
    String Email;
    String Contact;
    String Address;
    String Latitude;
    String Longitude;
    String ImageUrl;
    String ParkingName;
    String ParkingSpace;
    String Category;
    String Rating;
    String NumRating;

    public UserAttr() {
    }

    public UserAttr(String id, String name, String email, String contact, String address, String latitude, String longitude, String imageUrl, String parkingName, String parkingSpace, String category, String rating, String numRating) {
        Id = id;
        Name = name;
        Email = email;
        Contact = contact;
        Address = address;
        Latitude = latitude;
        Longitude = longitude;
        ImageUrl = imageUrl;
        ParkingName = parkingName;
        ParkingSpace = parkingSpace;
        Category = category;
        Rating = rating;
        NumRating = numRating;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getNumRating() {
        return NumRating;
    }

    public void setNumRating(String numRating) {
        NumRating = numRating;
    }

    public String getId() {
        return Id;
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

    public void setId(String id) {
        Id = id;
    }
    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
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

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getParkingName() {
        return ParkingName;
    }

    public void setParkingName(String parkingName) {
        ParkingName = parkingName;
    }

    public String getParkingSpace() {
        return ParkingSpace;
    }

    public void setParkingSpace(String parkingSpace) {
        ParkingSpace = parkingSpace;
    }
}
