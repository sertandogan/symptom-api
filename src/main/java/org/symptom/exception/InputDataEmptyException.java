package org.symptom.exception;


import org.symptom.enums.ErrorType;

public class InputDataEmptyException extends BaseException {
    @Override
    public ErrorType getErrorType() {
        return ErrorType.INPUT_DATA_EMPTY_ERROR;
    }
}
