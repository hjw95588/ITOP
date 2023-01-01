package com.ebchinatech.itop.api.domain;

import java.util.List;

public class AttendanceResponse extends AttendanceExternal {
    private int status;
    private String message;
    private List<AttendanceExternal> data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AttendanceExternal> getData() {
        return data;
    }

    public void setData(List<AttendanceExternal> data) {
        this.data = data;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
