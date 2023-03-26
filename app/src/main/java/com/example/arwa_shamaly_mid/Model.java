package com.example.arwa_shamaly_mid;
import java.util.ArrayList;
public class Model {
    ArrayList<Data> dataArrayList;
    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }
    public void setDataArrayList(ArrayList<Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }
}
class Data{
    User user;
    ArrayList<PhotoOrder> photoOrderArrayList;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public ArrayList<PhotoOrder> getPhotoOrderArrayList() {
        return photoOrderArrayList;
    }
    public void setPhotoOrderArrayList(ArrayList<PhotoOrder> photoOrderArrayList) {
        this.photoOrderArrayList = photoOrderArrayList;
    }
}
class User{
    String Phone;
    String Details;
    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }
    public String getDetails() {
        return Details;
    }
    public void setDetails(String details) {
        Details = details;
    }
}
class PhotoOrder{
    String  Photo;
    public String getPhoto() {
        return Photo;
    }
    public void setPhoto(String photo) {
        Photo = photo;
    }
}
