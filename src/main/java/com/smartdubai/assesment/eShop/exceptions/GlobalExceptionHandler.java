package com.smartdubai.assesment.eShop.exceptions;

import com.smartdubai.assesment.eShop.controller.response.ApiError;
import com.smartdubai.assesment.eShop.services.BooksServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

  Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseBody
  public List<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    List<ApiError> errors =
        fieldErrors.stream()
            .map(
                fieldError ->
                    new ApiError(
                        "INVALID_REQ",
                        fieldError.getField() + " " + fieldError.getDefaultMessage()))
            .collect(Collectors.toList());
    log.error("Validation errors",e);
    return errors;
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = JpaObjectRetrievalFailureException.class)
  @ResponseBody
  public List<ApiError> handleMethodNotFoundException(JpaObjectRetrievalFailureException e) {
    log.error("JPA errors",e);
    //TODO: we need our own runtime errors here. we should wrap this error into our own errors and throw that from respected service/repo classes.
    return Arrays.asList(new ApiError("DATA ERROR", e.getMessage()));
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public List<ApiError> handleInternalException(Exception e) {
    log.error("Generic errors",e);
    return Arrays.asList(new ApiError("INTERNAL_ERROR", e.getMessage()));
  }


}
