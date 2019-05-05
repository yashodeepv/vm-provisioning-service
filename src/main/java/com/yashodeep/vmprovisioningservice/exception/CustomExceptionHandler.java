package com.yashodeep.vmprovisioningservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(value = {ProvisionedVmNotFoundException.class})
    public ResponseEntity provisionedVmNotFound(ProvisionedVmNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Provsioned VM not found");
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity userNotFound(UserNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User Not found");
    }

    @ExceptionHandler(value = {VMConfigMissingException.class})
    public ResponseEntity vmConfigMissing(VMConfigMissingException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("VM Config missing in PUT request");
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity vmConfigMissing(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getLocalizedMessage());
    }

}
