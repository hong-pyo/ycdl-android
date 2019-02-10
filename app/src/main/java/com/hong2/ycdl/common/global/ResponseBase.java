package com.hong2.ycdl.common.global;


import java.io.Serializable;

@SuppressWarnings("PMD.UnusedPrivateField")
public abstract class ResponseBase<T>  implements Serializable {

    private String rCode = RCodeContant.CODE.SUCCESS;

    private String rMessage = RCodeContant.MSG.SUCCESS;

    private T rData;

    public String getrCode() {
        return rCode;
    }

    public void setrCode(String rCode) {
        this.rCode = rCode;
    }

    public String getrMessage() {
        return rMessage;
    }

    public void setrMessage(String rMessage) {
        this.rMessage = rMessage;
    }

    public T getrData() {
        return rData;
    }

    public void setrData(T rData) {
        this.rData = rData;
    }
}