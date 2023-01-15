package org.symptom.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.symptom.enums.ErrorType;

@Getter
@NoArgsConstructor
public abstract class BaseException extends RuntimeException {
    public BaseException(String message){
        super(message);
    }
    public abstract ErrorType getErrorType();
}
