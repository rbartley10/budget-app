package learn.budget_app.controllers;

import learn.budget_app.domain.Result;
import learn.budget_app.domain.ResultType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String debugMessage;


    public ErrorResponse(String message, int status, String debugMessage) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public static ResponseEntity<ErrorResponse> build(String message, int status, String debugMessage) {
        ErrorResponse errorResponse = new ErrorResponse(message, status, debugMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    public static ResponseEntity<ErrorResponse> build(String message, HttpStatus status) {
        return build(message, status.value(), null);
    }

    public static <T> ResponseEntity<Object> build(Result<T> result) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (result.getType() == null || result.getType() == ResultType.INVALID) {
            status = HttpStatus.BAD_REQUEST;
        } else if (result.getType() == ResultType.NOT_FOUND) {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(result.getMessages(), status);
    }
}