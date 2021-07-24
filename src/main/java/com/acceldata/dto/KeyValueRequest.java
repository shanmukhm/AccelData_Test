package com.acceldata.dto;

import com.acceldata.service.RequestType;

public class KeyValueRequest {
    private RequestType requestType;
    private KeyValue keyValue;
    private String key;

    public KeyValueRequest(RequestType requestType, KeyValue keyValue, String key) {
        this.requestType = requestType;
        this.keyValue = keyValue;
        this.key = key;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public KeyValue getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(KeyValue keyValue) {
        this.keyValue = keyValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
