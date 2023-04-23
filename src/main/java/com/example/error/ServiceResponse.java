package com.example.error;


public class ServiceResponse extends Response{

    public ServiceResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public ServiceResponse(){
        
    }
}
