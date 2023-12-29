package org.choongang.commons.exceptions;

import org.springframework.http.HttpStatus;

public class BadrequestException extends CommonException {

    public BadrequestException(String message){
        super(message, HttpStatus.BAD_REQUEST);
    }
}
