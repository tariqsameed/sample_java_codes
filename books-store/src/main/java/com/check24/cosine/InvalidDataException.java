package com.check24.cosine;

public class InvalidDataException extends Exception {

    String message;


    public InvalidDataException(String message){
        super(message);
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}