package com.capstone.dangdang.error;


import com.capstone.dangdang.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalHttpExHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponse> handleHttpCustomException(CustomException e) {
        log.error("handleHttpCustomException throw CustomException : {}", e.getErrorCode());
        e.printStackTrace();
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message(ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(x -> x.getDefaultMessage())
                                .collect(Collectors.toList()).toString())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("ValidException")
                        .build());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleException(Exception e) {
//        e.printStackTrace();
//        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
