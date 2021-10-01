package com.amdocs.usp.userms.configurations;

import javax.servlet.http.HttpServletRequest;

import com.amdocs.usp.userms.exceptions.DataNotFoundException;
import com.amdocs.usp.userms.view.object.DefaultErrorVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<DefaultErrorVO> validation(DataNotFoundException e, HttpServletRequest request) {
        log.error("M=ControllerExceptionHandler.DataNotFoundException.validation", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createError(e.getMessage(), request.getRequestURI()));
    }

    private DefaultErrorVO createError(String error, String path) {
        return DefaultErrorVO.builder().status(HttpStatus.NOT_FOUND.value()).error("Dados não encontrados")
                .message(error).path(path).build();
    }

}
