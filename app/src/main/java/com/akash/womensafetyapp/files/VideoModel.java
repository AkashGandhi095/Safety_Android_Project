package com.akash.womensafetyapp.files;

public class VideoModel {

    private String Video_name , Image;

    public VideoModel(String video_name, String image) {
        Video_name = video_name;
        Image = image;
    }

    public String getVideo_name() {
        return Video_name;
    }

    public String getImage() {
        return Image;
    }
}
