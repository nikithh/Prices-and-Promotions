package com.publicis.sapient.group.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GroupDoesntExist extends RuntimeException {

    public GroupDoesntExist(final String message) {
        super(message);
    }

}
