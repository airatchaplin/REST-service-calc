package com.airatchaplin.restcalc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class, IllegalArgumentException.class, ArithmeticException.class})
    public ResponseEntity<Object> handlerApiRequestException(RuntimeException e) {
        HttpStatus httpStatus = null;
        Map<String, Object> body = new HashMap<>();
        if (e.getClass().equals(NotFoundException.class)) {
            httpStatus = HttpStatus.NOT_FOUND;
            body.put("timestamp", new Date());
            body.put("status", HttpStatus.FORBIDDEN.value());
            body.put("message", e.getMessage());
            return new ResponseEntity<>(body, httpStatus);
        }
        if (e.getClass().equals(IllegalArgumentException.class) || e.getClass().equals(ArithmeticException.class)) {
            httpStatus = HttpStatus.BAD_REQUEST;
            body.put("timestamp", new Date());
            body.put("status", HttpStatus.FORBIDDEN.value());
            body.put("message", e.getMessage());
            return new ResponseEntity<>(body, httpStatus);
        }

        ApiException apiException = new ApiException(e.getMessage(), httpStatus);
        assert false;
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
