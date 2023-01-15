package org.symptom.exception;


import org.symptom.enums.ErrorType;

public class IllegalFileFormatException extends BaseException {
    @Override
    public ErrorType getErrorType() {
        return ErrorType.ILLEGAL_FORMAT_EXCEPTION_ERROR;
    }
}
