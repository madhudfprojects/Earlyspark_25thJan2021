package com.det.skillinvillage.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Taluka {
    @SerializedName("District_ID")
    @Expose
    private String districtID;
    @SerializedName("Taluka_ID")
    @Expose
    private String talukaID;
    @SerializedName("Taluka_Name")
    @Expose
    private String talukaName;

    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
    }

    public String getTalukaID() {
        return talukaID;
    }

    public void setTalukaID(String talukaID) {
        this.talukaID = talukaID;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    @NonNull
    @Override
    public String toString() {
        return talukaName;
    }

}
