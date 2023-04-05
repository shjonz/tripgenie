package com.example.fcctut;

public class DataClass {
    private String dataname;
    private String dataImage;

    public DataClass(String dataname, String dataImage) {
        this.dataname = dataname;
        this.dataImage = dataImage;
    }

    public String getDataname() {
        return dataname;
    }

    public void setDataname(String dataname) {
        this.dataname = dataname;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }
}
