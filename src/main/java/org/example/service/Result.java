package org.example.service;

public record Result<T>(boolean status,String message,T element){

    public static <T> Result<T> success(String message,T element){
        return new Result<T>(true,message,element);
    }
    public static <T> Result<T> failed(String message){
        return new Result<T>(false,message,null);
    }
}
