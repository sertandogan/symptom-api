package org.symptom.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorCode;

    public ErrorResponse(String message){
        this.message = message;
    }

    public ErrorResponse(Integer errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }

}
