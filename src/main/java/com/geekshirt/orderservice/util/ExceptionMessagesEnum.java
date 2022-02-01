package com.geekshirt.orderservice.util;

public enum ExceptionMessagesEnum {

    INCORRECT_REQUEST_EMPTY_ITEMS_ORDER("Empty items are not allowed in order"),
    ACCOUNT_NOT_FOUND("Account not found");

    private final String value;

    ExceptionMessagesEnum(String msg){
        value = msg;
    }

    public String getValue(){
        return this.value;
    }


}
