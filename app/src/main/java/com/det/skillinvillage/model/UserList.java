package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class UserList {
    @SerializedName("Status")
    @Expose
    private Boolean status;

    @SerializedName("Message")
    @Expose
    private String message;
    //StatusStatus
    @SerializedName("lstSummary")
    @Expose
    private List<UserlistDetails> listUser = null;

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


    public List<UserlistDetails> getListUser() {
        return listUser;
    }

    public void setListUser(List<UserlistDetails> listUser) {
        this.listUser = listUser;
    }
}
