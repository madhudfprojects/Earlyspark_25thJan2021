package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Class_AssessmentData {
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("lst")
    @Expose
    private List<Class_AssessmentStudentDataList> lst = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Class_AssessmentStudentDataList> getLst() {
        return lst;
    }

    public void setLst(List<Class_AssessmentStudentDataList> lst) {
        this.lst = lst;
    }

}
