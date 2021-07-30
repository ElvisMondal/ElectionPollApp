package com.assignment4;

import java.io.Serializable;

public class OfficialClass implements Serializable {

    private String City;
    private String State;
    private String Zip;
    private String position;
    private String names;
    private String lineone;
    private String linetwo;
    private String linethree;
    private String Citys;
    private String States;
    private String Zips;
    private String party;
    private String ph;
    private String Urls;
    private String Mail;
    private String PhotoUrl;
    private  String fb;
    private  String tw;
    private  String yt;

    private int value;

    public OfficialClass(String city, String state, String zip, String position, String names, String lineone, String linetwo,String linethree, String citys, String states, String zips, String party, String ph, String urls,String Mail, String photoUrl, String fb, String tw,String yt, int value) {
        this.City = city;
        this.State = state;
        this.Zip = zip;
        this.position = position;
        this.names = names;
        this.lineone = lineone;
        this.linetwo = linetwo;
        this.linethree=linethree;
        this.Citys = citys;
        this.States = states;
        this.Zips = zips;
        this.party = party;
        this.ph = ph;
        this.Urls = urls;
        this.Mail=Mail;
        this.PhotoUrl = photoUrl;
        this.value = value;
        this.fb=fb;
        this.tw=tw;
        this.yt=yt;
    }

    public String getCity() {
        return City;
    }

    public String getState() {
        return State;
    }

    public String getZip() {
        return Zip;
    }

    public String getPosition() {
        return position;
    }

    public String getNames() {
        return names;
    }

    public String getLineone() {
        return lineone;
    }

    public String getLinetwo() {
        return linetwo;
    }

    public String getCitys() {
        return Citys;
    }

    public String getStates() {
        return States;
    }

    public String getZips() {
        return Zips;
    }

    public String getParty() {
        return party;
    }

    public String getPh() {
        return ph;
    }

    public String getUrls() {
        return Urls;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public int getValue() {
        return value;
    }

    public String getMail() {
        return Mail;
    }

    public String getFb() {
        return fb;
    }

    public String getTw() {
        return tw;
    }

    public String getYt() {
        return yt;
    }

    public String getLinethree() {
        return linethree;
    }
}
