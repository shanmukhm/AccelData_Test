package com.acceldata.dto;

public class AccelResponse {
    private String message;
    private boolean success;
    private int errorCode;

    public AccelResponse(String message, boolean success, int errorCode) {
        this.message = message;
        this.success = success;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
