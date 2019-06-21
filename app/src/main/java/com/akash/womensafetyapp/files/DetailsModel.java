package com.akash.womensafetyapp.files;

public class DetailsModel {
    private String Name , PhoneNo;

    public DetailsModel(String name, String phoneNo) {
        Name = name;
        PhoneNo = phoneNo;
    }

    public String getName() {
        return Name;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }
}
