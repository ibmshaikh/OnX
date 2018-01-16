package com.example.ibrahim.onx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ibrahim on 23-12-2017.
 */

public class buyersclass {
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }



    public Map<String, String> getTime() {
        return Time;
    }

    public void setTime(Map<String, String> time) {
        Time = time;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    private String Description;

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        Price = Price;
    }

    private int Price;
    private Map<String, String> Time;
    private String UserKey;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private String Name;

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    public String getImage3() {
        return Image3;
    }

    public void setImage3(String image3) {
        Image3 = image3;
    }

    public String getImage4() {
        return Image4;
    }

    public void setImage4(String image4) {
        Image4 = image4;
    }

    public String getImage5() {
        return Image5;
    }

    public void setImage5(String image5) {
        Image5 = image5;
    }

    private String Image1;
    private String Image2;
    private String Image3;
    private String Image4;
    private String Image5;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    private List<String> images = new ArrayList<>();

    public long getOrdering() {
        return ordering;
    }

    public void setOrdering(long ordering) {
        this.ordering = ordering;
    }

    private long ordering;

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    private String USERID;

    public long getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    private long PhoneNumber;


    public buyersclass(){

    }

    public buyersclass(long ordering,String description,int price, List<String> images,Map<String, String> time,String image1,String image2,String image3,String image4,String image5) {

        this.images = images;
        this.Description=description;
        this.Price=price;
        this.Time=time;
        this.Image1=image1;
        this.Image2=image2;
        this.Image3 =image3;
        this.Image4=image4;
        this.Image5=image5;
        this.ordering=ordering;
    }


}
