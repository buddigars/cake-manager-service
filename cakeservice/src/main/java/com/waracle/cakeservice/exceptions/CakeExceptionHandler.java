package com.waracle.cakeservice.exceptions;

import com.waracle.cakeservice.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class CakeExceptionHandler {
    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        final ErrorResponse response = new ErrorResponse();
        HttpStatus status;

        if (ex instanceof ResourceNotFoundException) {
            ResourceNotFoundException rnf = (ResourceNotFoundException) ex;
            response.setMessage("[" + rnf.resourceType + "] not found");
            status = NOT_FOUND;
        } else if (ex instanceof IllegalArgumentException) {
            response.setMessage(StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : "Incorrect arguments");
            status = BAD_REQUEST;
        } else if (ex instanceof CakeAlreadyExistsException) {
            response.setMessage("Cake already exists");
            status = BAD_REQUEST;
        } else if (ex instanceof HttpClientErrorException.Unauthorized) {
            response.setMessage(StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : "Unauthorised User");
            status = UNAUTHORIZED;
        } else if (ex instanceof AccessDeniedException) {
            response.setMessage("Access Denied");
            status = FORBIDDEN;
        } else if (ex instanceof DatabaseOperationException) {
            DatabaseOperationException dbe = (DatabaseOperationException) ex;
            response.setMessage(dbe.dbError);
            status = BAD_REQUEST;
        } else {
            log.error("Unexpected error occurred ", ex.getCause());
            response.setMessage("Application error occurred");
            status = INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(response);
    }
}
