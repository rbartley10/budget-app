package learn.budget_app.controllers;

import org.apache.tomcat.util.json.TokenMgrError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        logger.severe("Database Error: " + ex.getMessage());
        return ErrorResponse.build(
                "There was a data integrity issue. Please ensure all data is valid and referenced records exist.",
                HttpStatus.CONFLICT.value(),
                ex.getMessage());
    }

    @ExceptionHandler(TokenMgrError.class)
    public ResponseEntity<ErrorResponse> handleTokenMgrError(TokenMgrError ex) {
        logger.severe("Token Parsing Error: " + ex.getMessage());
        return ErrorResponse.build(
                "There was an issue parsing the input. Please check your syntax and try again.",
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        logger.warning("Validation Error: " + ex.getMessage());
        return ErrorResponse.build(
                "Validation failed for one or more fields. Please check the input and try again.",
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        logger.warning("Media Type Not Supported: " + ex.getMessage());
        return ErrorResponse.build(
                "Unsupported media type. Please send data in the correct format (e.g., JSON).",
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        logger.warning("Malformed JSON Request: " + ex.getMessage());
        return ErrorResponse.build(
                "The request body is invalid or unreadable. Please check the syntax and try again.",
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        logger.warning("No Handler Found: " + ex.getMessage());
        return ErrorResponse.build(
                "The requested resource was not found. Please check the URL and try again.",
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        logger.severe("Unexpected Error: " + ex.getMessage());
        return ErrorResponse.build(
                "An unexpected error occurred. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage());
    }
}