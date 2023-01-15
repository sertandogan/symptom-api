package org.symptom.exception;


import org.symptom.enums.ErrorType;

public class IllegalDataFormatException extends BaseException {
    public IllegalDataFormatException(String message){
        super(message);
    }
    @Override
    public ErrorType getErrorType() {
        return ErrorType.ILLEGAL_FORMAT_EXCEPTION_ERROR;
    }
}
