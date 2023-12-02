package com.bookwise.sembackend.dev;

import com.bookwise.sembackend.model.api.ResultBox;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private void log(Exception e) {
        log.error(e.getClass().getSimpleName() + ": " + e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public ResultBox sqlExceptionHandler(SQLException e) {
        log(e);
        return ResultBox.error(ExceptionEnum.USER_EXIST, "操作失败");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResultBox constraintViolationException(ConstraintViolationException e) {
        log(e);
        return ResultBox.error(ExceptionEnum.FAILED, "操作失败");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResultBox dataIntegrityViolationException(DataIntegrityViolationException e) {
        log(e);
        return ResultBox.error(ExceptionEnum.FAILED, "操作失败");
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ResultBox ioExceptionHandler(IOException e) {
        log(e);
        return ResultBox.error(ExceptionEnum.FAILED, "IO 异常");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultBox exceptionHandler(Exception e) {
        log(e);
        return ResultBox.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
    }
}
