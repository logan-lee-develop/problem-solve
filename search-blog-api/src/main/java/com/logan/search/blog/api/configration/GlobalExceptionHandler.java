package com.logan.search.blog.api.configration;

import static com.logan.search.blog.api.common.ErrorCode.ERROR_UNKNOWN;

import com.logan.search.blog.api.ApiPackagePlaceHolder;
import com.logan.search.blog.api.common.ErrorCode;
import com.logan.search.blog.api.common.ErrorResponse;
import com.logan.search.blog.domain.exception.BadRequestException;
import com.logan.search.blog.domain.exception.resilience.ResilienceException;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Slf4j
@ControllerAdvice(basePackageClasses = {ApiPackagePlaceHolder.class})
public class GlobalExceptionHandler {

    @ExceptionHandler({
      BadRequestException.class,
      IllegalArgumentException.class,
      ConstraintViolationException.class})
    ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
        log.info("BadRequestException: {}", e.getMessage(), e);
        return new ResponseEntity<>(
          ErrorResponse.of(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage()),
          HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResilienceException.class)
    ResponseEntity<ErrorResponse> handleCannotProcessException(ResilienceException e) {
        log.warn("Unprocessable Process Exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(
          ErrorResponse.of(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), e.getMessage()),
          HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> handleAllException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(toError(ERROR_UNKNOWN), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ErrorResponse toError(ErrorCode errorCode) {
        return ErrorResponse.of(errorCode.getCode(), errorCode.getMessage());
    }
}
