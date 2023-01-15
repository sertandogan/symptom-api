package org.symptom.exception;


import org.symptom.enums.ErrorType;

public class SymptomNotFoundException extends BaseException {
    @Override
    public ErrorType getErrorType() {
        return ErrorType.SYMPTOM_NOT_FOUND_ERROR;
    }
}
