package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location_DataList {
    @SerializedName("Count")
    @Expose
    private List<Count> count = null;
    @SerializedName("State")
    @Expose
    private List<State> state = null;
    @SerializedName("District")
    @Expose
    private List<District> district = null;
    @SerializedName("Taluka")
    @Expose
    private List<Taluka> taluka = null;
    @SerializedName("Village")
    @Expose
    private List<Village> village = null;
    @SerializedName("Year")
    @Expose
    private List<Year> year = null;
    @SerializedName("Response")
    @Expose
    private String response;



    public List<State> getState() {
        return state;
    }

    public void setState(List<State> state) {
        this.state = state;
    }

    public List<District> getDistrict() {
        return district;
    }

    public void setDistrict(List<District> district) {
        this.district = district;
    }

    public List<Taluka> getTaluka() {
        return taluka;
    }

    public void setTaluka(List<Taluka> taluka) {
        this.taluka = taluka;
    }

    public List<Village> getVillage() {
        return village;
    }

    public void setVillage(List<Village> village) {
        this.village = village;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Year> getYear() {
        return year;
    }

    public void setYear(List<Year> year) {
        this.year = year;
    }


    public List<Count> getCount() {
        return count;
    }

    public void setCount(List<Count> count) {
        this.count = count;
    }
}
