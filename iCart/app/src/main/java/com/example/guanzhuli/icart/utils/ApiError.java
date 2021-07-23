package com.example.guanzhuli.icart.utils;

import com.google.gson.annotations.SerializedName;

public class ApiError {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}