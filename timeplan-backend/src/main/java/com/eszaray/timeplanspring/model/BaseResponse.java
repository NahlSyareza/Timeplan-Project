package com.eszaray.timeplanspring.model;

public class BaseResponse<T> {
    public boolean state;
    public String message;
    public T payload;

    public BaseResponse(boolean state, String message, T payload) {
        this.state = state;
        this.message = message;
        this.payload = payload;
    }
}
