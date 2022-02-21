package com.develop.torres.test.app.app_test.exceptions;

public class BancoException extends Exception{

    private String code;
    private String message;
    private Exception exc;

    public BancoException() {
    }

    public BancoException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BancoException(String code, String message, Exception exc) {
        this.code = code;
        this.message = message;
        this.exc = exc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getExc() {
        return exc;
    }

    public void setExc(Exception exc) {
        this.exc = exc;
    }
}

