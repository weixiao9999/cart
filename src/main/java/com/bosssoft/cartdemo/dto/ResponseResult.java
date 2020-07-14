package com.bosssoft.cartdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hujierong
 * @date 2020-7-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult {

    private Integer status;

    private String message;

    private Object data;

    public ResponseResult(Integer status) {
        this.status = status;
    }

    public ResponseResult(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseResult(Integer status, Throwable throwable) {
        this.status = status;
        this.message = throwable.getMessage();
    }

    public ResponseResult(Integer status, Object data) {
        this.status = status;
        this.data = data;
    }
}
