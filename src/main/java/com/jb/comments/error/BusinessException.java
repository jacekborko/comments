package com.jb.comments.error;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    @Getter
    private ErrorType errorType;

    public BusinessException(ErrorType errorType){
        this.errorType = errorType;
    }
}
