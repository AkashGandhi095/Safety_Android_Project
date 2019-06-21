package com.akash.womensafetyapp.files;

public class MainViewModel {

    private String Name , MainImage;
    public MainViewModel(String name, String mainImage) {
        Name = name;
        MainImage = mainImage;
    }


    public String getName() {
        return Name;
    }

    public String getMainImage() {
        return MainImage;
    }
}
