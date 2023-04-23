package com.example.error;

import lombok.Data;

@Data
public abstract class Response {
    protected int code; //code de succes=200 code d'echec=510
    protected String message;

    public void setResponse(int code, String message){
        this.code=code;
        this.message=message;
    }
}
