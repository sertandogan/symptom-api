package org.symptom.exception;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.symptom.enums.ErrorType;
import org.symptom.model.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Set;

@ControllerAdvice
@Component
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Set<ErrorResponse>> handle(Exception ex, HttpServletRequest request) {
        var errorType = ErrorType.UNKNOWN_ERROR;
        LOG.error(errorType.name(), request, errorType.getHttpStatus(), ex);
        return ResponseEntity.status(errorType.getHttpStatus())
                .body(Set.of(new ErrorResponse(errorType.getErrorCode(), getMessage(errorType.getErrorMessageKey()))));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Set<ErrorResponse>> handleBaseException(BaseException ex, HttpServletRequest request) {
        var errorType = Objects.nonNull(ex.getErrorType()) ? ex.getErrorType() : ErrorType.UNKNOWN_ERROR;
        var errorMessage = StringUtils.isBlank(ex.getMessage()) ? getMessage(errorType.getErrorMessageKey()) : ex.getMessage();
        LOG.error(errorType.name(), request, errorType.getHttpStatus(), ex);
        return ResponseEntity.status(errorType.getHttpStatus())
                .body(Set.of(new ErrorResponse(errorType.getErrorCode(), errorMessage)));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Set<ErrorResponse>> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        var errorType = ErrorType.ILLEGAL_ARGUMENT_EXCEPTION_ERROR;

        LOG.error(errorType.name(), request, errorType.getHttpStatus(), ex);
        return ResponseEntity.status(errorType.getHttpStatus())
                .body(Set.of(new ErrorResponse(errorType.getErrorCode(), getMessage(errorType.getErrorMessageKey()))));
    }

    private String getMessage(String messageKey) {
        try {
            return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return messageKey;
        }
    }
}
