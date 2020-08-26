package com.smartsoftwaresolutions.rosary2.Text_spinner;

public class spinner_item {
    String zeker,zeker_ar,zeker_id;

    public spinner_item(String zeker, String zeker_ar,String zeker_id) {
        this.zeker = zeker;
        this.zeker_ar = zeker_ar;
        this.zeker_id = zeker_id;
    }

    public String getZeker_id() {
        return zeker_id;
    }

    public void setZeker_id(String zeker_id) {
        this.zeker_id = zeker_id;
    }

    public String getZeker() {
        return zeker;
    }

    public void setZeker(String zeker) {
        this.zeker = zeker;
    }

    public String getZeker_ar() {
        return zeker_ar;
    }

    public void setZeker_ar(String zeker_ar) {
        this.zeker_ar = zeker_ar;
    }
}
