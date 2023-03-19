package com.logan.search.blog.api.common;

public enum ErrorCode {

    ERROR_UNKNOWN("500", "Unkown error happened");

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}