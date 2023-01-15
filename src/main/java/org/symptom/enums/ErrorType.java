package org.symptom.enums;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ErrorType {

    UNKNOWN_ERROR(INTERNAL_SERVER_ERROR, 5000, "api.error.UNKNOWN_ERROR.message"),
    ILLEGAL_ARGUMENT_EXCEPTION_ERROR(BAD_REQUEST, 4000, "api.error.ILLEGAL_ARGUMENT_EXCEPTION_ERROR.message"),
    ILLEGAL_FORMAT_EXCEPTION_ERROR(BAD_REQUEST, 4001, "api.error.ILLEGAL_FORMAT_EXCEPTION_ERROR.message"),
    INPUT_DATA_EMPTY_ERROR(BAD_REQUEST, 4002, "api.error.INPUT_DATA_EMPTY_ERROR.message"),
    SYMPTOM_NOT_FOUND_ERROR(NOT_FOUND, 4040, "api.error.SYMPTOM_NOT_FOUND_ERROR.message");

    private final Integer errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessageKey;

    ErrorType(HttpStatus httpStatus, Integer errorCode, String errorMessageKey) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessageKey = errorMessageKey;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessageKey() {
        return errorMessageKey;
    }


}
