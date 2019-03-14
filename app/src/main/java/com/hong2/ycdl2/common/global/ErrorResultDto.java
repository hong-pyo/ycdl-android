package com.hong2.ycdl2.common.global;

public class ErrorResultDto {
    private String errorMessage;
    private int errorCode;

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
