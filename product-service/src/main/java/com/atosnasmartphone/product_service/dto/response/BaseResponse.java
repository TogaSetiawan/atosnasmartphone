package com.atosnasmartphone.product_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse {
    private String status;
    private String message;
    private T data;

    public static <T> BaseResponse<T> ok(T data){
        return new BaseResponse<>("success","OK",data);
    }

    public static <T> BaseResponse<T> error(String message){
        return new BaseResponse<>("error",message,null);
    }
}
