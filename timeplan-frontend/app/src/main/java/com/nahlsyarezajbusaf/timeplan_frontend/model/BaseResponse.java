package com.nahlsyarezajbusaf.timeplan_frontend.model;

public class BaseResponse<T> {
    public boolean state;
    public String message;
    public T payload;

    /**
     * Standard for basic response. Standardization really helps in everything, I tell you
     *
     * @param state
     * @param message
     * @param payload
     */
    public BaseResponse(boolean state, String message, T payload) {
        this.state = state;
        this.message = message;
        this.payload = payload;
    }
}
