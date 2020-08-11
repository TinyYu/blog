package com.ly;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 指定返回状态码 -- 404
public class MyNotFoundException_404 extends RuntimeException {
    public MyNotFoundException_404() {}

    public MyNotFoundException_404(String message){
        super(message);
    }

    public MyNotFoundException_404(String message, Throwable cause){
        super(message,cause);
    }
}
