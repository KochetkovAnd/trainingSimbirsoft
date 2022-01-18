package ru.simbirsoft.training.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class BaseControllerAdvice {


    @ExceptionHandler(ResourceNotFoundException.class)
    public Object resourceNotFoundException(ResourceNotFoundException e) {
        return response(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(NoPermissionException.class)
    public Object noPermissionException(NoPermissionException e) {
        return response(HttpStatus.FORBIDDEN, e);
    }

    private Object response (HttpStatus status, AbstractException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String, String> body = new HashMap<>();
        body.put("message", e.getMessage());
        body.put("techInfo", e.getTechInfo());
        body.put("status", status.toString());
        return new ResponseEntity<>(body, headers, status);
    }
}
