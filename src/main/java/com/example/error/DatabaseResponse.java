package com.example.error;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class DatabaseResponse extends Response{
    public DatabaseResponse(int code,String message){
        this.code=code;
        this.message=message;
    }
    public DatabaseResponse(){

    }
}
