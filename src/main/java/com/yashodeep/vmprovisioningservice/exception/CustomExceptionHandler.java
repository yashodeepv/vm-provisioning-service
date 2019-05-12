package com.yashodeep.vmprovisioningservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.List;


@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {ProvisionedVmNotFoundException.class})
    public ResponseEntity provisionedVmNotFound(ProvisionedVmNotFoundException ex, WebRequest request) {
        return getErrorResponseEntity("Provsioned VM not found", HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity userNotFound(UserNotFoundException ex, WebRequest request) {
        return getErrorResponseEntity("User Not found", HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(value = {UserAlreadyRegisteredException.class})
    public ResponseEntity userNotFound(UserAlreadyRegisteredException ex, WebRequest request) {
        return getErrorResponseEntity("User Already Registered", HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(value = {VMConfigMissingException.class})
    public ResponseEntity vmConfigMissing(VMConfigMissingException ex, WebRequest request) {
        return getErrorResponseEntity("VM Config missing in PUT request", HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity vmConfigMissing(Exception ex, WebRequest request) {
        return getErrorResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity getErrorResponseEntity(String message, HttpStatus httpStatus, Throwable t) {
        return ResponseEntity.status(httpStatus).body(new ApiError(httpStatus, message, t.getMessage()));
    }

}

class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }


    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
