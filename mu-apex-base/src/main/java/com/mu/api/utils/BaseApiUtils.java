package com.mu.api.utils;

import com.mu.api.constants.Constants;
import com.mu.api.entity.BaseResponse;
import lombok.Data;

@Data
public class BaseApiUtils<T> {
    //通用封装
    public BaseResponse<T> setResult(Integer code, String msg, T data){
        return new BaseResponse<T>(code, msg, data);
    }

    public BaseResponse<T> setResultError(Integer code, String msg) {
        return setResult(code, msg, null);
    }

    //返回错误，可以传msg
    public BaseResponse<T> setResultError(String msg) {
        return setResult(Constants.HTTP_RES_CODE_500, msg, null);
    }

    //返回成功，可以传data值
    public BaseResponse<T> setResultSuccess(T data) {
        return setResult(Constants.HTTP_RES_CODE_200,
                Constants.HTTP_RES_CODE_200_VALUE, data);
    }

    //返回成功，沒有data值
    public BaseResponse<T> setResultSuccess() {
        return setResult(Constants.HTTP_RES_CODE_200,
                Constants.HTTP_RES_CODE_200_VALUE, null);
    }
}
