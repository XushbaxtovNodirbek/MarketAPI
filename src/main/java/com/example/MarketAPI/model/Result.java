package com.example.MarketAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    String message;
    boolean status;
    Object data;
    String exception;

    public Result success(Object data){
        return new Result("Success",true,data,null);
    }

    public Result failed(String exception){
        return new Result("Failed",false,null,exception);
    }

}
