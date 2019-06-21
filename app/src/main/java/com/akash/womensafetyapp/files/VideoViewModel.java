package com.akash.womensafetyapp.files;

public class VideoViewModel {
    private String Video_name;
    private String Video_id;
    private String Video_desc;

    private String Source;
    private String Link;

    public String getLink() {
        return Link;
    }




    public VideoViewModel() { }

    public String getVideo_name() {
        return Video_name;
    }

    public String getVideo_id() {
        return Video_id;
    }

    public String getVideo_desc() {
        return Video_desc;
    }


    public String getSource() {
        return Source;
    }
}