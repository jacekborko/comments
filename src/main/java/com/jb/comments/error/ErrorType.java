package com.jb.comments.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public enum ErrorType {
    BASE_DIR_CONFIGURATION_ERROR("Base directory configured not properly"),
    SERVICE_CONNECTION_ERROR("Error while connecting to post service"),
    CANNOT_CLEAR_BASE_DIR("Can not clear base directory"),
    CANNOT_CREATE_BASE_DIR("Can not create base directory"),
    CANNOT_CREATE_DOMAIN_DIR("Can not create domain directory"),
    ERROR_WHILE_PARSE_COMMENT("Error while parsing comment"),
    ERROR_WHILE_SAVING_FILE("Error while saving file with comment"),
    ERROR_WHILE_CLOSING("Error while closing comment file"),
    UNKNOWN("Unknown")
    ;

    private HttpStatus status;
    private String message;

    ErrorType(String message){
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = message;
    }
}